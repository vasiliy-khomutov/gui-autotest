package gateways;

import critical.TestUtils;
import model.Captcha;
import model.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PastaSapmax {

    private long id = System.currentTimeMillis();
    private String idTransaction;
    private String baseUrl;
    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin;
    private String passwordAdmin;

    private String pendingMercahnt = "#63868 - www.pasta-test-sapmax.com";
    private String optionPendingMerchant = "option[value=\"63868\"]";
    private String MIDpending = "63868";

    private String pendingMercahntUrl = "http://www.pasta-test-sapmax.com";
    private String pendingStatus = "Pending";
    private String typePurchase = "Purchase";

    private String preAuthMercahnt = "#63869 - www.pasta-test2-sapmax.com";
    private String optionPreAuthMerchant = "option[value=\"63869\"]";
    private String MIDpreAuth = "63869";

    private String preAuthMercahntUrl = "http://www.pasta-test2-sapmax.com";
    private String preauthStatus = "PreAuthorized";
    //private String typePreauth = "PreAuth";

    private String currencyRUB = "RUB";

    private String amount = "444";
    private String partialCompleteAmount = "44";

    private String orderID = "GUI-autotest";
    private String email = "autoTEST@test.test";
    private String numberCardA = "5444";
    private String numberCardB = "8707";
    private String numberCardC = "2449";
    private String numberCardD = "3746";
    private String expDateMonth = "05";
    private String expDateYear = "2015";
    private String expDate = "05 / 2015";
    private String cardHolderName = "MR.AUTOTEST";
    private String cvc = "111";
    private String bank = "QA-BANK";

    private String testGateway = "Pasta (Medoro) Test";
    private String cardType = "MasterCard";

    private String sapmaxMerchantId = "3199";
    private String lastActionComplete = "Complete";

    @BeforeTest
    public void createParameters(){

        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
    }

    @Test (enabled = false)
    public void pendingTransaction(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, Captcha.getCaptcha(driver));

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, null,
                pendingMercahnt, optionPendingMerchant, id + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, pendingMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(pendingMercahntUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, Captcha.getCaptcha(driver));
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName, amount, amount, testGateway, email);

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount, amount, testGateway, cardHolderName, email);
    }

    @Test (enabled = false)
    public void preAuthTransactionPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, Captcha.getCaptcha(driver));

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, null, preAuthMercahnt, optionPreAuthMerchant,
                id + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, Captcha.getCaptcha(driver));
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName, amount, amount, testGateway, email);

        // partial preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(partialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName, partialCompleteAmount, partialCompleteAmount, testGateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, partialCompleteAmount, partialCompleteAmount, testGateway, cardHolderName, email);
    }

    @Test (enabled = true)
    public void preAuthTransactionFull(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, Captcha.getCaptcha(driver));

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, null, preAuthMercahnt,
                optionPreAuthMerchant, id + "1" + orderID , amount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + "1" + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, Captcha.getCaptcha(driver));
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + "1" + orderID, typePurchase, preauthStatus,
                cardHolderName, amount, amount, testGateway, email);

        // full preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + "1" + orderID, typePurchase, pendingStatus,
                cardHolderName, amount, amount, testGateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + "1" + orderID, lastActionComplete, pendingStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amount, testGateway, cardHolderName, email);
    }
}
