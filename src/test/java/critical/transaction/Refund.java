package critical.transaction;


import critical.TestUtils;
import model.DriverFactory;
import model.Connect;
import model.Environment;
import model.Utils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    private String threeDSAmountPending = "668";
    private String threeDSAmountPendingRefund1 = "68";
    private String threeDSAmountPendingRefund2 = "600,44";
    private String threeDSAmountPendingRefund3 = "668,44";

    private String threeDSAmountPreauth1 = "600";
    private String threeDSAmountPreauth2 = "444";
    private String threeDSAmountPreauthComplete ="444,44";
    private String threeDSAmountPreauthRefund1 = "44";
    private String threeDSAmountPreauthRefund2 = "400,44";
    private String threeDSAmountPreauthRefund3 = "444,44";

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
    @Test (enabled = true)
    public void SimplePending_PartialRefund_Merchant(){

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
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund1);

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

    @Test (enabled = true)
    public void SimplePreauth_PartialComplete_PartialRefund_Merchant(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

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

    @Test (enabled = true)
    public void SimplePreauth_FullComplete_PartialRefund_Merchant(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

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
    @Test (enabled = true)
    public void SimplePending_PartialPartialRefund_Merchant(){

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund1);

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

    @Test (enabled = true)
    public void SimplePreauth_PartialComplete_PartialPartialRefund_Merchant(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

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

    @Test (enabled = true)
    public void SimplePreauth_FullComplete_PartialPartialRefund_Merchant(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

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
    @Test (enabled = true)
    public void SimplePending_FullRefund_Merchant(){

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund3);

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

    @Test (enabled = true)
    public void SimplePreauth_PartialComplete_FullRefund_Merchant(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund3);

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

    @Test (enabled = true)
    public void SimplePreauth_FullComplete_FullRefund_Merchant(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund3);

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
    @Test (enabled = true)
    public void SimplePending_PartialRefund_Admin(){

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund1);

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

    @Test (enabled = true)
    public void SimplePreauth_PartialComplete_PartialRefund_Admin(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

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

    @Test (enabled = true)
    public void SimplePreauth_FullComplete_PartialRefund_Admin(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

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
    @Test (enabled = true)
    public void SimplePending_PartialPartialRefund_Admin(){

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund1);

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

        idRefund2 = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund2);

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

    @Test (enabled = true)
    public void SimplePreauth_PartialComplete_PartialPartialRefund_Admin(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

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

        idRefund2 = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund2);

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

    @Test (enabled = true)
    public void SimplePreauth_FullComplete_PartialPartialRefund_Admin(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund1);

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

        idRefund2 = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund2);

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
    @Test (enabled = true)
    public void SimplePending_FullRefund_Admin(){

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPendingRefund3);

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

    @Test (enabled = true)
    public void SimplePreauth_PartialComplete_FullRefund_Admin(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund3);

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

    @Test (enabled = true)
    public void SimplePreauth_FullComplete_FullRefund_Admin(){

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
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, simpleAmountPreauthRefund3);

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



    // MERCHANT
    // 3DS partial
    @Test (enabled = true)
    public void ThreeDSPending_PartialRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPending + ".44";

        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PENDING);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PENDING
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING,"TransactionId=" + IdTransaction, PRIVATE_SECURITY_KEY_3DS_PENDING));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
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
                settledStatus, threeDSAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, cardHolderName, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_PartialComplete_PartialRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth1 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth1, threeDSAmountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                settledStatus, threeDSAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, cardHolderName, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_FullComplete_PartialRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth2 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth2, threeDSAmountPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                settledStatus, threeDSAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, cardHolderName, email);
    }

    // 3DS partial + partial
    @Test (enabled = true)
    public void ThreeDSPending_PartialPartialRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPending + ".44";

        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PENDING);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PENDING
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING,"TransactionId=" + IdTransaction, PRIVATE_SECURITY_KEY_3DS_PENDING));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
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
                settledStatus, threeDSAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPendingRefund2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, threeDSAmountPendingRefund2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund2, threeDSAmountPendingRefund2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, threeDSAmountPendingRefund2,
                threeDSAmountPendingRefund2, testGateway, cardHolderName, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_PartialComplete_PartialPartialRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth1 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth1, threeDSAmountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                settledStatus, threeDSAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPreauthRefund2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, threeDSAmountPreauthRefund2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund2, threeDSAmountPreauthRefund2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, threeDSAmountPreauthRefund2,
                threeDSAmountPreauthRefund2, testGateway, cardHolderName, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_FullComplete_PartialPartialRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth2 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth2, threeDSAmountPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                settledStatus, threeDSAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPreauthRefund2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, threeDSAmountPreauthRefund2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund2, threeDSAmountPreauthRefund2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, threeDSAmountPreauthRefund2,
                threeDSAmountPreauthRefund2, testGateway, cardHolderName, email);
    }

    // 3DS full
    @Test (enabled = true)
    public void ThreeDSPending_FullRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPending + ".44";

        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PENDING);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PENDING
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING,"TransactionId=" + IdTransaction, PRIVATE_SECURITY_KEY_3DS_PENDING));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
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
                settledStatus, threeDSAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPendingRefund3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund3, threeDSAmountPendingRefund3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund3, threeDSAmountPendingRefund3, testGateway, cardHolderName, email);

    }

    @Test (enabled = true)
    public void ThreeDSPreauth_PartialComplete_FullRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth1 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth1, threeDSAmountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                settledStatus, threeDSAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPreauthRefund3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund3, threeDSAmountPreauthRefund3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund3, threeDSAmountPreauthRefund3, testGateway, cardHolderName, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_FullComplete_FullRefund_Merchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth2 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth2, threeDSAmountPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                settledStatus, threeDSAmountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(threeDSAmountPreauthRefund3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund3, threeDSAmountPreauthRefund3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund3, threeDSAmountPreauthRefund3, testGateway, cardHolderName, email);
    }


    // ADMIN
    // 3DS partial
    @Test (enabled = true)
    public void ThreeDSPending_PartialRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPending + ".44";

        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PENDING);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PENDING
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING,"TransactionId=" + IdTransaction, PRIVATE_SECURITY_KEY_3DS_PENDING));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_PartialComplete_PartialRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth1 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth1, threeDSAmountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_FullComplete_PartialRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth2 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth2, threeDSAmountPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, email);
    }

    // 3DS partial + partial
    @Test (enabled = true)
    public void ThreeDSPending_PartialPartialRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPending + ".44";

        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PENDING);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PENDING
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING,"TransactionId=" + IdTransaction, PRIVATE_SECURITY_KEY_3DS_PENDING));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPendingRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund1, threeDSAmountPendingRefund1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, threeDSAmountPendingRefund1, threeDSAmountPendingRefund2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPendingRefund2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund2, threeDSAmountPendingRefund2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund2, threeDSAmountPendingRefund2, testGateway, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_PartialComplete_PartialPartialRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth1 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth1, threeDSAmountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPreauthRefund2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund2, threeDSAmountPreauthRefund2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund2, threeDSAmountPreauthRefund2, testGateway, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_FullComplete_PartialPartialRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth2 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth2, threeDSAmountPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPreauthRefund1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, threeDSAmountPreauthRefund1, threeDSAmountPreauthRefund2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPreauthRefund2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund2, threeDSAmountPreauthRefund2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund2, threeDSAmountPreauthRefund2, testGateway, email);
    }

    // 3DS full
    @Test (enabled = true)
    public void ThreeDSPending_FullRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPending + ".44";

        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PENDING);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PENDING, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PENDING
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING,"TransactionId=" + IdTransaction, PRIVATE_SECURITY_KEY_3DS_PENDING));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPendingRefund3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPendingRefund3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPendingRefund3, threeDSAmountPendingRefund3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPendingRefund3, threeDSAmountPendingRefund3, testGateway, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_PartialComplete_FullRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth1 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth1, threeDSAmountPreauth1, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPreauthRefund3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund3, threeDSAmountPreauthRefund3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund3, threeDSAmountPreauthRefund3, testGateway, email);
    }

    @Test (enabled = true)
    public void ThreeDSPreauth_FullComplete_FullRefund_Admin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + threeDSAmountPreauth2 + ".44";

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME,
                CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
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

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PREAUTH
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PREAUTH));
        List<NameValuePair> pair2 = new ArrayList<NameValuePair>();
        pair2.add(new BasicNameValuePair("PARes", x));
        pair2.add(new BasicNameValuePair("MD", y));

        try {
            requestPost.setEntity(new UrlEncodedFormEntity(pair2));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);
            idTransaction = response.toString().substring(25,response.toString().length()- 16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // partial preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, threeDSAmountPreauth2, threeDSAmountPreauth2, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSAmountPreauthComplete);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, threeDSAmountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(threeDSAmountPreauthRefund3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = Utils.getIdTransactionRefund(driver, idTransaction, threeDSAmountPreauthRefund3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSAmountPreauthRefund3, threeDSAmountPreauthRefund3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, threeDSAmountPreauthRefund3, threeDSAmountPreauthRefund3, testGateway, email);
    }
}
