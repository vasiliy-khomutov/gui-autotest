package model;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static void login (WebDriver driver, String baseUrl, String login, String password) {
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, login, password, Captcha.getCaptcha(driver));
    }

    public static boolean checkResultPageTransaction(WebDriver driver, String parameter){
        for (WebElement e: driver.findElements(By.tagName("td"))){
              if(e.getText().contains(parameter)){
                  return true;
              }
        }
        return false;
    }

    public static String getIdTransaction(WebDriver driver){
        if (!(driver.findElements(By.xpath("//tr[6]/td[2]")).size() > 0)){
            return "failed";
        }
        return driver.findElement(By.xpath("//tr[6]/td[2]")).getText();
    }

    public static boolean checkTransactionInLK(WebDriver driver, String id){
        for (WebElement e:driver.findElements(By.tagName("td"))){
            if (e.getText().contains(id)){
                return  true;
            }
        }
        return false;
    }

    public static void authorized(WebDriver driver, String login, String password, String captcha) {
        driver.findElement(By.id("ctl00_content_tbLogin")).clear();
        driver.findElement(By.id("ctl00_content_tbLogin")).sendKeys(login);
        driver.findElement(By.id("ctl00_content_tbPassword")).clear();
        driver.findElement(By.id("ctl00_content_tbPassword")).sendKeys(password);
        driver.findElement(By.id("ctl00_content_cptCaptcha_captchaText")).clear();
        driver.findElement(By.id("ctl00_content_cptCaptcha_captchaText")).sendKeys(captcha);
        driver.findElement(By.id("ctl00_content_ibtnLogon")).click();
    }

    /*public static boolean universalCheck(WebDriver driver, String tag, String parameter){
        for(WebElement e: driver.findElements(By.tagName(tag))){
            if(e.getText().contains(parameter)){
                return true;
            }
        }
        return false;
    }*/

    public static boolean universalCheck(WebDriver driver, String flag, String selector, String parameter){
        if(flag.equals("css")){
            return driver.findElement(By.cssSelector(selector)).getText().equals(parameter);
        }
        if(flag.equals("linkText")){
            return driver.findElement(By.linkText(selector)).getText().equals(parameter);
        }
        if(flag.equals("xpath")){
            if(parameter == null){
                return !(driver.findElement(By.xpath(selector)).getText().isEmpty());
            }else{
                return driver.findElement(By.xpath(selector)).getText().contains(parameter);
            }
        }
        return false;
    }

    public static String getA2SSecurityKey(String...params){
        StringBuilder result = new StringBuilder();
        for(String param : params){
            result.append(param).append("&");
        }
        String source = result.substring(0, result.toString().length() - 1);
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(source.getBytes());
            String md5 = convertToHex(md.digest());
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertToHex(byte[] srcData) {
        StringBuilder sbuffer = new StringBuilder();
        for (int i = 0; i < srcData.length; i++) {
            int flag = (srcData[i] >>> 4) & 0x0F;
            int twoHalf = 0;
            do {
                if ((0 <= flag) && (flag <= 9)) {
                    sbuffer.append((char) ('0' + flag));
                } else {
                    sbuffer.append((char) ('a' + (flag - 10)));
                }
                flag = srcData[i] & 0x0F;
            } while (twoHalf++ < 1);
        }
        return sbuffer.toString();
    }

    public static String getUSDAmount(String amount, String currency) {
        // TODO - Currency Exchange service
        // rouble
        if(currency.equals("RUB")){
            Connect connectDb = new Connect();
            float rate = connectDb.getRate("USD");
            float amountRUB = Float.parseFloat(amount);
            float amountUSD = amountRUB/rate;
            return Float.toString(round(amountUSD, 2)).replace(".",",");
        }
        // usd
        if(currency.equals("USD")){
            return amount.toString();
        }
        // euro
        if(currency.equals("EUR")){
            Connect connectDb = new Connect();
            float rateUSD = connectDb.getRate("USD");
            float rateEUR = connectDb.getRate("EUR");
            float amountRUB = Float.parseFloat(amount) * rateEUR;
            float amountUSD = amountRUB / rateUSD;
            return Float.toString(round(amountUSD, 2)).replace(".",",");
        }
        System.out.println("Incorrect Currency!");
        return null;
    }

    private static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    }

    public static void checkVoidedForm(WebDriver driver,String MID, String idTransaction, String orderId, String cardHolderName, String status) {
        //TODO amount and parameters + refactor
        //1
        // assert in test
        WebElement element = driver.findElement(By.className("infoBlock"));
        Assert.assertTrue(element.findElement(By.xpath("//input[@id ='ctl00_content_voidTransaction_cmdCancel']")).isDisplayed(), "Failure voidedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[3]")).getText().contains(MID),"Failure voidedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[4]")).getText().contains(idTransaction),"Failure voidedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[5]")).getText().contains(orderId),"Failure voidedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[13]")).getText().contains(cardHolderName),"Failure voidedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[8]")).getText().contains(status),"Failure voidedForm!");
    }

    //TODO refactor #1
    public static boolean checkMessage(WebDriver driver){
        return driver.findElement(By.id("ctl00_content_voidTransaction_lblResultMessage")).isDisplayed();
    }

    public static void checkChargeBackForm(WebDriver driver, String idTransaction, String amount, String status, String gateway, String cardHolderName){

        WebElement chargeBackForm = driver.findElement(By.id("mainContent"));
        Assert.assertNotNull(chargeBackForm.findElement(By.linkText(idTransaction)), "Failure chargeBack form!");
        Assert.assertTrue(chargeBackForm.findElement(By.name("ctl00$content$editor$amount")).getAttribute("value").contains(amount), "Failure chargeBack form!");
        Assert.assertTrue(chargeBackForm.findElement(By.xpath("//tr[4]")).getText().contains(status), "Failure chargeBack form!");
        Assert.assertTrue(chargeBackForm.findElement(By.xpath("//tr[5]")).getText().contains(gateway), "Failure chargeBack form!");
        Assert.assertTrue(chargeBackForm.findElement(By.name("ctl00$content$editor$clientName")).getAttribute("value").contains(cardHolderName), "Failure chargeBack form!");
    }

    //TODO refactor #1
    public static String getIdTransactionCharge(WebDriver driver, String idTransaction) {
        driver.findElement(By.linkText(idTransaction)).click();
        return driver.findElement(By.id("Table1")).findElement(By.className("gray")).getAttribute("id");
    }

    public static boolean checkChargeBackImgAdmin(WebDriver driver,String idTransaction){
        return driver.findElement(By.id("tr"+idTransaction)).findElement(By.className("Settled")).findElement(By.tagName("img")).getAttribute("title").contains("Chargeback");
    }

    public static void checkCardTransactionChagreBackAdmin(WebDriver driver, String MID, String idTransaction, String orderId, String lastAction, String statusTransaction,
                                                           String cause, String cbcode, String cbindicator, String cardType, String numberCard,String expDate, String bank,
                                                           String amount, String amountTR,String gateway){
        driver.findElement(By.linkText(idTransaction)).click();
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[1]/td", MID), "Incorrect MID field on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[2]/td", null), "TRX date field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[3]/td", null), "Payment field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[5]/td", idTransaction), "Incorrect TRX ID field on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[6]/td", null), "Acquirer TRX ID field is empty on transaction card (Admin Login).") ;
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[7]/td", orderId), "Order ID field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[8]/td", lastAction), "Last action field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[9]/td", cause), "Cause field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[10]/td", cbcode), "CBcode field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[11]/td", cbindicator), "CBindicator field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[12]/td", statusTransaction), "Status transaction field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[15]/td", cardType), "Card type field is empty on transaction card (Admin Login).");
        driver.findElement(By.linkText("полный номер")).click();
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='pan-view']", numberCard), "Card number field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", expDate), "Expire date field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[18]/td", bank), "Bank name field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[20]/td", amount), "Amount field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[21]/td", amountTR), "TRX amount field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[22]/td", gateway), "Gateway field is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[23]/td", null), "Original transaction is empty on transaction card (Admin Login).");
        Assert.assertTrue(universalCheck(driver, "linkText", "Зарегистрировать Retrieval Request", "Зарегистрировать Retrieval Request"), "Incorrect Retrieval Request link/text (Admin Login).");
    }

    public static boolean checkChargeBackImgMerchant(WebDriver driver,String idTransaction){
        return driver.findElement(By.id("tr"+idTransaction)).findElement(By.tagName("span")).getAttribute("title").contains("Chargeback");
    }

    public static void checkCardTransactionChargeBackMerchant(WebDriver driver, String MID, String idTransaction, String orderId, String typeTransaction, String cause,  String statusTransaction,
                                                              String cardHolderName, String amount, String amountTR, String gateway, String email){
        driver.findElement(By.linkText(idTransaction)).click();
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[1]/td", null), "Transaction date field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[3]/td", MID), "Incorrect MID on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[4]/td", idTransaction), "Incorrect TRX ID on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[5]/td", orderId), "Order ID field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[7]/td", typeTransaction), "Type field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[8]/td", cause), "Cause field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[9]/td", statusTransaction), "Status field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[10]/td", null), "Code field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[14]/td", cardHolderName), "Incorrect Cardholder field on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[15]/td", amount), "Amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[16]/td", amountTR), "TRX amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", gateway), "Gateway field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[1]/td", cardHolderName), "Incorrect Cardholder field on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[7]/td", email), "Incorrect email field on transaction card (Merchant Login).");
    }

    public static void checkCompletedPreauth(WebDriver driver, String MID,  String idTransaction, String orderId, String type, String statusTransaction, String cardHolderName,
                                             String amount, String amountTr, String gateway, String email){

        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[1]", null), "Transaction date field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[3]", MID), "Incorrect MID on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[4]", idTransaction), "Incorrect TRX ID on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[5]", orderId), "Order ID field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[7]", type), "Type field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[8]", statusTransaction), "Status field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[9]", null), "Code field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[10]", null), "Message field is empty on transaction card (Merchant Login).");

        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[12]", null), "Card number field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[13]", cardHolderName), "Incorrect Cardholder field on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[14]", amount), "Amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[15]", amountTr), "TRX amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[16]", gateway), "Gateway field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[17]", null), "IP field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[18]", null), "Country field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[19]", null), "Issuer country field is empty on transaction card (Merchant Login).");

    }

    //TODO refactor #2
    public static String getIdTransactionRefund(WebDriver driver, String idTransaction, String amount) {
        driver.findElement(By.linkText(idTransaction)).click();
        WebElement element = driver.findElement(By.id("refunds"));
        for (WebElement e: element.findElements(By.tagName("tr"))){
               if(e.getText().contains(amount)){
                   return e.findElement(By.tagName("a")).getText();
               }
        }
        return null;
    }
}
