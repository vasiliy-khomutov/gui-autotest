package critical.transaction;


import critical.TestUtils;
import model.DriverFactory;
import model.Environment;
import model.Utils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Voided {

    private String idTransaction;
    private String baseUrl;
    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin;
    private String passwordAdmin;
    private String captcha = "ability";

    // simple
    private String simpleamount = "222";
    private String simplepartialCompleteAmount = "22";

    private String simpleMIDpending = "57482";
    private String simplePendingPrivateSecurityKey = "a0dd4eea-8652-4a4d-a9e8-367920163d1a";
    private String simpleMIDpreAuth = "57483";
    private String simplePreauthPrivateSecurityKey = "bc151bea-8c58-40e0-bb44-0a6ae510a5a2";

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
    private String threeDSPendingPrivateSecurityKey = "2dc7c04f-47d6-4616-80b0-be06e2786cb8";

    private String preAuthMercahnt3DS = "#59531 - www.transactions2.com";
    private String optionPreAuthMerchant3DS = "option[value=\"59531\"]";
    private String MIDpreAuth3DS = "59531";
    private String threeDSPreAuthPrivateSecurityKey = "bccda6cb-e5c8-4bd8-8816-8c015ed457f1";

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
    @Parameters({"url", "MerchantId","OrderId", "Amount", "CurrencyRub", "PrivateSecurityKey", "CardHolderName", "CardExpDate", "CardCvv", "Country", "City",
            "Ip", "contentType", "Address", "Email", "Issuer"})
    public void createCorrectParameters(String url, String MerchantId, String OrderId, String Amount, String CurrencyRub, String PrivateSecurityKey, String CardHolderName,
                                        String CardExpDate, String CardCvv, String Country, String City, String Ip, String contentType, String adress, String email,
                                        String issuer){

        URL = url + "/transaction/auth/";
        MERCHANT_ID_PENDING = MerchantId + simpleMIDpending;
        MERCHANT_ID_PREAUTH = MerchantId + simpleMIDpreAuth;
        MERCHANT_ID_3DS_PENDING = MerchantId + MIDpending3DS;
        MERCHANT_ID_3DS_PREAUTH = MerchantId + MIDpreAuth3DS;
        ORDER_ID = OrderId;
        AMOUNT = Amount;
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

    // simple merchant
    @Test
    public void SimplePendingVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT += simpleamount + ".00";

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //open voidedForm and check
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, simpleMIDpending, idTransaction, id + orderID, cardHolderName, pendingStatus);

        // void trx
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

        // open all transaction tab and check transaction card
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idTransaction, id + orderID, typePurchase, voidedStatus, cardHolderName,
                simpleamount, simpleamount, testGateway, email);

        // admin authorization and check transaction card
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idTransaction, id + orderID, lastActionVoid, voidedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimplePreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, preAuthStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8), lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimplePartialCompletedPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

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
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simplepartialCompleteAmount, simplepartialCompleteAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimpleFullCompletedPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, EMAIL, ISSUER));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // merchant authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

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
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, simpleMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
    }

    // simple admin
    @Test
    public void SimplePendingVoidAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PENDING);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PENDING, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_PREAUTH);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_PREAUTH, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simplepartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
    }

    // 3ds merchant
    @Test
    public void threeDSPendingVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // api part
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

        // gui part
        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();

        Utils.checkVoidedForm(driver, MIDpending3DS, idTransaction, id + orderID, cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idTransaction, id + orderID, typePurchase, voidedStatus, cardHolderName,
                simpleamount, simpleamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DS, idTransaction, id + orderID, lastActionVoid, voidedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
        driver.manage().deleteAllCookies();
    }

    @Test
    public void threeDSPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // api part
        //create transaction
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey ="SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY_3DS_PREAUTH);
        requestPost = (HttpPost)Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID_3DS_PREAUTH, ORDER_ID + id, AMOUNT,CURRENCY_RUB, SecurityKey,
                        CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV, COUNTRY, CITY, IP, EMAIL, ISSUER));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);

        //get parameters
        String PAReq = TestUtils.getParameter(response, "eq=.*&A");
        PAReq =  PAReq.substring(3, PAReq.length()-2).replace(" ", "").replace(",", "");

        String IdTransaction = TestUtils.getParameter(response, "Id=.*&Oper");
        IdTransaction = IdTransaction.substring(3, IdTransaction.length()-5);

        String PD = TestUtils.getParameter(response, "PD=.*&binC");
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

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8), cardHolderName, preAuthStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8), lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSPartialCompletedPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSpartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSpartialCompleteAmount, threeDSpartialCompleteAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSFullCompletedPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                                  preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        //open voidedForm and check
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdVoid")).click();
        Utils.checkVoidedForm(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

        //open transaction all and check cardTransaction
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8),
                typePurchase, voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);

        //authorization admin and check transactionCard
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin,captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DS, idTransaction, (id + orderID).substring(8),
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);
    }

    // 3ds admin
    @Test
    public void threeDSPendingVoidAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // api part
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
            //pairs.add(new BasicNameValuePair("TermUrl", "https://secure.payonlinesystem.com/3ds/complete.3ds?merchantId=13680&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID,"TransactionId="+IdTransaction,PRIVATE_SECURITY_KEY)));
            requestPost.setEntity(new UrlEncodedFormEntity(pairs));
            requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
            response = Environment.getResponceRequest(requestPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String x = response.get(15).substring(49, response.get(15).length()-3);
        String y = response.get(16).substring(46, response.get(16).length()-3);

        requestPost = Environment.createPOSTRequest("https://secure.payonlinesystem.com/3ds/complete.3ds?" + MERCHANT_ID_3DS_PENDING
                + "&publicKey="+TestUtils.getSecurityKey(MERCHANT_ID_3DS_PENDING,"TransactionId=" + IdTransaction,PRIVATE_SECURITY_KEY_3DS_PENDING));
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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
                simpleamount, simpleamount, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DS, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.manage().deleteAllCookies();
    }

    @Test
    public void threeDSPreAuthVoidAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
    }

    @Test
    public void threeDSPartialCompletedPreAuthVoidAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(threeDSpartialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // full preauth complete
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                preAuthStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(simpleamount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath(".//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamount, simpleamount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DS, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleamount, simpleamount, testGateway, email);
    }

    // there is no way to complete sapmax api trx :(
    @Test
    public void SimpleSapmaxPendingVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        Utils.checkVoidedForm(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, lastActionVoid, voidedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);

        driver.manage().deleteAllCookies();
    }

    @Test
    public void SimpleSapmaxPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
        Utils.checkVoidedForm(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, preAuthStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimpleSapmaxPartialCompletedPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
        Utils.checkVoidedForm(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxpartialCompleteAmount, simpleSapmaxpartialCompleteAmount, testGateway, cardHolderName, email);
    }

    @Test
    public void SimpleSapmaxFullCompletedPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
        Utils.checkVoidedForm(driver, simpleSapmaxMIDpreAuth, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.name("ctl00$content$voidTransaction$cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);
    }

    // 3ss sapmax api = :(
    @Test
    public void threeDSSapmaxPendingVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        Utils.checkVoidedForm(driver, MIDpending3DSSapmax, idTransaction, id + orderID, cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.id("ctl00_content_voidTransaction_cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
                threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);
        driver.manage().deleteAllCookies();
    }

    @Test
    public void threeDSSapmaxPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        Utils.checkVoidedForm(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8), cardHolderName, preAuthStatus);

        //voided
        driver.findElement(By.id("ctl00_content_voidTransaction_cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSSapmaxPartialCompletedPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        Utils.checkVoidedForm(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.id("ctl00_content_voidTransaction_cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSpartialCompleteAmountSapmax, threeDSpartialCompleteAmountSapmax, testGateway, cardHolderName, email);
    }

    @Test
    public void threeDSSapmaxFullCompletedPreAuthVoid(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        Utils.checkVoidedForm(driver, MIDpreAuth3DSSapmax, idTransaction, (id + orderID).substring(8), cardHolderName, pendingStatus);

        //voided
        driver.findElement(By.id("ctl00_content_voidTransaction_cmdVoid")).click();
        Assert.assertTrue(Utils.checkMessage(driver), "Message voided is not displayed!");

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
                lastActionVoid, voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, threeDSamountSapmax, threeDSamountSapmax, testGateway, cardHolderName, email);
    }

    // sapmax api = :/
    @Test
    public void SimpleSapmaxPendingVoidAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, cardHolderName, email);

        //check voided partially completed preauth at merchant backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, typePurchase,
                voidedStatus, cardHolderName, simpleSapmaxAmount, simpleSapmaxAmount, testGateway, email);
    }

    // sapmax api = :\
    @Test
    public void threeDSSapmaxPendingVoidAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
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

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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
                voidedStatus, cardTypeVisa, numberCardA + numberCardB + numberCardC + numberCardD,
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
