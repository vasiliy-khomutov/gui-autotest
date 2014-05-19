package critical.transaction;


import critical.TestUtils;
import model.DriverFactory;
import model.Connect;
import model.Environment;
import model.Utils;
import org.apache.http.client.methods.HttpPost;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class Refund {

    private String idTransaction;
    private String baseUrl;
    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin;
    private String passwordAdmin;
    private String captcha = "ability";

    // simple
    private String simpleAmountPending = "448";
    private String simpleAmountPendingRefund1 = "48";
    private String simpleAmountPendingRefund2 = "400,40";
    private String simpleAmountPendingRefund3 = "448,40";

    private String simplependingMercahnt = "#57482 - www.test1.ru";
    private String simpleoptionPendingMerchant = "option[value=\"57482\"]";
    private String simpleMIDpending = "57482";
    private String simplePendingPrivateSecurityKey = "a0dd4eea-8652-4a4d-a9e8-367920163d1a";

    private String simpleAmountPreauth1 = "300";
    private String simpleAmountPreauth2 = "224";
    private String simpleAmountPreauthComplete ="224,44";
    private String simpleAmountPreauthRefund1 = "24";
    private String simpleAmountPreauthRefund2 = "200,44";
    private String simpleAmountPreauthRefund3 = "224,44";

    private String simplepreAuthMercahnt = "#57483 - www.transactions.com";
    private String simpleoptionPreAuthMerchant = "option[value=\"57483\"]";
    private String simpleMIDpreAuth = "57483";
    private String simplePreauthPrivateSecurityKey = "bc151bea-8c58-40e0-bb44-0a6ae510a5a2";


    // 3DS
    private String threeDSAmountPending = "648";
    private String threeDSAmountPendingRefund1 = "68";
    private String threeDSAmountPendingRefund2 = "600,40";
    private String threeDSAmountPendingRefund3 = "648,40";

    private String threeDSpartialCompleteAmount = "66";

    private String pendingMercahnt3DS = "#59530 - www.test2.com";
    private String optionPendingMerchant3DS = "option[value=\"59530\"]";
    private String MIDpending3DS = "59530";
    private String threeDSPendingPrivateSecurityKey = "2dc7c04f-47d6-4616-80b0-be06e2786cb8";

    private String preAuthMercahnt3DS = "#59531 - www.transactions2.com";
    private String optionPreAuthMerchant3DS = "option[value=\"59531\"]";
    private String MIDpreAuth3DS = "59531";
    private String threeDSPreAuthPrivateSecurityKey = "bccda6cb-e5c8-4bd8-8816-8c015ed457f1";


    private String completedRefundsAmount0 = "0.00";

    // additional parameters
    private String pendingStatus = "Pending";
    private String preAuthStatus = "PreAuth";
    private String voidedStatus = "Voided";
    private String settledStatus = "Settled";

    private String lastActionVoid = "Void";
    private String lastActionComplete = "Complete";
    private String lastActionRefund = "Refund";

    private String sapmaxMerchantId = "3199";
    private String idRefund;
    private String idRefund2;

    private String typePurchase = "Purchase";
    private String typeRefund = "Refund";

    private String testGateway = "Test gateway";
    private String cardTypeMaster = "MasterCard";
    private String cardTypeVisa = "Visa";

    private String currencyRUB = "RUB";
    private String orderID = "";
    private String email = "autoTEST@test.test";
    private String numberCardA = "4111";
    private String numberCardB = "1111";
    private String numberCardC = "1111";
    private String numberCardD = "1111";
    private String expDateMonth = "09";
    private String expDateYear = "2014";
    private String expDate = "09 / 2014";
    private String cardHolderName = "MR.AUTOTEST";
    private String cvc = "111";
    private String bank = "QA-BANK";

    // api trx parameters
    private String URL;
    private String SecurityKey;
    private String PRIVATE_SECURITY_KEY_PENDING;
    private String PRIVATE_SECURITY_KEY_PREAUTH;
    private String PRIVATE_SECURITY_KEY_3DS_PENDING;
    private String PRIVATE_SECURITY_KEY_3DS_PREAUTH;
    private String MERCHANT_ID_PENDING;
    private String MERCHANT_ID_PREAUTH;
    private String MERCHANT_ID_3DS_PENDING;
    private String MERCHANT_ID_3DS_PREAUTH;
    private String ORDER_ID;
    private String AMOUNT;
    private String CURRENCY_RUB;
    private String CARD_NUMBER;
    private String CARD_HOLDER_NAME;
    private String CARD_EXP_DATE;
    private String CARD_CVV;
    private String COUNTRY;
    private String CITY;
    private String ADDRESS;
    private String IP;
    private String EMAIL;
    private String ISSUER;
    private String CONTENT_TYPE;

    HttpPost requestPost;

    @BeforeClass
    @Parameters({"url", "MerchantId","OrderId", "CurrencyRub", "PrivateSecurityKey", "CardHolderName", "CardExpDate", "CardCvv", "Country", "City",
            "Ip", "contentType", "Address", "Email", "Issuer"})
    public void createCorrectParameters(String url, String MerchantId, String OrderId, String CurrencyRub, String PrivateSecurityKey, String CardHolderName,
                                        String CardExpDate, String CardCvv, String Country, String City, String Ip, String contentType, String adress, String email,
                                        String issuer){

        URL = url + "/transaction/auth/";
        MERCHANT_ID_PENDING = MerchantId + simpleMIDpending;
        MERCHANT_ID_PREAUTH = MerchantId + simpleMIDpreAuth;
        MERCHANT_ID_3DS_PENDING = MerchantId + MIDpending3DS;
        MERCHANT_ID_3DS_PREAUTH = MerchantId + MIDpreAuth3DS;
        ORDER_ID = OrderId;
        CURRENCY_RUB = CurrencyRub;
        PRIVATE_SECURITY_KEY_PENDING = PrivateSecurityKey + simplePendingPrivateSecurityKey;
        PRIVATE_SECURITY_KEY_PREAUTH = PrivateSecurityKey + simplePreauthPrivateSecurityKey;
        PRIVATE_SECURITY_KEY_3DS_PENDING = PrivateSecurityKey + threeDSPendingPrivateSecurityKey;
        PRIVATE_SECURITY_KEY_3DS_PREAUTH = PrivateSecurityKey + threeDSPreAuthPrivateSecurityKey;
        CARD_NUMBER ="CardNumber=" + numberCardA + numberCardB + numberCardC + numberCardD;
        CARD_HOLDER_NAME = CardHolderName;
        CARD_EXP_DATE = CardExpDate;
        CARD_CVV = CardCvv;
        COUNTRY = Country;
        CITY = City;
        IP = Ip;
        CONTENT_TYPE = contentType;
        ADDRESS =  adress;
        EMAIL = email;
        ISSUER = issuer;

        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
    }

    // MERCHANT
    // simple partial
    @Test
    public void PendingRefundSimplePartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT = "Amount=" + simpleAmountPending + ".40";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpending, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPendingRefund1, simpleAmountPendingRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPendingRefund1, simpleAmountPendingRefund1, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimplePartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth1 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimplePartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth2 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, cardHolderName, email);
    }

    // simple partial + partial
    @Test
    public void PendingRefundSimplePartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT = "Amount=" + simpleAmountPending + ".40";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        // go to transactions list (all)
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // open transaction card and open refund form
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check opened refund form
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpending, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPending);

        //type amount and make FIRST refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get first refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPendingRefund1, simpleAmountPendingRefund1, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleAmountPendingRefund1,
                simpleAmountPendingRefund1, testGateway, cardHolderName, email);

        // make SECOND refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        // go to transactions list (all)
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // open transaction card and open refund form
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //type amount
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPendingRefund2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleAmountPendingRefund2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPendingRefund2, simpleAmountPendingRefund2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleAmountPendingRefund2,
                simpleAmountPendingRefund2, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimplePartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth1 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, cardHolderName, email);

        // make SECOND refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        // go to transactions list (all)
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // open transaction card and open refund form
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //type amount
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPreauthRefund2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleAmountPreauthRefund2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund2, simpleAmountPreauthRefund2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleAmountPreauthRefund2,
                simpleAmountPreauthRefund2, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimplePartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth2 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, cardHolderName, email);

        // make SECOND refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        // go to transactions list (all)
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // open transaction card and open refund form
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //type amount
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPreauthRefund2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleAmountPreauthRefund2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund2, simpleAmountPreauthRefund2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleAmountPreauthRefund2,
                simpleAmountPreauthRefund2, testGateway, cardHolderName, email);
    }

    // simple full
    @Test
    public void PendingRefundSimpleFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT = "Amount=" + simpleAmountPending + ".40";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpending, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPendingRefund3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPendingRefund3, simpleAmountPendingRefund3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPendingRefund3, simpleAmountPendingRefund3, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimpleFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth1 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPreauthRefund3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund3, simpleAmountPreauthRefund3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund3, simpleAmountPreauthRefund3, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimpleFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth2 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleAmountPreauthRefund3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund3, simpleAmountPreauthRefund3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund3, simpleAmountPreauthRefund3, testGateway, cardHolderName, email);
    }


    // ADMIN
    // simple partial
    @Test
    public void PendingRefundSimplePartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT = "Amount=" + simpleAmountPending + ".40";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPendingRefund1, simpleAmountPendingRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPendingRefund1, simpleAmountPendingRefund1, testGateway, email);
    }

    @Test
    public void PartialPreauthRefundSimplePartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth1 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, email);
    }

    @Test
    public void FullPreauthRefundSimplePartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth2 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, email);
    }

    // simple partial + partial
    @Test
    public void PendingRefundSimplePartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT = "Amount=" + simpleAmountPending + ".40";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPendingRefund1, simpleAmountPendingRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPendingRefund1, simpleAmountPendingRefund1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, simpleAmountPendingRefund1, simpleAmountPendingRefund2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPendingRefund2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPendingRefund2, simpleAmountPendingRefund2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPendingRefund2, simpleAmountPendingRefund2, testGateway, email);
    }

    @Test
    public void PartialPreauthRefundSimplePartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth1 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, simpleAmountPreauthRefund1, simpleAmountPreauthRefund2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPreauthRefund2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund2, simpleAmountPreauthRefund2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund2, simpleAmountPreauthRefund2, testGateway, email);
    }

    @Test
    public void FullPreauthRefundSimplePartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth2 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund1, simpleAmountPreauthRefund1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, simpleAmountPreauthRefund1, simpleAmountPreauthRefund2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPreauthRefund2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund2, simpleAmountPreauthRefund2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund2, simpleAmountPreauthRefund2, testGateway, email);
    }

    // simple full
    @Test
    public void PendingRefundSimpleFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT = "Amount=" + simpleAmountPending + ".40";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPendingRefund3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPendingRefund3, simpleAmountPendingRefund3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPendingRefund3, simpleAmountPendingRefund3, testGateway, email);
    }

    @Test
    public void PartialPreauthRefundSimpleFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth1 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPreauthRefund3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund3, simpleAmountPreauthRefund3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund3, simpleAmountPreauthRefund3, testGateway, email);
    }

    @Test
    public void FullPreauthRefundSimpleFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + simpleAmountPreauth2 + ".44";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleAmountPreauthRefund3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleAmountPreauthRefund3, simpleAmountPreauthRefund3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleAmountPreauthRefund3, simpleAmountPreauthRefund3, testGateway, email);
    }


    /*
    // MERCHANT
    // 3DS partial
    @Test
    public void PendingRefund3DSPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT = "Amount=" + threeDSAmountPending + ".40";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpending3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPendingPart1, amount3DSPendingPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPendingPart1, amount3DSPendingPart1, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefund3DSPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefund3DSPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);

        // если ip прописан в secure (если не прописан, то не отсутствует поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount3DSPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, cardHolderName, email);
    }
    /*
    // 3DS partial + partial
    @Test
    public void PendingRefund3DSPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpending3DS;
        AMOUNT +=  amount3DSPending + ".22";
        PRIVATE_SECURITY_KEY += threeDSPendingPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        // go to transactions list (all)
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // open transaction card and open refund form
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check opened refund form
        TestUtils.checkRefundFormMerchant(driver, MIDpending3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPending);

        //type amount and make FIRST refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get first refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPendingPart1, amount3DSPendingPart1, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPendingPart1,
                amount3DSPendingPart1, testGateway, cardHolderName, email);

        // make SECOND refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        // go to transactions list (all)
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // open transaction card and open refund form
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //type amount
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPendingPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSPendingPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPendingPart2, amount3DSPendingPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPendingPart2,
                amount3DSPendingPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefund3DSPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, cardHolderName, email);

        // make SECOND refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        // go to transactions list (all)
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // open transaction card and open refund form
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //type amount
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart2, amount3DSPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthPart2,
                amount3DSPreauthPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefund3DSPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);

        // если ip прописан в secure (если не прописан, то не отсутствует поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, cardHolderName, email);

        // make SECOND refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        // go to transactions list (all)
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // open transaction card and open refund form
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //type amount
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart2, amount3DSPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSPreauthPart2,
                amount3DSPreauthPart2, testGateway, cardHolderName, email);
    }

    // 3DS full
    @Test
    public void PendingRefund3DSFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpending3DS;
        AMOUNT +=  amount3DSPending + ".22";
        PRIVATE_SECURITY_KEY += threeDSPendingPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpending3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPendingPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPendingPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPendingPart3, amount3DSPendingPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPendingPart3, amount3DSPendingPart3, testGateway, cardHolderName, email);

    }

    @Test
    public void PartialPreauthRefund3DSFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPreauthPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefund3DSFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);

        // если ip прописан в secure (если не прописан, то не отсутствует поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        // change status transaction
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

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSPreauthPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway, cardHolderName, email);
    }

    // ADMIN
    // 3DS partial
    @Test
    public void PendingRefund3DSPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpending3DS;
        AMOUNT +=  amount3DSPending + ".22";
        PRIVATE_SECURITY_KEY += threeDSPendingPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPendingPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPendingPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPendingPart1, amount3DSPendingPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPendingPart1, amount3DSPendingPart1, testGateway, email);
    }

    @Test
    public void PartialPreauthRefund3DSPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, email);
    }

    @Test
    public void FullPreauthRefund3DSPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);

        // если ip прописан в secure (если не прописан, то не отсутствует поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, email);
    }

    // 3DS partial + partial
    @Test
    public void PendingRefund3DSPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpending3DS;
        AMOUNT +=  amount3DSPending + ".22";
        PRIVATE_SECURITY_KEY += threeDSPendingPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPendingPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPendingPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPendingPart1, amount3DSPendingPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPendingPart1, amount3DSPendingPart1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, amount3DSPendingPart1, amount3DSPendingPart2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPendingPart2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPendingPart2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPendingPart2, amount3DSPendingPart2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPendingPart2, amount3DSPendingPart2, testGateway, email);
    }

    @Test
    public void PartialPreauthRefund3DSPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, amount3DSPreauthPart1, amount3DSPreauthPart2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPreauthPart2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart2, amount3DSPreauthPart2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart2, amount3DSPreauthPart2, testGateway, email);
    }

    @Test
    public void FullPreauthRefund3DSPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);

        // если ip прописан в secure (если не прописан, то не отсутствует поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart1, amount3DSPreauthPart1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, amount3DSPreauthPart1, amount3DSPreauthPart2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPreauthPart2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart2, amount3DSPreauthPart2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart2, amount3DSPreauthPart2, testGateway, email);
    }

    // 3DS full
    @Test
    public void PendingRefund3DSFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpending3DS;
        AMOUNT +=  amount3DSPending + ".22";
        PRIVATE_SECURITY_KEY += threeDSPendingPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPendingPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPendingPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPendingPart3, amount3DSPendingPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPendingPart3, amount3DSPendingPart3, testGateway, email);
    }

    @Test
    public void PartialPreauthRefund3DSFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);
        // если ip прописан в secure (если не прописан, то не заполняется поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway, email);
    }

    @Test
    public void FullPreauthRefund3DSFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += MIDpreAuth3DS;
        AMOUNT += amount3DSPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += threeDSPreAuthPrivateSecurityKey;

        //create transaction
        requestPost = Environment.createPOSTRequest(URL.replace("3ds/", ""));
        SECURITY_KEY ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SECURITY_KEY, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
        //PD = PD.substring(3, PD.length()-5);

        // если ip прописан в secure (если не прописан, то не отсутствует поле "страна запроса" в карточке транзакции в ЛК мерчанта)
        PD = PD.substring(3, PD.length()-18);

        String ACSUrl = TestUtils.getParameter(response, "Url=.*&PD=");
        ACSUrl = ACSUrl.substring(4, ACSUrl.length()-4);

        //send parameter PAReq
        requestPost = Environment.createPOSTRequest(ACSUrl);

        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("PaReq", PAReq));
            pairs.add(new BasicNameValuePair("MD", IdTransaction + ";" + PD));
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            System.out.print(response.toString()+ " Pares-------------\n");
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        Assert.assertTrue(driver.findElement(By.xpath(".*//*//**//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSPreauthPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSPreauthPart3, amount3DSPreauthPart3, testGateway, email);
    }*/
}
