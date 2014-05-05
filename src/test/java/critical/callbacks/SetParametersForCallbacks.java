package critical.callbacks;


import critical.TestUtils;
import model.Environment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class SetParametersForCallbacks {


    private long id = System.currentTimeMillis();

    private String baseUrl = "https://secure.payonlinesystem.com/";
    private String loginMerchant = "info@smart-travel.ru";
    private String passwordMerchant = "tester123";
    private String loginAdmin = "v.khomutov";
    private String passwordAdmin = "tester123";
    private String captcha = "ability";
    private String MIDpending = "55477";
    private String currencyRUB = "RUB";
    private String orderID = "GUI-autotest";
    private String email = "autoTEST@test.test";
    private String numberCardA = "4111";
    private String numberCardB = "1111";
    private String numberCardC = "1111";
    private String numberCardD = "1111";
    private String expDateMonth = "05";
    private String expDateYear = "2015";
    private String cardHolderName = "MR.AUTOTEST";
    private String cvc = "111";
    private String bank = "QA-BANK";
    private String callbackOption = "Отправлять при отклонении оплаты";
    private String failureCallbackUrl = "https://secure.payonlinesystem.com/admin";
    private String pendingMercahnt = "#55477 - www.autotest.gui.com";
    private String optionPendingMerchant = "option[value=\"55477\"]";
    private String failAmount = "33";
    private String idTransaction;
    WebDriver driver = DriverFactory.getInstance().getDriver();




   @Test
   public void setParam (){

        //admin Login
        driver.get(baseUrl + "login/");
        model.Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //find merchant and open merchant settings
        driver.findElement(By.id("ctl00_content_filter_merchantId")).clear();
        driver.findElement(By.id("ctl00_content_filter_merchantId")).sendKeys(MIDpending);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(MIDpending)).click();
        driver.findElement(By.xpath("./*//*[@id='settingsView']/div/a")).click();


        //set CallBack mode
        new Select(driver.findElement(By.id("ctl00_content_clientSecurity_callbackMode"))).selectByVisibleText(callbackOption);

        //fill Failure CallBack Url field and save parameters
        driver.findElement(By.id("ctl00_content_clientSecurity_failureCallBackUrl")).clear();
        driver.findElement(By.id("ctl00_content_clientSecurity_failureCallBackUrl")).sendKeys(failureCallbackUrl);
        driver.findElement(By.id("ctl00_content_clientSecurity_cmdSave")).click();

        //merchant Login
        driver.get(baseUrl + "login/");
        model.Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //payment transaction and check result page
        TestUtils.paymentTransaction(driver, pendingMercahnt, optionPendingMerchant, currencyRUB, "123", failAmount, email, numberCardA,
                numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cardHolderName, cvc, bank);
        Assert.assertTrue(model.Utils.checkResultPageTransaction(driver, failAmount) & model.Utils.checkResultPageTransaction(driver, "123"), "Failure result page!");
        Assert.assertTrue(driver.findElement(By.id("ctl00_content_cardForm_errorContent_errorMessage")).isDisplayed(), "Failure result page!");
    }

  /*  @Test
    public void setParam (){

        //admin Login
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //find merchant and open merchant settings
        driver.findElement(By.id("ctl00_content_filter_merchantId")).clear();
        driver.findElement(By.id("ctl00_content_filter_merchantId")).sendKeys(MIDpending);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(MIDpending)).click();
        driver.findElement(By.xpath("./*//*[@id='settingsView']/div/a")).click();


        //set CallBack mode
        new Select(driver.findElement(By.id("ctl00_content_clientSecurity_callbackMode"))).selectByVisibleText(callbackOption);

        //fill Failure CallBack Url field and save parameters
        driver.findElement(By.id("ctl00_content_clientSecurity_failureCallBackUrl")).clear();
        driver.findElement(By.id("ctl00_content_clientSecurity_failureCallBackUrl")).sendKeys(failureCallbackUrl);
        driver.findElement(By.id("ctl00_content_clientSecurity_cmdSave")).click();

        //merchant Login
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //payment transaction and check result page
        TestUtils.paymentTransaction(driver, pendingMercahnt, optionPendingMerchant, currencyRUB, "123", failAmount, email, numberCardA,
                                                                                                   numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cardHolderName, cvc, bank);
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, failAmount) & Utils.checkResultPageTransaction(driver, "123"), "Failure result page!");
        Assert.assertTrue(driver.findElement(By.id("ctl00_content_cardForm_errorContent_errorMessage")).isDisplayed(), "Failure result page!");
    }          */
}
