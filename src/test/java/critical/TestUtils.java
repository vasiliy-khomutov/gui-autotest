package critical;


import model.Environment;
import model.Utils;
import org.apache.http.HttpRequest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils {

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

    public static String getSecurityKey(String...params){
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

    public static void paymentTransaction(WebDriver driver, String merchant, String optionMerchant,String currency,  String orderId, String amount, String email,
                                              String numberCardA, String numberCardB, String numberCardC, String numberCardD, String expDateMonth, String expDateYear,
                                              String cardHolderName, String cvc, String bank) {

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
        driver.findElement(By.xpath("//*[@id = 'ctl00_content_cardForm_cardNumberA']")).clear();
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
        driver.findElement(By.id("ctl00_content_cardForm_email")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_email")).sendKeys(email);
        driver.findElement(By.id("ctl00_content_cardForm_cmdProcess")).click();
    }


    public static String getNewIdTransaction(WebDriver driver, String merchant, String optionMerchant, String orderId, String amount,
                String numberCardA, String numberCardB, String numberCardC, String numberCardD, String expDateMonth, String expDateYear, String cvc,
                String bank, String email, String currency, String cardHolderName){

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
        driver.findElement(By.xpath("//*[@id = 'ctl00_content_cardForm_cardNumberA']")).clear();
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
        driver.findElement(By.id("ctl00_content_cardForm_email")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_email")).sendKeys(email);
        driver.findElement(By.id("ctl00_content_cardForm_cmdProcess")).click();
        return Utils.getIdTransaction(driver);
    }

    public static String getNewIdTransactionSapmax(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String captcha, String merchant, String optionMerchant, String orderId, String amount,
                                                   String numberCardA, String numberCardB, String numberCardC, String numberCardD, String expDateMonth, String expDateYear, String cvc,
                                                   String bank, String email, String currency, String cardHolderName, String sapmaxMerchantId){

        generateTransaction(driver, merchant, optionMerchant, currency, amount, orderId, email);

        //type value in  payment form
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        driver.findElement(By.xpath("//*[@id = 'ctl00_content_cardForm_cardNumberA']")).clear();
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
        driver.findElement(By.id("ctl00_content_cardForm_email")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_email")).sendKeys(email);
        driver.findElement(By.id("ctl00_content_cardForm_cmdProcess")).click();

        // select currency and continue
        new Select(driver.findElement(By.name("currency"))).selectByVisibleText("USD");
        new Select(driver.findElement(By.name("currency"))).selectByVisibleText("EUR");
        new Select(driver.findElement(By.name("currency"))).selectByVisibleText("RUB");
        driver.findElement(By.xpath(".//*[@id='mainContent']/div/div[3]/form[1]/input[3]")).click();

        //get random amount value from admin backend
        String randomAmount = getRandomAmountValue(baseUrl, loginAdmin, passwordAdmin, captcha, sapmaxMerchantId);

        // enter ra
        driver.findElement(By.id("ctl00_content_amount")).clear();
        driver.findElement(By.id("ctl00_content_amount")).sendKeys(randomAmount);
        driver.findElement(By.id("ctl00_content_cmdProcess")).click();

        return Utils.getIdTransaction(driver);
    }

    public static String getNewIdTransaction3DS(WebDriver driver, String merchant, String optionMerchant, String orderId, String amount,
                                             String numberCardA, String numberCardB, String numberCardC, String numberCardD, String expDateMonth, String expDateYear, String cvc,
                                             String bank, String email, String currency, String cardHolderName){

        generateTransaction(driver, merchant, optionMerchant, currency, amount, orderId, email);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        driver.findElement(By.xpath("//*[@id = 'ctl00_content_cardForm_cardNumberA']")).clear();
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
        driver.findElement(By.id("ctl00_content_cardForm_email")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_email")).sendKeys(email);
        driver.findElement(By.id("ctl00_content_cardForm_cmdProcess")).click();
        driver.findElement(By.cssSelector("input.process")).click();
        TestUtils.closeAlertAndGetItsText(driver);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        return Utils.getIdTransaction(driver);
    }

    public static void checkCardTransactionMerchant(WebDriver driver, String MID,  String idTransaction, String orderId, String type, String statusTransaction, String cardHolderName,
                                                    String amount, String amountTr, String gateway, String email){

        driver.findElement(By.linkText(idTransaction)).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[1]/td", null), "Transaction date field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[3]/td", MID), "Incorrect MID on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[4]/td", idTransaction), "Incorrect TRX ID on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[5]/td", orderId), "Order ID field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[7]/td", type), "Type field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[8]/td", statusTransaction), "Status field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[9]/td", null), "Code field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[10]/td", null), "Message field is empty on transaction card (Merchant Login).");

        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[12]/td", null), "Card number field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[13]/td", cardHolderName), "Incorrect Cardholder field on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[14]/td", amount), "Amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[15]/td", amountTr), "TRX amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[16]/td", gateway), "Gateway field is empty on transaction card (Merchant Login).");
        //TODO refactor for api transaction!!!!
        //Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", null), "IP field is empty on transaction card (Merchant Login).");
        //Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[18]/td", null), "Country field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[19]/td", null), "Issuer country field is empty on transaction card (Merchant Login).");

        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[1]/td", cardHolderName), "Incorrect Cardholder field on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[7]/td", email), "Incorrect email field on transaction card (Merchant Login).");
    }

    public static void checkCardTransactionAdmin(WebDriver driver, String MID, String idTransaction, String orderID, String lastAction, String statusTransaction, String cardType,
                                                 String cardNumber, String expDate, String bank, String amount, String amountTr, String gateway, String cardHolderName, String email){

        driver.findElement(By.linkText(idTransaction)).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[1]/td", MID), "Incorrect MID field on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[2]/td", null), "TRX date field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[5]/td", idTransaction), "Incorrect TRX ID field on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[6]/td", null), "Acquirer TRX ID field is empty on transaction card (Admin Login).") ;
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[7]/td", orderID), "Order ID field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[8]/td", lastAction), "Last action field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[9]/td", statusTransaction), "TRX status field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[10]/td", null), "Condition field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[11]/td", null), "Code field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[12]/td", cardType), "Card type field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[13]/td", null), "Card number field is empty on transaction card (Admin Login).");
        driver.findElement(By.linkText("полный номер")).click();
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='pan-view']", cardNumber), "Full PAN field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[14]/td", expDate), "Expire date field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[15]/td", bank), "Bank name field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", amount), "Amount field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[18]/td", amountTr), "TRX amount field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[19]/td", gateway), "Gateway field is empty on transaction card (Admin Login).");

        // Buyer info
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[1]/td", cardHolderName), "Incorrect cardholder field on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[7]/td", null), "Country (IP) field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='tran-email']/a", email), "Incorrect email field on transaction card (Admin Login).");
        //TODO for transaction api!!!!!
        //Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[10]/td", null), "IP field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[11]/td", null), "UserAgent field is empty on transaction card (Admin Login).");
        //Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[12]/td", null), "Browser language field is empty on transaction card (Admin Login).");
        //Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[13]/td", null), "Display Info field is empty on transaction card (Admin Login).");
        //Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[14]/td", null), "Local time field is empty on transaction card (Admin Login).");
    }

    public static void checkCardTransactionChargeBackMerchant(WebDriver driver, String MID, String idTransaction, String orderId, String typeTransaction, String cause,  String statusTransaction,
                                                              String cardHolderName, String amount, String amountTR, String gateway, String email){
        driver.findElement(By.linkText(idTransaction)).click();
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[1]/td", null), "Transaction date field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[3]/td", MID), "Incorrect MID on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[4]/td", idTransaction), "Incorrect TRX ID on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[5]/td", orderId), "Order ID field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[7]/td", typeTransaction), "Type field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[8]/td", cause), "Cause field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[9]/td", statusTransaction), "Status field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[10]/td", null), "Code field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[14]/td", cardHolderName), "Incorrect Cardholder field on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[15]/td", amount), "Amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[16]/td", amountTR), "TRX amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", gateway), "Gateway field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[1]/td", cardHolderName), "Incorrect Cardholder field on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[7]/td", email), "Incorrect email field on transaction card (Merchant Login).");
    }

    public static void checkCardTransactionChagreBackAdmin(WebDriver driver, String MID, String idTransaction, String orderId, String lastAction, String statusTransaction,
                                                           String cause, String cbcode, String cbindicator, String cardType, String numberCard,String expDate, String bank,
                                                           String amount, String amountTR,String gateway){
        driver.findElement(By.linkText(idTransaction)).click();
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[1]/td", MID), "Incorrect MID field on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[2]/td", null), "TRX date field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[3]/td", null), "Payment field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[5]/td", idTransaction), "Incorrect TRX ID field on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[6]/td", null), "Acquirer TRX ID field is empty on transaction card (Admin Login).") ;
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[7]/td", orderId), "Order ID field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[8]/td", lastAction), "Last action field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[9]/td", cause), "Cause field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[10]/td", cbcode), "CBcode field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[11]/td", cbindicator), "CBindicator field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[12]/td", statusTransaction), "Status transaction field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[15]/td", cardType), "Card type field is empty on transaction card (Admin Login).");
        driver.findElement(By.linkText("полный номер")).click();
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='pan-view']", numberCard), "Card number field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", expDate), "Expire date field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[18]/td", bank), "Bank name field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[20]/td", amount), "Amount field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[21]/td", amountTR), "TRX amount field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[22]/td", gateway), "Gateway field is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[23]/td", null), "Original transaction is empty on transaction card (Admin Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "linkText", "Зарегистрировать Retrieval Request", "Зарегистрировать Retrieval Request"), "Incorrect Retrieval Request link/text (Admin Login).");
    }

    public static void checkCompletedPreauth(WebDriver driver, String MID,  String idTransaction, String orderId, String type, String statusTransaction, String cardHolderName,
                                             String amount, String amountTr, String gateway, String email){

        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[1]", null), "Transaction date field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[3]", MID), "Incorrect MID on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[4]", idTransaction), "Incorrect TRX ID on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[5]", orderId), "Order ID field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[7]", type), "Type field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[8]", statusTransaction), "Status field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[9]", null), "Code field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[10]", null), "Message field is empty on transaction card (Merchant Login).");

        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[12]", null), "Card number field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[13]", cardHolderName), "Incorrect Cardholder field on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[14]", amount), "Amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[15]", amountTr), "TRX amount field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[16]", gateway), "Gateway field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[17]", null), "IP field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[18]", null), "Country field is empty on transaction card (Merchant Login).");
        Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='mainContent']/div[5]/table/tbody/tr[19]", null), "Issuer country field is empty on transaction card (Merchant Login).");

    }


    public static void checkChargeBackForm(WebDriver driver, String idTransaction, String amount, String status, String gateway, String cardHolderName){

        WebElement chargeBackForm = driver.findElement(By.id("mainContent"));
        Assert.assertNotNull(chargeBackForm.findElement(By.linkText(idTransaction)), "Failure chargeBack form!");
        Assert.assertTrue(chargeBackForm.findElement(By.name("ctl00$content$editor$amount")).getAttribute("value").contains(amount), "Failure chargeBack form!");
        Assert.assertTrue(chargeBackForm.findElement(By.xpath("//tr[4]")).getText().contains(status), "Failure chargeBack form!");
        Assert.assertTrue(chargeBackForm.findElement(By.xpath("//tr[5]")).getText().contains(gateway), "Failure chargeBack form!");
        Assert.assertTrue(chargeBackForm.findElement(By.name("ctl00$content$editor$clientName")).getAttribute("value").contains(cardHolderName), "Failure chargeBack form!");
    }

    public static boolean checkChargeBackImgAdmin(WebDriver driver,String idTransaction){
        return driver.findElement(By.id("tr"+idTransaction)).findElement(By.className("Settled")).findElement(By.tagName("img")).getAttribute("title").contains("Chargeback");
    }

    public static boolean checkChargeBackImgMerchant(WebDriver driver,String idTransaction){
        return driver.findElement(By.id("tr"+idTransaction)).findElement(By.tagName("span")).getAttribute("title").contains("Chargeback");
    }


    //TODO refactor #1
    public static String getIdTransactionCharge(WebDriver driver, String idTransaction) {
        driver.findElement(By.linkText(idTransaction)).click();
        return driver.findElement(By.id("Table1")).findElement(By.className("gray")).getAttribute("id");
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

    //TODO refactor #2
    public static String getIdTransactionRefund2(WebDriver driver, String idTransaction, String amount) {
        driver.findElement(By.linkText(idTransaction)).click();

        /*WebElement element = driver.findElement(By.xpath("./*//*[@id='refunds']/tbody/tr[3]"));*/
        WebElement element = driver.findElement(By.xpath(".//*[@id='refunds']"));
        return element.findElement(By.className("white")).findElement(By.tagName("a")).getText();

    }

    //TODO refactor #2
    public static String getIdTransactionRefund3(WebDriver driver, String idTransaction, String amount) {
        driver.findElement(By.linkText(idTransaction)).click();

        WebElement element = driver.findElement(By.xpath(".//*[@id='refunds']/tbody/tr[2]"));
        return driver.findElement(By.xpath("//td/table/tbody/tr[3]/td[2]/a")).getText();
    }

    //TODO refactor #1
    public static boolean checkMessage(WebDriver driver){
        return driver.findElement(By.id("ctl00_content_voidTransaction_lblResultMessage")).isDisplayed();
    }

    //TODO refactor #2
    public static boolean checkMessage2(WebDriver driver){
        return driver.findElement(By.id("ctl00_content_completeTransaction_lblResultMessage")).isDisplayed();
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

    public static void checkAcceptedForm(WebDriver driver,String MID, String idTransaction, String orderId, String cardHolderName, String status, String amount) {
        //TODO amount and parameters + refactor
        //2
        WebElement element = driver.findElement(By.className("infoBlock"));
        Assert.assertTrue(driver.findElement(By.xpath("//input[@name ='ctl00$content$completeTransaction$cmdCancel']")).isDisplayed(), "Failure acceptedForm!");
        Assert.assertTrue(driver.findElement(By.name("ctl00$content$completeTransaction$amount")).getAttribute("value").contains(amount), "Failure acceptedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[3]")).getText().contains(MID),"Failure acceptedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[4]")).getText().contains(idTransaction),"Failure acceptedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[5]")).getText().contains(orderId),"Failure acceptedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[13]")).getText().contains(cardHolderName),"Failure acceptedForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[8]")).getText().contains(status),"Failure acceptedForm!");
    }

    public static void checkRefundFormMerchant(WebDriver driver, String MID, String idTransaction, String orderId, String cardHolderName, String status, String amount) {
        //TODO amount and parameters + refactor
        //3
        WebElement element = driver.findElement(By.className("infoBlock"));
        Assert.assertTrue(driver.findElement(By.xpath("//span[@id ='ctl00_content_refundTransaction_refundMaxAmountInfo']")).isDisplayed(), "Failure refundForm!");
        Assert.assertTrue(driver.findElement(By.id("ctl00_content_refundTransaction_refundMaxAmountInfo")).getText().contains(amount), "Failure refundForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[3]")).getText().contains(MID),"Failure refundForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[4]")).getText().contains(idTransaction),"Failure refundForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[5]")).getText().contains(orderId),"Failure refundForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[13]")).getText().contains(cardHolderName),"Failure refundForm!");
        Assert.assertTrue(element.findElement(By.xpath("//tr[8]")).getText().contains(status),"Failure refundForm!");
    }

    public static void checkRefundFormAdmin(WebDriver driver, String refundAmount, String residueAmount) {
        //TODO amount and parameters + refactor
        WebElement element = driver.findElement(By.id("refund-action"));
        Assert.assertTrue(element.findElement(By.id("ctl00_content_actions_lblRefundedAmount")).getText().contains(refundAmount),"Failure refundFormAdmin!");
        Assert.assertTrue(element.findElement(By.id("ctl00_content_actions_lblMaxRefundAmount")).getText().contains(residueAmount.replace(",", ".")), "Failure refundFormAdmin!");
    }

    public static boolean checkVoidedMessage(WebDriver driver) {
        return driver.findElement(By.xpath("//div[@id = 'void-n-refund']")).getText().contains("Транзакция отменена");
    }

    public static void keyPressEnter() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static String closeAlertAndGetItsText(WebDriver driver) {
        boolean acceptNextAlert = true;
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    public static boolean checkAttribute3DSMerchant(WebDriver driver, String idTransaction){

        if(driver.findElement(By.id( "tr5" + idTransaction)).isDisplayed()&
                driver.findElement(By.xpath(".//*[@id='view-top']/table[1]/tbody/tr[11]/td")).getText().contains("Full 3D-Secure")&
                    driver.findElement(By.xpath("//li[@class='info']")).getText().contains("Пройдена 3-D Secure")){
            return true;
        }
        return false;
    }

    public static boolean checkAttribute3DSAdmin(WebDriver driver, String idTransaction){
        if( driver.findElement(By.xpath(".//*[@id='view-top']/table[1]/tbody/tr[10]/td")).getText().contains("Full 3D-Secure")&
                driver.findElement(By.xpath("//li[@class='info']")).getText().contains("Full 3-D Secure")){
            return true;
        }
        return false;
    }

    private static String getRandomAmountValue(String baseUrl, String loginAdmin, String passwordMerchant, String captcha, String sapmaxMerchantId) {
        String ra;

        // TODO - replace driver with new window
        //WebDriver driver = new FirefoxDriver();
        WebDriver driver = Environment.createDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordMerchant, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_filter_merchantId")).sendKeys(sapmaxMerchantId);
        driver.findElement(By.id("ctl00_content_filter_cdRandomAmount")).sendKeys(Keys.SPACE);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        ra = driver.findElement(By.className("sum_col")).getText();

        ra = ra.replace("р.","");
        // TODO add usd and euro replace

        ra = ra.replace(",",".");
        ra = ra.replace(" ","");

        driver.quit();
        return ra;

    }


    public static String getNewIdTransaction3DSSapmax(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String captcha, String merchant, String optionMerchant, String orderId, String amount,
                                                      String numberCardA, String numberCardB, String numberCardC, String numberCardD, String expDateMonth, String expDateYear, String cvc,
                                                      String bank, String email, String currency, String cardHolderName, String sapmaxMerchantId){

        generateTransaction(driver, merchant, optionMerchant, currency, amount, orderId, email);

        //type value in  payment form
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        driver.findElement(By.xpath("//*[@id = 'ctl00_content_cardForm_cardNumberA']")).clear();
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
        driver.findElement(By.id("ctl00_content_cardForm_email")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_email")).sendKeys(email);
        driver.findElement(By.id("ctl00_content_cardForm_cmdProcess")).click();

        driver.findElement(By.cssSelector("input.process")).click();
        TestUtils.closeAlertAndGetItsText(driver);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        // select currency and continue
        new Select(driver.findElement(By.name("currency"))).selectByVisibleText("USD");
        new Select(driver.findElement(By.name("currency"))).selectByVisibleText("EUR");
        new Select(driver.findElement(By.name("currency"))).selectByVisibleText("RUB");
        driver.findElement(By.xpath(".//*[@id='mainContent']/div/div[3]/form[1]/input[3]")).click();

        //get random amount value from admin backend
        String randomAmount = getRandomAmountValue(baseUrl, loginAdmin, passwordAdmin, captcha, sapmaxMerchantId);

        // enter ra
        driver.findElement(By.id("ctl00_content_amount")).clear();
        driver.findElement(By.id("ctl00_content_amount")).sendKeys(randomAmount);
        driver.findElement(By.id("ctl00_content_cmdProcess")).click();

        return Utils.getIdTransaction(driver);
    }

    public static boolean checkAttribute3DSMerchantSapmax(WebDriver driver, String idTransaction){

        if(driver.findElement(By.id( "tr5" + idTransaction)).isDisplayed()&
                driver.findElement(By.xpath(".//*[@id='view-top']/table[1]/tbody/tr[11]/td")).getText().contains("Full 3D-Secure, SapMax")&
                driver.findElement(By.xpath("//li[@class='info']")).getText().contains("Пройдена аутентификация")){
            return true;
        }
        return false;
    }

    public static boolean checkAttribute3DSAdminSapmax(WebDriver driver, String idTransaction){
        if(driver.findElement(By.xpath(".//*[@id='view-top']/table[3]/tbody/tr[11]")).getText().contains("3-D Secure enabled")&
                driver.findElement(By.xpath(".//*[@id='view-top']/table[1]/tbody/tr[10]/td")).getText().contains("Full 3D-Secure")&
                driver.findElement(By.xpath(".//*[@id='view-top']/div[1]/ul/li[2]/em")).getText().contains("Sapmax")){
            return true;
        }
        return false;
    }

    /*public static void waitElementIsVisible(WebDriver driver, String locator){


           // final Wait<WebDriver> wait = new WebDriverWait(driver, 15, 1000).ignoring(ElementNotVisibleException.class, NoSuchElementException.class);
           /// wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator))));
    }*/

    public static String createBodyRequest(String...params){
        StringBuilder result = new StringBuilder();
        for(String param : params){
            result.append(param).append("&");
        }
        result.replace(result.length() - 1, result.length(), "");
        return result.toString();
    }

    public static HttpRequest setHeader(HttpRequest request, String...headers){
        for(String header :headers){
            String[] paramHeader = header.split(":");
            request.setHeader(paramHeader[0], paramHeader[1]);
        }
        return request;
    }

    public static boolean checkParameter(java.util.List<String> response, String parameter){
        if(response.toString().contains(parameter)){
            return true;
        }
        return false;
    }

    public static String getParameter (java.util.List<String> response, String parameter){
        Pattern pattern = Pattern.compile(parameter);
        Matcher matcher = pattern.matcher(response.toString());
        if(matcher.find()){
            return matcher.group(0);
        }
        return null;
    }
}
