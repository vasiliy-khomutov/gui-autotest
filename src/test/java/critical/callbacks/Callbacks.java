package critical.callbacks;

import critical.TestUtils;
import model.Email;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Callbacks {

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



    @Test
    public void failureCallback(){

            WebDriver driver = DriverFactory.getInstance().getDriver();

            //admin login and  find failed trx
            driver.get(baseUrl + "login/");
            Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
            driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
            driver.findElement(By.id("ctl00_content_all")).click();
            driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
            driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys("123");
            driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

            //find failed trx id and open details
            idTransaction = driver.findElement(By.xpath("//tr[contains(@id, 'tr')]")).findElement(By.tagName("a")).getText();
            driver.findElement(By.linkText(idTransaction)).click();

            //go to audit page
            //driver.findElement(By.xpath(".*//*//**//*[@id='view-top']/div[5]/div/a")).click();
            driver.findElement(By.linkText("Аудит")).click();

            // Check if Callback sent
            new Select(driver.findElement(By.id("ctl00_content_filter_objectType"))).selectByVisibleText("CallBack");
            new Select(driver.findElement(By.id("ctl00_content_filter_actionType"))).selectByVisibleText("Отправка данных");


            driver.findElement(By.name("ctl00$content$filter$cmdSelect")).click();
            driver.findElement(By.xpath("//table[@id='list_transactions']/tbody/tr[2]/td[2]/a")).click();

            // TODO - add more details + refactor!!!
            /*String text = driver.findElement(By.xpath(".*//**//*//**//**//**//*[@id='gray_border']")).getText();
            Assert.assertTrue(text.contains(idTransaction), "Failure audit information!");
            Assert.assertTrue(text.contains("123"), "Failure audit information!");
            Assert.assertTrue(text.contains(failAmount), "Failure audit information!");
            Assert.assertTrue(text.contains(currencyRUB), "Failure audit information!");
            Assert.assertTrue(text.contains(cardHolderName), "Failure audit information!");*/
    }
}
