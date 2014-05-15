package gateways;

import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.lang.reflect.Method;

public class TestUtils {

    public static String getA2SIdTransactionLink(String...params){
        StringBuilder result = new StringBuilder();
        for(String param : params){
            result.append(param).append("&");
        }
        result.delete(44,45);
        String source = result.substring(0, result.toString().length() - 1);
        source = source.replace(" ","+");
        return source;
    }
    // TODO - to be refactored
    public static String getA2STransactionId(WebDriver driver, String amount, String MerchantId){
        if(driver.findElements(By.xpath(".//*[@id='list_transactions']//tr[2]//td[8]")).size() > 0) {
            if (driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[8]")).getText().contains(amount) &&
                driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[5]")).getText().contains(MerchantId)) {
                return driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[2]//a[1]")).getText();
            }
        }
        if (driver.findElements(By.xpath(".//*[@id='list_transactions']//tr[3]//td[8]")).size() > 0) {
            if (driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[3]//td[8]")).getText().contains(amount) &&
                driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[3]//td[5]")).getText().contains(MerchantId)) {
                return driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[3]//td[2]//a[1]")).getText();
            }
            if (driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[8]")).getText().contains(amount) &&
                driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[5]")).getText().contains(MerchantId)) {
                return driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[2]//a[1]")).getText();
            }
        }
        if (driver.findElements(By.xpath(".//*[@id='list_transactions']//tr[4]//td[8]")).size() > 0) {
            if (driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[4]//td[8]")).getText().contains(amount) &&
                driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[4]//td[5]")).getText().contains(MerchantId)) {
                return driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[4]//td[2]//a[1]")).getText();
            }
            if (driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[3]//td[8]")).getText().contains(amount) &&
                driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[3]//td[5]")).getText().contains(MerchantId)) {
                return driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[3]//td[2]//a[1]")).getText();
            }
            if (driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[8]")).getText().contains(amount) &&
                driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[5]")).getText().contains(MerchantId)) {
                return driver.findElement(By.xpath(".//*[@id='list_transactions']//tr[2]//td[2]//a[1]")).getText();
            }
        }
        System.out.println("A2S transaction id not found!");
        return null;
    }

    public static void initiateA2STransactionId(WebDriver driver, String numberCardA, String numberCardB, String numberCardC, String numberCardD,
                                                String expDateMonth, String expDateYear, String cardHolderName, String cvc, String bank, String email){

        // type value in  payment form
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

    public static boolean checkA2STransactionResultPage(WebDriver driver, String parameter){
        for (WebElement e: driver.findElements(By.tagName("td"))){
            if(e.getText().contains(parameter)){
                return true;
            }
        }
        return false;
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
        universalTrxCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[6]/td");
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

    private static boolean universalTrxCheck(WebDriver driver, String flag, String selector){
        if(driver.findElement(By.xpath(".//*[@id='view-top']/table[1]/tbody/tr[9]/td")).getText().equals("Declined")){
            return driver.findElement(By.xpath(".//*[@id='view-top']/table[1]/tbody/tr[6]/td")).getText().isEmpty();
        }else{
            return Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[9]/td", null);
        }
    }

}
