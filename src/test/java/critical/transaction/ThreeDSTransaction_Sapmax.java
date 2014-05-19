package critical.transaction;


import critical.TestUtils;
import model.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ThreeDSTransaction_Sapmax {

    private long id = System.currentTimeMillis();

    private String idTransaction;
    private String baseUrl;


    private String loginMerchant;
    private String passwordMerchant;

    private String loginAdmin;
    private String passwordAdmin;

    private String captcha = "ability";

    private String pendingMercahnt3DS = "#59523 - www.3DS-trx-sapmax.ru";
    private String optionPendingMerchant3DS = "option[value=\"59523\"]";
    private String MIDpending3DS = "59523";
    private String pendingMercahnt3DSUrl = "http://www.3ds-trx-sapmax.ru/";
    private String pendingStatus = "Pending";
    private String typePurchase = "Purchase";

    private String preAuthMercahnt3DS = "#59529 - www.3DS-trx-sapmax2.ru";
    private String optionPreAuthMerchant3DS = "option[value=\"59529\"]";
    private String MIDpreAuth3DS = "59529";
    private String preAuthMercahnt3DSUrl = "http://www.3ds-trx-sapmax2.ru";
    private String preauthStatus = "PreAuthorized";
    //private String lastActionPreAuth = "PreAuth";

    private String currencyRUB = "RUB";
    private String amount = "888";
    private String partialCompleteAmount = "88";

    private String orderID = "GUI-autotest";
    private String email = "autoTEST@test.test";
    private String numberCardA = "4111";
    private String numberCardB = "1111";
    private String numberCardC = "1111";
    private String numberCardD = "1111";
    private String expDateMonth = "05";
    private String expDateYear = "2015";
    private String expDate = "05 / 2015";
    private String cardHolderName = "MR.AUTOTEST";
    private String cvc = "111";
    private String bank = "QA-BANK";

    private String testGateway = "Test gateway";
    private String cardType = "Visa";
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

    @Test
    public void threeDSTransactionPending(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha, pendingMercahnt3DS, optionPendingMerchant3DS,
                id + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(pendingMercahnt3DSUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName, amount, amount, testGateway, email);
        Assert.assertTrue(TestUtils.checkAttribute3DSMerchantSapmax(driver, idTransaction));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idTransaction, id + orderID, typePurchase, pendingStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amount, testGateway, cardHolderName, email);

        Assert.assertTrue(TestUtils.checkAttribute3DSAdminSapmax(driver, idTransaction));
    }

    @Test
    public void preAuthTransactionPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha, preAuthMercahnt3DS, optionPreAuthMerchant3DS,
                id + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahnt3DSUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName, amount, amount, testGateway, email);
        Assert.assertTrue(TestUtils.checkAttribute3DSMerchantSapmax(driver, idTransaction));

        // partial preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(partialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        TestUtils.checkCompletedPreauth(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName, partialCompleteAmount, partialCompleteAmount, testGateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, partialCompleteAmount, partialCompleteAmount, testGateway, cardHolderName, email);


    }

    @Test
    public void preAuthTransactionFull(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha, preAuthMercahnt3DS, optionPreAuthMerchant3DS,
                id + "1" + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + "1" + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahnt3DSUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + "1" + orderID, typePurchase, preauthStatus, cardHolderName, amount, amount, testGateway, email);
        Assert.assertTrue(TestUtils.checkAttribute3DSMerchantSapmax(driver, idTransaction));

        // full preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        TestUtils.checkCompletedPreauth(driver, MIDpreAuth3DS, idTransaction, id + "1" + orderID, typePurchase, pendingStatus, cardHolderName, amount, amount, testGateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, id + "1" + orderID, lastActionComplete, pendingStatus, cardType,
                                                     numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amount, testGateway, cardHolderName, email);
    }
}
