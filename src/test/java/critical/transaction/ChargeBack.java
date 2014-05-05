package critical.transaction;


import critical.TestUtils;
import critical.callbacks.DriverFactory;
import model.Connect;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ChargeBack {

    private String idChargeBack;

    private String lastActionCharge = "Chargeback";
    private String cause = "auto test check";
    private String cbcode = "1";
    private String cbindicator = "1111";

    //private String partialCBKAmount1 = "888,88";
    private String partialCBKAmount2 = "44,44";

    private String typeTransaction = "Chargeback";

    private String idTransaction;

    private String baseUrl;

    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin;
    private String passwordAdmin;
    private String captcha = "ability";

    private String orderID = "";
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

    private String currencyRUB = "RUB";
    private String sapmaxMerchantId = "3199";

    private String typePurchase = "Purchase";

    private String pendingStatus = "Pending";
    private String preAuthStatus = "PreAuth";
    private String preAuthStatus2 = "PreAuthorized";
    private String settledStatus = "Settled";

    // simple

    private String simpleamountPending = "222,22";
    //private String simpleamountPending2 = "1222,22";

    private String simplependingMercahnt = "#57482 - www.test1.ru";
    private String simpleoptionPendingMerchant = "option[value=\"57482\"]";
    private String simpleMIDpending = "57482";

    private String simpleamountPreauth1 = "300";
    private String simpleamountPreauth2 = "224,44";
    //private String simpleamountPreauth4 = "1224,44";
    private String simpleamountPreauthComplete ="224,44";
    private String simpleamountPreauthPart3 = "224,44";

    private String simplepreAuthMercahnt = "#57483 - www.transactions.com";
    private String simpleoptionPreAuthMerchant = "option[value=\"57483\"]";
    private String simpleMIDpreAuth = "57483";

    // simple sapmax

    private String simpleSapmaxamountPending = "444,22";
    //private String simpleSapmaxamountPending2 = "1444,22";

    private String simpleSapmaxamountPreauth1 = "600";
    private String simpleSapmaxamountPreauth2 = "424,44";
    //private String simpleSapmaxamountPreauth4 = "1424,44";
    private String simpleSapmaxamountPreauthComplete ="424,44";
    private String simpleSapmaxamountPreauthPart3 = "424,44";

    private String simpleSapmaxpendingMercahnt = "#59514 - www.trx-sapmax.ru";
    private String simpleSapmaxoptionPendingMerchant = "option[value=\"59514\"]";
    private String simpleSapmaxMIDpending = "59514";

    private String simpleSapmaxpreAuthMercahnt = "#59528 - www.trx-sapmax2.ru";
    private String simpleSapmaxoptionPreAuthMerchant = "option[value=\"59528\"]";
    private String simpleSapmaxMIDpreAuth = "59528";

    // 3DS

    private String amount3DSPending = "666,22";
    //private String amount3DSPending2 = "1666,22";

    private String amount3DSPreauth1 = "700";
    private String amount3DSPreauth2 = "624,44";
    //private String amount3DSPreauth4 = "1624,44";
    private String amount3DSPreauthComplete ="624,44";
    private String amount3DSPreauthPart3 = "624,44";

    private String pendingMercahnt3DS = "#59530 - www.test2.com";
    private String optionPendingMerchant3DS = "option[value=\"59530\"]";
    private String MIDpending3DS = "59530";

    private String preAuthMercahnt3DS = "#59531 - www.transactions2.com";
    private String optionPreAuthMerchant3DS = "option[value=\"59531\"]";
    private String MIDpreAuth3DS = "59531";

    // 3DS Sapmax

    private String amount3DSSapmaxPending = "666,22";
    //private String amount3DSSapmaxPending2 = "1666,22";

    private String amount3DSSapmaxPreauth1 = "700";
    private String amount3DSSapmaxPreauth2 = "624,44";
    //private String amount3DSSapmaxPreauth4 = "1624,44";
    private String amount3DSSapmaxPreauthComplete ="624,44";
    private String amount3DSSapmaxPreauthPart3 = "624,44";

    private String pendingMercahnt3DSSapmax = "#59523 - www.3DS-trx-sapmax.ru";
    private String optionPendingMerchant3DSSapmax = "option[value=\"59523\"]";
    private String MIDpending3DSSapmax = "59523";

    private String preAuthMercahnt3DSSapmax = "#59529 - www.3DS-trx-sapmax2.ru";
    private String optionPreAuthMerchant3DSSapmax = "option[value=\"59529\"]";
    private String MIDpreAuth3DSSapmax = "59529";

    @BeforeTest
    public void createParameters(){

        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
    }

    // full chargeback
    // simple
    @Test
    public void simplePendingCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplependingMercahnt, simpleoptionPendingMerchant,
                id + orderID, simpleamountPending, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPending, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPending, simpleamountPending, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPending, simpleamountPending, testGateway,
                email);
    }

    @Test
    public void simplePreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauth1, preAuthStatus2, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauth1, simpleamountPreauth1, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPreauth1, simpleamountPreauth1, testGateway,
                email);
    }

    @Test
    public void simplePartialCompletedPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamountPreauth1, simpleamountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthComplete, simpleamountPreauthComplete, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPreauthComplete, simpleamountPreauthComplete, testGateway,
                email);
    }

    @Test
    public void simpleFullCompletedPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamountPreauth1, simpleamountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthComplete, simpleamountPreauthComplete, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction, cause,
                settledStatus, cardHolderName, simpleamountPreauthComplete, simpleamountPreauthComplete, testGateway,
                email);
    }


    // simple sapmax
    @Test
    public void simpleSapmaxPendingCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and complete simple sapmax payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpendingMercahnt, simpleSapmaxoptionPendingMerchant, id + orderID, simpleSapmaxamountPending,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPending, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPending, simpleSapmaxamountPending, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPending, simpleSapmaxamountPending, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauth1, preAuthStatus2, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauth1, simpleSapmaxamountPreauth1, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPreauth1, simpleSapmaxamountPreauth1, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxPartialCompletedPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleSapmaxamountPreauth1, simpleSapmaxamountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID,
                lastActionCharge, settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthComplete,
                simpleSapmaxamountPreauthComplete, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID,
                typeTransaction, cause, settledStatus, cardHolderName, simpleSapmaxamountPreauthComplete, simpleSapmaxamountPreauthComplete, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxFullCompletedPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxamountPreauth2, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleSapmaxamountPreauth2, simpleSapmaxamountPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthComplete, simpleSapmaxamountPreauthComplete, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPreauthComplete, simpleSapmaxamountPreauthComplete, testGateway,
                email);
    }

    // 3DS
    @Test
    public void threeDSPendingCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, pendingMercahnt3DS, optionPendingMerchant3DS, id + orderID,
                amount3DSPending, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB,cardHolderName);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPending, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpending3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPending, amount3DSPending, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpending3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPending, amount3DSPending, testGateway,
                email);
    }

    @Test
    public void threeDSPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                amount3DSPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauth1, preAuthStatus2, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauth1, amount3DSPreauth1, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauth1, amount3DSPreauth1, testGateway,
                email);
    }

    @Test
    public void threeDSPartialCompletedPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                amount3DSPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, amount3DSPreauth1, amount3DSPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthComplete, amount3DSPreauthComplete, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauthComplete, amount3DSPreauthComplete, testGateway,
                email);
    }

    @Test
    public void threeDSFullCompletedPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                amount3DSPreauth2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, amount3DSPreauth2, amount3DSPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthComplete, amount3DSPreauthComplete, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauthComplete, amount3DSPreauthComplete, testGateway,
                email);
    }


    // 3DS sapmax
    @Test
    public void threeDSSapmaxPendingCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                pendingMercahnt3DSSapmax, optionPendingMerchant3DSSapmax, id + orderID, amount3DSSapmaxPending, numberCardA,
                numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,
                cardHolderName, sapmaxMerchantId);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPending, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpending3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPending, amount3DSSapmaxPending, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpending3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPending, amount3DSSapmaxPending, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauth1, preAuthStatus2, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauth1, amount3DSSapmaxPreauth1, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauth1, amount3DSSapmaxPreauth1, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxPartialCompletedPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, amount3DSSapmaxPreauth1, amount3DSSapmaxPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSSapmaxPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge, settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthComplete, amount3DSSapmaxPreauthComplete, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauthComplete, amount3DSSapmaxPreauthComplete, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxFullCompletedPreAuthCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, amount3DSSapmaxPreauth2, amount3DSSapmaxPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSSapmaxPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge, settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthComplete, amount3DSSapmaxPreauthComplete, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauthComplete, amount3DSSapmaxPreauthComplete, testGateway,
                email);
    }


    // settled trx cbk
    // simple
    @Test
    public void simplePendingSettledCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //new transaction,payment and get id
        idTransaction =  TestUtils.getNewIdTransaction(driver, simplependingMercahnt, simpleoptionPendingMerchant,
                id + orderID, simpleamountPending, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPending, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPending, simpleamountPending, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPending, simpleamountPending, testGateway,
                email);
    }

    @Test
    public void simplePreauthPartialCompleteCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        // generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway,
                email);
    }

    @Test
    public void simplePreauthFullCompleteCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        // generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth2, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway,
                email);
    }


    // simple sapmax
    @Test
    public void simpleSapmaxPendingSettledCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpendingMercahnt, simpleSapmaxoptionPendingMerchant, id + orderID, simpleSapmaxamountPending,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPending, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPending, simpleSapmaxamountPending, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPending, simpleSapmaxamountPending, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxPreauthPartialCompleteCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant, id + orderID, simpleSapmaxamountPreauth1,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxPreauthFullCompleteCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant, id + orderID, simpleSapmaxamountPreauth2,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway,
                email);
    }


    // 3DS
    @Test
    public void threeDSPendingSettledCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, pendingMercahnt3DS, optionPendingMerchant3DS,
                id + orderID, amount3DSPending, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPending, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpending3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPending, amount3DSPending, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpending3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPending, amount3DSPending, testGateway,
                email);
    }

    @Test
    public void threeDSPreauthPartialCompleteCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID, amount3DSPreauth1,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway,
                email);
    }

    @Test
    public void threeDSPreauthFullCompleteCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID, amount3DSPreauth2,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway,
                email);
    }

    // 3DS sapmax
    @Test
    public void threeDSSapmaxPendingSettledCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha, pendingMercahnt3DSSapmax,
                optionPendingMerchant3DSSapmax, id + orderID, amount3DSSapmaxPending, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPending, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpending3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPending, amount3DSSapmaxPending, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpending3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPending, amount3DSSapmaxPending, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxPreauthPartialCompleteCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear,
                cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSSapmaxPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxPreauthFullCompleteCBK(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear,
                cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSSapmaxPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway,
                email);
    }

    // partial chargeback
    // simple
    @Test
    public void simplePendingCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplependingMercahnt, simpleoptionPendingMerchant,
                id + orderID, simpleamountPending, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPending, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPending, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPending, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simplePreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauth1, preAuthStatus2, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauth1, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPreauth1, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simplePartialCompletedPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamountPreauth1, simpleamountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthComplete, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPreauthComplete, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simpleFullCompletedPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamountPreauth1, simpleamountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthComplete, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction, cause,
                settledStatus, cardHolderName, simpleamountPreauthComplete, partialCBKAmount2, testGateway,
                email);
    }


    // simple sapmax
    @Test
    public void simpleSapmaxPendingCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and complete simple sapmax payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpendingMercahnt, simpleSapmaxoptionPendingMerchant, id + orderID, simpleSapmaxamountPending,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPending, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPending, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPending, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauth1, preAuthStatus2, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauth1, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPreauth1, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxPartialCompletedPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleSapmaxamountPreauth1, simpleSapmaxamountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID,
                lastActionCharge, settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthComplete,
                partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID,
                typeTransaction, cause, settledStatus, cardHolderName, simpleSapmaxamountPreauthComplete, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxFullCompletedPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxamountPreauth2, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleSapmaxamountPreauth2, simpleSapmaxamountPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthComplete, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPreauthComplete, partialCBKAmount2, testGateway,
                email);
    }


    // 3DS
    @Test
    public void threeDSPendingCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, pendingMercahnt3DS, optionPendingMerchant3DS, id + orderID,
                amount3DSPending, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB,cardHolderName);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPending, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpending3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPending, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpending3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPending, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                amount3DSPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauth1, preAuthStatus2, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauth1, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauth1, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSPartialCompletedPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                amount3DSPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, amount3DSPreauth1, amount3DSPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthComplete, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauthComplete, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSFullCompletedPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                amount3DSPreauth2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, amount3DSPreauth2, amount3DSPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthComplete, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauthComplete, partialCBKAmount2, testGateway,
                email);
    }


    // 3DS sapmax
    @Test
    public void threeDSSapmaxPendingCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                pendingMercahnt3DSSapmax, optionPendingMerchant3DSSapmax, id + orderID, amount3DSSapmaxPending, numberCardA,
                numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,
                cardHolderName, sapmaxMerchantId);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPending, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpending3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPending, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpending3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPending, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauth1, preAuthStatus2, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauth1, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauth1, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxPartialCompletedPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, amount3DSSapmaxPreauth1, amount3DSSapmaxPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSSapmaxPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge, settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthComplete, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauthComplete, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxFullCompletedPreAuthCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, amount3DSSapmaxPreauth2, amount3DSSapmaxPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSSapmaxPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauthComplete, pendingStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge, settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthComplete, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauthComplete, partialCBKAmount2, testGateway,
                email);
    }


    // settled trx cbk
    // simple
    @Test
    public void simplePendingSettledCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //new transaction,payment and get id
        idTransaction =  TestUtils.getNewIdTransaction(driver, simplependingMercahnt, simpleoptionPendingMerchant,
                id + orderID, simpleamountPending, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPending, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPending, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPending, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simplePreauthPartialCompleteCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        // generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth1, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthPart3, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPreauthPart3, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simplePreauthFullCompleteCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        // generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamountPreauth2, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleamountPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthPart3, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleamountPreauthPart3, partialCBKAmount2, testGateway,
                email);
    }


    // simple sapmax
    @Test
    public void simpleSapmaxPendingSettledCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpendingMercahnt, simpleSapmaxoptionPendingMerchant, id + orderID, simpleSapmaxamountPending,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPending, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPending, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPending, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxPreauthPartialCompleteCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant, id + orderID, simpleSapmaxamountPreauth1,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthPart3, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPreauthPart3, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void simpleSapmaxPreauthFullCompleteCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant, id + orderID, simpleSapmaxamountPreauth2,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxamountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, simpleSapmaxamountPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthPart3, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, simpleSapmaxMIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, simpleSapmaxamountPreauthPart3, partialCBKAmount2, testGateway,
                email);
    }


    // 3DS
    @Test
    public void threeDSPendingSettledCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, pendingMercahnt3DS, optionPendingMerchant3DS,
                id + orderID, amount3DSPending, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPending, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpending3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPending, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpending3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPending, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSPreauthPartialCompleteCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID, amount3DSPreauth1,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthPart3, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauthPart3, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSPreauthFullCompleteCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID, amount3DSPreauth2,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);
        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DS, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthPart3, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DS, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSPreauthPart3, partialCBKAmount2, testGateway,
                email);
    }


    // 3DS sapmax
    @Test
    public void threeDSSapmaxPendingSettledCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha, pendingMercahnt3DSSapmax,
                optionPendingMerchant3DSSapmax, id + orderID, amount3DSSapmaxPending, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPending, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpending3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPending, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpending3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPending, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxPreauthPartialCompleteCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear,
                cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSSapmaxPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthPart3, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauthPart3, partialCBKAmount2, testGateway,
                email);
    }

    @Test
    public void threeDSSapmaxPreauthFullCompleteCBKPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        // authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, amount3DSSapmaxPreauth2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear,
                cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSSapmaxPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        TestUtils.checkChargeBackForm(driver, idTransaction, amount3DSSapmaxPreauthPart3, settledStatus, testGateway, cardHolderName);

        //type all value in form and click nextButton
        driver.findElement(By.name("ctl00$content$editor$amount")).clear();
        driver.findElement(By.name("ctl00$content$editor$amount")).sendKeys(partialCBKAmount2);

        driver.findElement(By.name("ctl00$content$editor$reason")).clear();
        driver.findElement(By.name("ctl00$content$editor$reason")).sendKeys(cause);
        driver.findElement(By.name("ctl00$content$editor$code")).clear();
        driver.findElement(By.name("ctl00$content$editor$code")).sendKeys(cbcode);
        driver.findElement(By.name("ctl00$content$editor$indicator")).clear();
        driver.findElement(By.name("ctl00$content$editor$indicator")).sendKeys(cbindicator);
        driver.findElement(By.name("ctl00$content$editor$cmdSave")).click();

        //check in lk admin
        driver.findElement(By.id("ctl00_content_leftMenu_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        idChargeBack = TestUtils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(TestUtils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthPart3, partialCBKAmount2, testGateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(TestUtils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        TestUtils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth3DSSapmax, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount3DSSapmaxPreauthPart3, partialCBKAmount2, testGateway,
                email);
    }
}
