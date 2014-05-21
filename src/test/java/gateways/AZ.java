package gateways;

import model.Connect;
import model.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AZ {

    // authorization details
    private String baseUrl;
    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin;
    private String passwordAdmin;
    private String captcha = "ability";

    // merchant details - pending
    private String pendingMercahnt = "#61837 - www.test-az.ru";
    private String optionPendingMerchant = "option[value=\"61837\"]";
    private String MIDpending = "61837";
    private String pendingMercahntUrl = "http://www.test-az.ru";

    // merchant details - preauth
    private String preAuthMercahnt = "#61838 - www.test-az2.ru";
    private String optionPreAuthMerchant = "option[value=\"61838\"]";
    private String MIDpreAuth = "61838";
    private String preAuthMercahntUrl = "http://www.test-az2.ru";

    // trx details
    private String orderID = "";
    private String email = "autotest@test.test";
    private String numberCardA;
    private String numberCardB;
    private String numberCardC;
    private String numberCardD;
    private String expDateMonth = "04";
    private String expDateYear = "2015";
    private String expDate = "04 / 2015";
    private String cvc = "291";
    private String cardHolderName = "MR.AUTOTEST";
    private String bank = "QA-BANK";

    // additional parameters
    private String pendingStatus = "Pending";
    private String preauthStatus = "PreAuthorized";
    private String voidedStatus = "Voided";
    private String settledStatus = "Settled";

    private String cardTypeVisa = "Visa";

    private String typePurchase = "Purchase";
    private String typePreAuth = "PreAuth";
    private String typeVoid = "Void";
    private String typeRefund = "Refund";

    private String gateway = "AzeriCard Test";

    private String amountUSD;
    private String idTransaction;
    private String idChargeBack;
    private String idRefund;

    private String lastActionCharge = "Chargeback";
    private String lastActionComplete = "Complete";
    private String lastActionVoid = "Void";
    private String lastActionRefund = "Refund";

    private String cause = "auto test check";
    private String cbcode = "1";
    private String cbindicator = "1111";
    private String typeTransaction = "Chargeback";

    @BeforeTest
    public void createParameters(){

        // initialize authorization parameters
        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];

        String [] az = Environment.readAZFile();
        numberCardA = az[0];
        numberCardB = az[1];
        numberCardC = az[2];
        numberCardD = az[3];
    }

    // TODO - data provider files
    // GW + Currency Exchange service testing
    // PENDING, PREAUTH, COMPLETE
    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "pendingAZ", enabled = true)
    public void pending(String currency, String amount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, pendingMercahnt, optionPendingMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, pendingMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(pendingMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                amount, amountUSD, gateway, email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauth(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauthAndPartialComplete(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // partial preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(partialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        String completedAmountUSD = Utils.getUSDAmount(partialCompleteAmount, currency);

        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                partialCompleteAmount, completedAmountUSD, gateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, partialCompleteAmount, completedAmountUSD,
                gateway, cardHolderName, email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauthAndFullComplete(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD,
                gateway, cardHolderName, email);
    }


    // VOIDS
    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "voidAZ", enabled = true)
    public void pendingAndVoid(String currency, String amount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, pendingMercahnt, optionPendingMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, pendingMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(pendingMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //open voidedForm and check
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, MIDpending, idTransaction, id + orderID, cardHolderName, pendingStatus);

        // void trx
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

        // open all transaction tab and check transaction card
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending, idTransaction, id + orderID, typePurchase, voidedStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // admin authorization and check transaction card
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending, idTransaction, id + orderID, typeVoid, voidedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauthAndVoid(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //open voidedForm and check
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, preauthStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, amount, amountUSD, gateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8), lastActionVoid,
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount, amountUSD, gateway, cardHolderName, email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauthAndPartialCompleteAndVoid(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // partial preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(partialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        String completedAmountUSD = Utils.getUSDAmount(partialCompleteAmount, currency);
        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                partialCompleteAmount, completedAmountUSD, gateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, partialCompleteAmount, completedAmountUSD,
                gateway, cardHolderName, email);

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, partialCompleteAmount, completedAmountUSD, gateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8), lastActionVoid,
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, partialCompleteAmount, completedAmountUSD, gateway, cardHolderName, email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauthAndFullCompleteAndVoid(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));
        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD,
                gateway, cardHolderName, email);

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, amount, amountUSD, gateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, (id + orderID).substring(8), lastActionVoid,
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount, amountUSD, gateway, cardHolderName, email);
    }


    // CHARGEBACKS
    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "cbkAZ", enabled = true)
    public void pendingAndCBK(String currency, String amount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, pendingMercahnt, optionPendingMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, pendingMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(pendingMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        Utils.checkChargeBackForm(driver, idTransaction, amountUSD, pendingStatus, gateway, cardHolderName);

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
        idChargeBack = Utils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(Utils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        Utils.checkCardTransactionChagreBackAdmin(driver, MIDpending, idChargeBack, id + orderID, lastActionCharge,
                settledStatus, cause, cbcode, cbindicator, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(Utils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        Utils.checkCardTransactionChargeBackMerchant(driver, MIDpending, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount, amountUSD, gateway,
                email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauthAndCBK(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //authorization admin and open registrationChargeBackForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        Utils.checkChargeBackForm(driver, idTransaction, amountUSD, preauthStatus, gateway, cardHolderName);

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
        idChargeBack = Utils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(Utils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        Utils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(Utils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        Utils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount, amountUSD, gateway,
                email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauthAndPartialCompleteAndCBK(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // partial preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(partialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        String completedAmountUSD = Utils.getUSDAmount(partialCompleteAmount, currency);
        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                partialCompleteAmount, completedAmountUSD, gateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, partialCompleteAmount, completedAmountUSD,
                gateway, cardHolderName, email);

        //authorization admin and open registrationChargeBackForm
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        Utils.checkChargeBackForm(driver, idTransaction, completedAmountUSD, pendingStatus, gateway, cardHolderName);

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
        idChargeBack = Utils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(Utils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        Utils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, partialCompleteAmount, completedAmountUSD, gateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(Utils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        Utils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, partialCompleteAmount, completedAmountUSD, gateway,
                email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "preauthAZ", enabled = true)
    public void preauthAndFullCompleteAndCBK(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD,
                gateway, cardHolderName, email);

        //authorization admin and open registrationChargeBackForm
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Зарегистрировать ChargeBack")).click();

        //check registrationChargeBackForm
        Utils.checkChargeBackForm(driver, idTransaction, amountUSD, pendingStatus, gateway, cardHolderName);

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
        idChargeBack = Utils.getIdTransactionCharge(driver, idTransaction).substring(2);
        Assert.assertTrue(Utils.checkChargeBackImgAdmin(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        Utils.checkCardTransactionChagreBackAdmin(driver, MIDpreAuth, idChargeBack, id + orderID, lastActionCharge, settledStatus,
                cause, cbcode, cbindicator, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway);

        //check in lk merch
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        Assert.assertTrue(Utils.checkChargeBackImgMerchant(driver, idChargeBack), "Failure image chargeBack in transaction list!");

        //check card transaction
        Utils.checkCardTransactionChargeBackMerchant(driver, MIDpreAuth, idChargeBack, id + orderID, typeTransaction,
                cause, settledStatus, cardHolderName, amount, amountUSD, gateway,
                email);
    }


    // REFUNDS
    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "refundAZ", enabled = true)
    public void pendingAndRefund(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, pendingMercahnt, optionPendingMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, pendingMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(pendingMercahntUrl));

        //check in administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //change transaction status
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpending, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount, amountUSD, gateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount, amountUSD, gateway, cardHolderName, email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "refundAZ", enabled = true)
    public void preauthAndRefund(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //change transaction status
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount, amountUSD, gateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "refundAZ", enabled = true)
    public void preauthAndPartialCompleteAndRefund(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // get USD amount
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // partial preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(partialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        String completedAmountUSD = Utils.getUSDAmount(partialCompleteAmount, currency);
        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                partialCompleteAmount, completedAmountUSD, gateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, partialCompleteAmount, completedAmountUSD,
                gateway, cardHolderName, email);

        //change transaction status
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, partialCompleteAmount);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(partialCompleteAmount);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, partialCompleteAmount);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, partialCompleteAmount, completedAmountUSD, gateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, partialCompleteAmount, completedAmountUSD, gateway, cardHolderName, email);
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "refundAZ", enabled = true)
    public void preauthAndFullCompleteAndRefund(String currency, String amount, String partialCompleteAmount){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount,
                numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, email, currency, cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        amountUSD = Utils.getUSDAmount(amount, currency);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        // preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        Utils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName,
                amount, amountUSD, gateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD,
                gateway, cardHolderName, email);

        //change transaction status
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount, amountUSD, gateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount, amountUSD, gateway, cardHolderName, email);
    }

    // 3DS - ?
}

