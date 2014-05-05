package critical.transaction;


import critical.TestUtils;
import critical.callbacks.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;


public class Voided {


    private String idTransaction;

    private String baseUrl = "https://secure.payonlinesystem.com/";
    private String loginMerchant = "crono.ru@gmail.com";
    private String passwordMerchant = "tester123";
    private String loginAdmin = "v.khomutov";
    private String passwordAdmin = "tester123";
    private String captcha = "ability";

    // simple
    private String simpleamount = "222";
    private String simplepartialCompleteAmount = "22";

    private String simplependingMercahnt = "#57482 - www.test1.ru";
    private String simpleoptionPendingMerchant = "option[value=\"57482\"]";
    private String simpleMIDpending = "57482";

    private String simplepreAuthMercahnt = "#57483 - www.transactions.com";
    private String simpleoptionPreAuthMerchant = "option[value=\"57483\"]";
    private String simpleMIDpreAuth = "57483";

    // simple sapmax
    private String simpleSapmaxAmount = "444";
    private String simpleSapmaxpartialCompleteAmount = "44";

    private String simpleSapmaxpendingMercahnt = "#59514 - www.trx-sapmax.ru";
    private String simpleSapmaxoptionPendingMerchant = "option[value=\"59514\"]";
    private String simpleSapmaxMIDpending = "59514";

    private String simpleSapmaxpreAuthMercahnt = "#59528 - www.trx-sapmax2.ru";
    private String simpleSapmaxoptionPreAuthMerchant = "option[value=\"59528\"]";
    private String simpleSapmaxMIDpreAuth = "59528";

    // 3DS
    private String threeDSamount = "666";
    private String threeDSpartialCompleteAmount = "66";

    private String pendingMercahnt3DS = "#59530 - www.test2.com";
    private String optionPendingMerchant3DS = "option[value=\"59530\"]";
    private String MIDpending3DS = "59530";

    private String preAuthMercahnt3DS = "#59531 - www.transactions2.com";
    private String optionPreAuthMerchant3DS = "option[value=\"59531\"]";
    private String MIDpreAuth3DS = "59531";

    // 3DS Sapmax
    private String threeDSamountSapmax = "888";
    private String threeDSpartialCompleteAmountSapmax = "88";

    private String pendingMercahnt3DSSapmax = "#59523 - www.3DS-trx-sapmax.ru";
    private String optionPendingMerchant3DSSapmax = "option[value=\"59523\"]";
    private String MIDpending3DSSapmax = "59523";

    private String preAuthMercahnt3DSSapmax = "#59529 - www.3DS-trx-sapmax2.ru";
    private String optionPreAuthMerchant3DSSapmax = "option[value=\"59529\"]";
    private String MIDpreAuth3DSSapmax = "59529";


    private String pendingStatus = "Pending";
    private String preAuthStatus = "PreAuth";
    private String voidedStatus = "Voided";

    private String lastActionVoid = "Void";
    private String lastActionComplete = "Complete";
    private String sapmaxMerchantId = "3199";

    private String typePurchase = "Purchase";
    private String testGateway = "Test gateway";
    private String cardType = "Visa";

    private String currencyRUB = "RUB";
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

    // merchant
    @Test
    public void SimplePendingVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplependingMercahnt, simpleoptionPendingMerchant,
                id + orderID, simpleamount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();

        TestUtils.checkVoidedForm(driver, simpleMIDpending, idTransaction, id + orderID, cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idTransaction, id + orderID, typePurchase, voidedStatus, cardHolderName,
                simpleamount, simpleamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idTransaction, id + orderID, lastActionVoid, voidedStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
        driver.manage().deleteAllCookies();
    }

