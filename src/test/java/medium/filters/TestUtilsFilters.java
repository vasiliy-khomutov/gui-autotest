package medium.filters;

import model.Captcha;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class TestUtilsFilters {

    public static void enableMatchEmailZipFilter(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String MID){

        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
        driver.findElement(By.id("ctl00_content_filter_merchantId")).clear();
        driver.findElement(By.id("ctl00_content_filter_merchantId")).sendKeys(MID);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(MID)).click();
        driver.findElement(By.xpath("//*[@id='tabs']/div[3]/div/a")).click();

        if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[24]")).getText().contains("Настройка")){
            driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[24]/td[2]/a[1]")).click();
        }else {
            driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl23_lnkTurnOn")).click();
        }
        WebElement day = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day"));
        day.clear();
        day.sendKeys("999");
        WebElement hour = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour"));
        hour.clear();
        hour.sendKeys("9");
        WebElement minute = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute"));
        minute.clear();
        minute.sendKeys("9");
        driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_IncludeDeclinedTransactions")).click();

        new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
        driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
    }

    public static void disableMatchEmailZipFilter(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String MID){
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
        WebElement idField = driver.findElement(By.id("ctl00_content_filter_merchantId"));
        idField.clear();
        idField.sendKeys(MID);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(MID)).click();
        driver.findElement(By.xpath("//*[@id='tabs']/div[3]/div/a")).click();
        driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl23_lnkTurnOff")).click();

    }

    public static String getNewIdTransaction_Filter(WebDriver driver, String merchant, String optionMerchant, String orderId, String amount,
                                             String numberCardA, String numberCardB, String numberCardC, String numberCardD, String expDateMonth,
                                             String expDateYear, String cvc, String bank, String address, String city, String zip, String country,
                                             String phone, String email, String currency, String cardHolderName, String baseUrl, String loginAdmin, String passwordAdmin){

        generateTransaction(driver, merchant, optionMerchant, currency, amount, orderId, email);

        //type value in  payment form
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberA")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberA")).sendKeys(numberCardA);
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberB")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberB")).sendKeys(numberCardB);
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberC")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberC")).sendKeys(numberCardC);
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberD")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberD")).sendKeys(numberCardD);
        new Select(driver.findElement(By.id("ctl00_content_cardForm_expDateMonth"))).selectByVisibleText(expDateMonth);
        new Select(driver.findElement(By.id("ctl00_content_cardForm_expDateYear"))).selectByVisibleText(expDateYear);
        driver.findElement(By.id("ctl00_content_cardForm_cardHolderName")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardHolderName")).sendKeys(cardHolderName);
        driver.findElement(By.id("ctl00_content_cardForm_cardCVC")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardCVC")).sendKeys(cvc);
        driver.findElement(By.id("ctl00_content_cardForm_bankName")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_bankName")).sendKeys(bank);
        driver.findElement(By.id("ctl00_content_cardForm_address")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_address")).sendKeys(address);
        driver.findElement(By.id("ctl00_content_cardForm_city")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_city")).sendKeys(city);
        driver.findElement(By.id("ctl00_content_cardForm_zipCode")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_zipCode")).sendKeys(zip);
        new Select(driver.findElement(By.id("ctl00_content_cardForm_country"))).selectByVisibleText(country);
        driver.findElement(By.id("ctl00_content_cardForm_phone")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_phone")).sendKeys(phone);
        driver.findElement(By.id("ctl00_content_cardForm_email")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_email")).sendKeys(email);
        driver.findElement(By.id("ctl00_content_cardForm_cmdProcess")).click();

        String result = getTransactionId(driver, orderId);
        // TODO - "failed" parameter
        if(result.equals("failed")){

            driver.get(baseUrl + "login/");
            Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
            driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
            driver.findElement(By.id("ctl00_content_all")).click();
            driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
            driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(orderId);
            driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
            String result1 = driver.findElement(By.xpath("//table[@id='list_transactions']/tbody/tr[2]/td[2]")).getText();
            driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
            driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
            return result1;
        }
        return result;
    }

    public static void checkTransactionCard_Admin_MatchEmailZip(WebDriver driver, String idTransaction, String email, String zip, String code){

        //driver.findElement(By.linkText(idTransaction)).click();
        waitForLogs(driver, idTransaction);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='tran-email']/a", email), "Incorrect email field on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[4]/td", zip.substring(0, zip.length()-4)), "Incorrect ZIP field on transaction card (Admin Login).");
        Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Match.Email.Zip')]")).getText().contains(code), "Incorrect Match.Email.Zip Result Code.");
        }

    private static void generateTransaction(WebDriver driver, String merchant, String optionMerchant, String currency, String amount, String orderId, String email){

        //generate transaction and open link
        driver.findElement(By.id("ctl00_ctl11_mhlTools")).click();
        new Select(driver.findElement(By.id("ctl00_content_ddlSiteList"))).selectByVisibleText(merchant);
        driver.findElement(By.cssSelector(optionMerchant)).click();
        new Select(driver.findElement(By.id("ctl00_content_ddlCurrencies"))).selectByVisibleText(currency);
        driver.findElement(By.id("ctl00_content_tbAmount")).clear();
        driver.findElement(By.id("ctl00_content_tbAmount")).sendKeys(amount);
        driver.findElement(By.name("ctl00$content$tbOrderId")).clear();
        driver.findElement(By.name("ctl00$content$tbOrderId")).sendKeys(orderId);
        driver.findElement(By.name("ctl00$content$tbCardHolderEmail")).clear();
        driver.findElement(By.name("ctl00$content$tbCardHolderEmail")).sendKeys(email);
        driver.findElement(By.name("ctl00$content$cmdProcess")).click();
        driver.findElement(By.id("ctl00_content_lnkPayment")).click();
        driver.close();
    }

    private static void waitForLogs(WebDriver driver, String idTransaction){

        boolean showed = false;

        // wait 3 second (total waiting time is 1 minute)
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        for(int i = 0; i < 20; i++){
            // click
            driver.findElement(By.linkText(idTransaction)).click();

            // search
            showed = driver.findElements(By.xpath("//span[contains(.,'Match.Email.Zip')]")).size() > 0;

            // if result found - break
            if(showed == true){
                break;
            }

        }
    }


    public static String getTransactionId(WebDriver driver, String orderId){
        // TODO - refactor. return failed trx id
        if (!(driver.findElements(By.xpath("//tr[6]/td[2]")).size() > 0)){
            return "failed";
        }
        return driver.findElement(By.xpath("//tr[6]/td[2]")).getText();
    }
}