    @Test
    public void SimplePreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, preAuthStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8), lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimplePartialCompletedPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simplepartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, simplepartialCompleteAmount, simplepartialCompleteAmount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simplepartialCompleteAmount, simplepartialCompleteAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimpleFullCompletedPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimpleSapmaxPendingVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and complete simple sapmax payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpendingMercahnt, simpleSapmaxoptionPendingMerchant, id + orderID, simpleSapmaxAmount,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //void the transaction (found by trx id) and check trx card
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();

        TestUtils.checkVoidedForm(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, typePurchase, voidedStatus, cardHolderName,
                simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, lastActionVoid, voidedStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);

        driver.manage().deleteAllCookies();
    }

    @Test
    public void SimpleSapmaxPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxAmount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, preAuthStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimpleSapmaxPartialCompletedPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxAmount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxpartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, simpleSapmaxpartialCompleteAmount, simpleSapmaxpartialCompleteAmount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxpartialCompleteAmount, simpleSapmaxpartialCompleteAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimpleSapmaxFullCompletedPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxAmount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSPendingVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, pendingMercahnt3DS, optionPendingMerchant3DS, id + orderID,
                threeDSamount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB,cardHolderName);

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();

        TestUtils.checkVoidedForm(driver, MIDpending3DS, idTransaction, id + orderID, cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idTransaction, id + orderID, typePurchase, voidedStatus, cardHolderName,
                threeDSamount, threeDSamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idTransaction, id + orderID, lastActionVoid, voidedStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, threeDSamount, threeDSamount, testGateway, cardHolderName, email);

        driver.manage().deleteAllCookies();
    }

    @Test
    public void threeDSPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                threeDSamount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, MIDpreAuth3DS, idTransaction, id + orderID, cardHolderName, preAuthStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8), lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamount, threeDSamount, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSPartialCompletedPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                threeDSamount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                                    preAuthStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSpartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, threeDSpartialCompleteAmount, threeDSpartialCompleteAmount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSpartialCompleteAmount, threeDSpartialCompleteAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSFullCompletedPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        /*idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                threeDSamount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);*/
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID,
                threeDSamount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB, cardHolderName);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSamount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        TestUtils.checkVoidedForm(driver, MIDpreAuth3DS, idTransaction, id + orderID, cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID,
                typePurchase, voidedStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamount, threeDSamount, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSSapmaxPendingVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                pendingMercahnt3DSSapmax, optionPendingMerchant3DSSapmax, id + orderID, threeDSamountSapmax, numberCardA,
                numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,
                cardHolderName, sapmaxMerchantId);

        //void the transaction (found by trx id) and check trx card
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();

        TestUtils.checkVoidedForm(driver, MIDpending3DSSapmax, idTransaction, id + orderID, cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.id("ctl00_content_voidTransaction_cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName,
                threeDSamountSapmax, threeDSamountSapmax, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
                threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);

        driver.manage().deleteAllCookies();
    }

    @Test
    public void threeDSSapmaxPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, threeDSamountSapmax, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);


        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();

        TestUtils.checkVoidedForm(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8), cardHolderName, preAuthStatus);

        //voided
        driver.findElement(By.id("ctl00_content_voidTransaction_cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSSapmaxPartialCompletedPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, threeDSamountSapmax, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);


        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSpartialCompleteAmountSapmax);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();

        TestUtils.checkVoidedForm(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.id("ctl00_content_voidTransaction_cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, threeDSpartialCompleteAmountSapmax, threeDSpartialCompleteAmountSapmax, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSpartialCompleteAmountSapmax, threeDSpartialCompleteAmountSapmax, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSSapmaxFullCompletedPreAuthVoid(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, threeDSamountSapmax, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);


        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSamountSapmax);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();

        TestUtils.checkVoidedForm(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.id("ctl00_content_voidTransaction_cmdVoid")).click();
        Assert.assertTrue(TestUtils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);
    }


    // admin
    @Test
    public void SimplePendingVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplependingMercahnt, simpleoptionPendingMerchant,
                id + orderID, simpleamount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //authorization admin and voided
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();

        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check message and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
                simpleamount, simpleamount, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.manage().deleteAllCookies();
    }

    @Test
    public void SimplePreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant,
                id + orderID, simpleamount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //authorization admin and voided
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check message and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
    }

    @Test
    public void SimplePartialCompletedPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simplepartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //admin authorization and void completed preauth
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check voided partially completed preauth at admin backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simplepartialCompleteAmount, simplepartialCompleteAmount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simplepartialCompleteAmount, simplepartialCompleteAmount, testGateway, email);
    }

    @Test
    public void SimpleFullCompletedPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, simplepreAuthMercahnt, simpleoptionPreAuthMerchant, id + orderID,
                simpleamount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //admin authorization and void completed preauth
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check voided partially completed preauth at admin backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
    }



    @Test
    public void SimpleSapmaxPendingVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and complete simple sapmax payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpendingMercahnt, simpleSapmaxoptionPendingMerchant, id + orderID, simpleSapmaxAmount,
                numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and voided
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();

        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check message and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
                simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);
        driver.manage().deleteAllCookies();
    }

    @Test
    public void SimpleSapmaxPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxAmount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and voided
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check message and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);
    }

    @Test
    public void SimpleSapmaxPartialCompletedPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxAmount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxpartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //admin authorization and void completed preauth
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check voided partially completed preauth at admin backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxpartialCompleteAmount, simpleSapmaxpartialCompleteAmount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleSapmaxpartialCompleteAmount, simpleSapmaxpartialCompleteAmount, testGateway, email);
    }

    @Test
    public void SimpleSapmaxFullCompletedPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransactionSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                simpleSapmaxpreAuthMercahnt, simpleSapmaxoptionPreAuthMerchant,
                id + orderID, simpleSapmaxAmount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleSapmaxAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //admin authorization and void completed preauth
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check voided partially completed preauth at admin backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);
    }



    @Test
    public void threeDSPendingVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, pendingMercahnt3DS, optionPendingMerchant3DS, id + orderID,
                threeDSamount, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email,
                currencyRUB,cardHolderName);

        //authorization admin and voided
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();

        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check message and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
                threeDSamount, threeDSamount, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);
        driver.manage().deleteAllCookies();
    }

    @Test
    public void threeDSPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID, threeDSamount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //authorization admin and voided
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check message and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamount, threeDSamount, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);
    }

    @Test
    public void threeDSPartialCompletedPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID, threeDSamount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSpartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //admin authorization and void completed preauth
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check voided partially completed preauth at admin backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSpartialCompleteAmount, threeDSpartialCompleteAmount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, threeDSpartialCompleteAmount, threeDSpartialCompleteAmount, testGateway, email);
    }

    @Test
    public void threeDSFullCompletedPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DS(driver, preAuthMercahnt3DS, optionPreAuthMerchant3DS, id + orderID, threeDSamount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSamount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //admin authorization and void completed preauth
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check voided partially completed preauth at admin backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamount, threeDSamount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, threeDSamount, threeDSamount, testGateway, email);
   }

    @Test
    public void threeDSSapmaxPendingVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                pendingMercahnt3DSSapmax, optionPendingMerchant3DSSapmax, id + orderID, threeDSamountSapmax, numberCardA,
                numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currencyRUB,
                cardHolderName, sapmaxMerchantId);

        //authorization admin and voided
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();

        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check message and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
                threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);
        driver.manage().deleteAllCookies();
    }

    @Test
    public void threeDSSapmaxPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, threeDSamountSapmax, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        //authorization admin and voided
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check message and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);
    }

    @Test
    public void threeDSSapmaxPartialCompletedPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, threeDSamountSapmax, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSpartialCompleteAmountSapmax);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //admin authorization and void completed preauth
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check voided partially completed preauth at admin backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSpartialCompleteAmountSapmax, threeDSpartialCompleteAmountSapmax, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, threeDSpartialCompleteAmountSapmax, threeDSpartialCompleteAmountSapmax, testGateway, email);
    }

    @Test
    public void threeDSSapmaxFullCompletedPreAuthVoidAdmin(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        //merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction3DSSapmax(driver, baseUrl, loginAdmin, passwordAdmin, captcha,
                preAuthMercahnt3DSSapmax, optionPreAuthMerchant3DSSapmax,
                id + orderID, threeDSamountSapmax, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cvc, bank, email, currencyRUB, cardHolderName, sapmaxMerchantId);

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSamountSapmax);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //admin authorization and void completed preauth
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Отменить")).click();
        driver.findElement(By.name("ctl00$content$actions$cmdVoid")).click();
        Assert.assertTrue(TestUtils.closeAlertAndGetItsText(driver).matches("^Отменить транзакцию[\\s\\S]$"));

        //check voided partially completed preauth at admin backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, lastActionVoid,
                voidedStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, threeDSamountSapmax, threeDSamountSapmax, testGateway, email);
    }
}
