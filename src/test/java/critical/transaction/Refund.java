package critical.transaction;


import critical.TestUtils;
import critical.callbacks.DriverFactory;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Refund {

    private String idTransaction;

    private String idRefund;
    private String idRefund2;

    private String baseUrl;

    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin;
    private String passwordAdmin;
    private String captcha = "ability";

    private String orderID = "";
    private String email = "autoTEST@test.test";
    private String numberCardA = "5444";
    private String numberCardB = "8707";
    private String numberCardC = "2449";
    private String numberCardD = "3746";
    private String expDateMonth = "09";
    private String expDateYear = "2014";
    private String expDate = "09 / 2014";
    private String cardHolderName = "MR.AUTOTEST";
    private String cvc = "111";
    private String bank = "QA-BANK";

    private String testGateway = "Test gateway";
    private String cardType = "MasterCard";

    private String currencyRUB = "RUB";
    private String sapmaxMerchantId = "3199";

    private String typePurchase = "Purchase";
    private String typeRefund = "Refund";

    private String pendingStatus = "Pending";
    private String preAuthStatus = "PreAuth";
    private String settledStatus = "Settled";

    private String lastActionRefund = "Refund";

    // simple

    private String simpleamountPending = "222";
    private String simpleamountPendingPart1 = "22";
    private String simpleamountPendingPart2 = "200,22";
    private String simpleamountPendingPart3 = "222,22";

    private String simplependingMercahnt = "#57482 - www.test1.ru";
    private String simpleoptionPendingMerchant = "option[value=\"57482\"]";
    private String simpleMIDpending = "57482";
    private String simplePendingPrivateSecurityKey = "a0dd4eea-8652-4a4d-a9e8-367920163d1a";

    private String simpleamountPreauth1 = "300";
    private String simpleamountPreauth2 = "224";
    private String simpleamountPreauthComplete ="224,44";
    private String simpleamountPreauthPart1 = "24";
    private String simpleamountPreauthPart2 = "200,44";
    private String simpleamountPreauthPart3 = "224,44";

    private String simplepreAuthMercahnt = "#57483 - www.transactions.com";
    private String simpleoptionPreAuthMerchant = "option[value=\"57483\"]";
    private String simpleMIDpreAuth = "57483";
    private String simplePreauthPrivateSecurityKey = "bc151bea-8c58-40e0-bb44-0a6ae510a5a2";

    // simple sapmax

    private String simpleSapmaxamountPending = "444,22";
    private String simpleSapmaxamountPendingPart1 = "44";
    private String simpleSapmaxamountPendingPart2 = "400,22";
    private String simpleSapmaxamountPendingPart3 = "444,22";

    private String simpleSapmaxamountPreauth1 = "600";
    private String simpleSapmaxamountPreauth2 = "424,44";
    private String simpleSapmaxamountPreauthComplete ="424,44";
    private String simpleSapmaxamountPreauthPart1 = "24";
    private String simpleSapmaxamountPreauthPart2 = "400,44";
    private String simpleSapmaxamountPreauthPart3 = "424,44";

    private String simpleSapmaxpendingMercahnt = "#59514 - www.trx-sapmax.ru";
    private String simpleSapmaxoptionPendingMerchant = "option[value=\"59514\"]";
    private String simpleSapmaxMIDpending = "59514";

    private String simpleSapmaxpreAuthMercahnt = "#59528 - www.trx-sapmax2.ru";
    private String simpleSapmaxoptionPreAuthMerchant = "option[value=\"59528\"]";
    private String simpleSapmaxMIDpreAuth = "59528";

    // 3DS
    private String amount3DSPending = "666";
    private String amount3DSPendingPart1 = "66";
    private String amount3DSPendingPart2 = "600,22";
    private String amount3DSPendingPart3 = "666,22";

    private String amount3DSPreauth1 = "700";
    private String amount3DSPreauth2 = "624";
    private String amount3DSPreauthComplete ="624,44";
    private String amount3DSPreauthPart1 = "24";
    private String amount3DSPreauthPart2 = "600,44";
    private String amount3DSPreauthPart3 = "624,44";

    private String pendingMercahnt3DS = "#59530 - www.test2.com";
    private String optionPendingMerchant3DS = "option[value=\"59530\"]";
    private String MIDpending3DS = "59530";
    private String threeDSPendingPrivateSecurityKey = "2dc7c04f-47d6-4616-80b0-be06e2786cb8";

    private String preAuthMercahnt3DS = "#59531 - www.transactions2.com";
    private String optionPreAuthMerchant3DS = "option[value=\"59531\"]";
    private String MIDpreAuth3DS = "59531";
    private String threeDSPreAuthPrivateSecurityKey = "bccda6cb-e5c8-4bd8-8816-8c015ed457f1";

    // 3DS Sapmax

    private String amount3DSSapmaxPending = "666,22";
    private String amount3DSSapmaxPendingPart1 = "66";
    private String amount3DSSapmaxPendingPart2 = "600,22";
    private String amount3DSSapmaxPendingPart3 = "666,22";

    private String amount3DSSapmaxPreauth1 = "700";
    private String amount3DSSapmaxPreauth2 = "624,44";
    private String amount3DSSapmaxPreauthComplete ="624,44";
    private String amount3DSSapmaxPreauthPart1 = "24";
    private String amount3DSSapmaxPreauthPart2 = "600,44";
    private String amount3DSSapmaxPreauthPart3 = "624,44";

    private String pendingMercahnt3DSSapmax = "#59523 - www.3DS-trx-sapmax.ru";
    private String optionPendingMerchant3DSSapmax = "option[value=\"59523\"]";
    private String MIDpending3DSSapmax = "59523";

    private String preAuthMercahnt3DSSapmax = "#59529 - www.3DS-trx-sapmax2.ru";
    private String optionPreAuthMerchant3DSSapmax = "option[value=\"59529\"]";
    private String MIDpreAuth3DSSapmax = "59529";

    private String completedRefundsAmount0 = "0.00";

    HttpPost requestPost;

    // api trx parameters
    private String URL;
    private String SecurityKey;
    private String PRIVATE_SECURITY_KEY;
    private String MERCHANT_ID;
    private String ORDER_ID;
    private String AMOUNT;
    private String CURRENCY_RUB;
    private String CARD_HOLDER_NAME;
    private String CARD_NUMBER;
    private String CARD_EXP_DATE;
    private String CARD_CVV;
    private String COUNTRY;
    private String CITY;
    private String ADDRESS;
    private String IP;
    private String EMAIL;
    private String ISSUER;
    private String CONTENT_TYPE;
    private String SECURITY_KEY;

    @BeforeClass
    @Parameters({"url", "MerchantId", "OrderId", "Amount", "CurrencyRub","CardHolderName", "CardNumber", "CardExpDate", "CardCvv",
            "Country", "City", "Address", "Ip", "Email", "Issuer", "contentType", "PrivateSecurityKey"})
    public void createCorrectParameters(String url, String MerchantId, String OrderId, String Amount, String CurrencyRub,
                                        String CardHolderName, String CardNumber, String CardExpDate, String CardCvv, String Country,
                                        String City, String Address, String Ip, String Email,
                                        String Issuer, String contentType, String PrivateSecurityKey){

        URL = url + "/transaction/auth/";
        MERCHANT_ID = MerchantId;
        ORDER_ID = OrderId;
        AMOUNT = Amount;
        CURRENCY_RUB = CurrencyRub;
        CARD_HOLDER_NAME = CardHolderName;
        CARD_NUMBER = CardNumber;
        CARD_EXP_DATE = CardExpDate;
        CARD_CVV = CardCvv;
        COUNTRY = Country;
        CITY = City;
        ADDRESS = Address;
        IP = Ip;
        EMAIL = Email;
        ISSUER = Issuer;
        CONTENT_TYPE = contentType;
        PRIVATE_SECURITY_KEY = PrivateSecurityKey;

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

        // add merchant parameters
        MERCHANT_ID += simpleMIDpending;
        AMOUNT += simpleamountPending + ".22";
        PRIVATE_SECURITY_KEY += simplePendingPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=Pending" ), "Incorrect status type.");

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
                settledStatus, simpleamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPendingPart1, simpleamountPendingPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPendingPart1, simpleamountPendingPart1, testGateway, cardHolderName, email);

    }

    @Test
    public void PartialPreauthRefundSimplePartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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
                settledStatus, simpleamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimplePartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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
                settledStatus, simpleamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, cardHolderName, email);
    }


    // simple partial + partial
    @Test
    public void PendingRefundSimplePartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpending;
        AMOUNT += simpleamountPending + ".22";
        PRIVATE_SECURITY_KEY += simplePendingPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=Pending" ), "Incorrect status type.");

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
                settledStatus, simpleamountPending);

        //type amount and make FIRST refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get first refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPendingPart1, simpleamountPendingPart1, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPendingPart1,
                simpleamountPendingPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPendingPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleamountPendingPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPendingPart2, simpleamountPendingPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPendingPart2,
                simpleamountPendingPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimplePartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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
                settledStatus, simpleamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleamountPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart2, simpleamountPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthPart2,
                simpleamountPreauthPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimplePartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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
                settledStatus, simpleamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleamountPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart2, simpleamountPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleamountPreauthPart2,
                simpleamountPreauthPart2, testGateway, cardHolderName, email);
    }

    // simple full
    @Test
    public void PendingRefundSimpleFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpending;
        AMOUNT += simpleamountPending + ".22";
        PRIVATE_SECURITY_KEY += simplePendingPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=Pending" ), "Incorrect status type.");

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
                settledStatus, simpleamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPendingPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPendingPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPendingPart3, simpleamountPendingPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPendingPart3, simpleamountPendingPart3, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimpleFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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
                settledStatus, simpleamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPreauthPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimpleFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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
                settledStatus, simpleamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleamountPreauthPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway, cardHolderName, email);
    }


    // ADMIN
    // simple partial
    @Test
    public void PendingRefundSimplePartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpending;
        AMOUNT += simpleamountPending + ".22";
        PRIVATE_SECURITY_KEY += simplePendingPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=Pending" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPendingPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPendingPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPendingPart1, simpleamountPendingPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPendingPart1, simpleamountPendingPart1, testGateway, email);
    }

    @Test
    public void PartialPreauthRefundSimplePartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, email);
    }

    @Test
    public void FullPreauthRefundSimplePartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, email);
    }

    // simple partial + partial
    @Test
    public void PendingRefundSimplePartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpending;
        AMOUNT += simpleamountPending + ".22";
        PRIVATE_SECURITY_KEY += simplePendingPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=Pending" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPendingPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPendingPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPendingPart1, simpleamountPendingPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPendingPart1, simpleamountPendingPart1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, simpleamountPendingPart1, simpleamountPendingPart2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPendingPart2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPendingPart2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPendingPart2, simpleamountPendingPart2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPendingPart2, simpleamountPendingPart2, testGateway, email);
    }

    @Test
    public void PartialPreauthRefundSimplePartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, simpleamountPreauthPart1, simpleamountPreauthPart2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart2, simpleamountPreauthPart2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart2, simpleamountPreauthPart2, testGateway, email);
    }

    @Test
    public void FullPreauthRefundSimplePartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart1, simpleamountPreauthPart1, testGateway, email);

        // second refund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, simpleamountPreauthPart1, simpleamountPreauthPart2);

        //type amount and make refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart2);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart2, simpleamountPreauthPart2, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart2, simpleamountPreauthPart2, testGateway, email);
    }

    // simple full
    @Test
    public void PendingRefundSimpleFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpending;
        AMOUNT += simpleamountPending + ".22";
        PRIVATE_SECURITY_KEY += simplePendingPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=Pending" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPendingPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPendingPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPendingPart3, simpleamountPendingPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPendingPart3, simpleamountPendingPart3, testGateway, email);
    }

    @Test
    public void PartialPreauthRefundSimpleFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth1 + ".00";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway, email);
    }

    @Test
    public void FullPreauthRefundSimpleFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        // add merchant parameters
        MERCHANT_ID += simpleMIDpreAuth;
        AMOUNT += simpleamountPreauth2 + ".44";
        PRIVATE_SECURITY_KEY += simplePreauthPrivateSecurityKey;

        // complete gw trx
        requestPost = Environment.createPOSTRequest(URL);
        SecurityKey = "SecurityKey=" + TestUtils.getSecurityKey(MERCHANT_ID, ORDER_ID + id, AMOUNT, CURRENCY_RUB, PRIVATE_SECURITY_KEY);
        requestPost = (HttpPost) Environment.setEntityRequest(requestPost, TestUtils.createBodyRequest(MERCHANT_ID, ORDER_ID + id, AMOUNT ,
                CURRENCY_RUB, SecurityKey, COUNTRY, CITY, ADDRESS, IP, EMAIL, ISSUER, CARD_HOLDER_NAME, CARD_NUMBER, CARD_EXP_DATE, CARD_CVV));
        requestPost = (HttpPost) Environment.setHeadersRequest(requestPost, CONTENT_TYPE);
        List<String> response = Environment.getResponceRequest(requestPost);
        System.out.println(response);

        // get trx id from response message
        idTransaction = Arrays.asList(response.get(0).split("&")).get(0).replace("Id=","");

        // check response details
        Assert.assertTrue(TestUtils.checkParameter(response, "Operation=Auth"), "Incorrect operation type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Result=Ok" ), "Incorrect result type.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Code=200" ), "Incorrect code.");
        Assert.assertTrue(TestUtils.checkParameter(response,"Status=PreAuthorized" ), "Incorrect status type.");

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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleamountPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleamountPreauthPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleamountPreauthPart3, simpleamountPreauthPart3, testGateway, email);
    }

    // MERCHANT
    // 3DS partial
    @Test
    public void PendingRefund3DSPartialMerchant(){

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

    // sapmax
    // simple sapmax partial
    @Test
    public void PendingRefundSimpleSapmaxPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPendingPart1, simpleSapmaxamountPendingPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPendingPart1, simpleSapmaxamountPendingPart1, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimpleSapmaxPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimpleSapmaxPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart2, simpleSapmaxamountPreauthPart2, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart2, simpleSapmaxamountPreauthPart2, testGateway, cardHolderName, email);
    }

    // simple sapmax partial + partial
    @Test
    public void PendingRefundSimpleSapmaxPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPendingPart1, simpleSapmaxamountPendingPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPendingPart1, simpleSapmaxamountPendingPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPendingPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleSapmaxamountPendingPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPendingPart2, simpleSapmaxamountPendingPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPendingPart2,
                simpleSapmaxamountPendingPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimpleSapmaxPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleSapmaxamountPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart2, simpleSapmaxamountPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleamountPreauthPart2, simpleamountPreauthPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimpleSapmaxPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleSapmaxamountPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart2, simpleSapmaxamountPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthPart2,
                simpleSapmaxamountPreauthPart2, testGateway, cardHolderName, email);
    }

    // simple sapmax full
    @Test
    public void PendingRefundSimpleSapmaxFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPendingPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPendingPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPendingPart3, simpleSapmaxamountPendingPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPendingPart3, simpleSapmaxamountPendingPart3, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimpleSapmaxFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimpleSapmaxFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway, cardHolderName, email);
    }

    // sapmax
    // 3DS sapmax
    @Test
    public void PendingRefund3DSSapmaxPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpending3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPendingPart1, amount3DSSapmaxPendingPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPendingPart1, amount3DSSapmaxPendingPart1, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefund3DSSapmaxPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund3(driver, idTransaction, amount3DSSapmaxPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefund3DSSapmaxPartialMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, cardHolderName, email);
    }

    // 3DS sapmax partial + partial
    @Test
    public void PendingRefund3DSSapmaxPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpending3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPendingPart1, amount3DSSapmaxPendingPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPendingPart1, amount3DSSapmaxPendingPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPendingPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSSapmaxPendingPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPendingPart2, amount3DSSapmaxPendingPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPendingPart2,
                amount3DSSapmaxPendingPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefund3DSSapmaxPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSSapmaxPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart2, amount3DSSapmaxPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart2, amount3DSSapmaxPreauthPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefund3DSSapmaxPartPartMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSSapmaxPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart2, amount3DSSapmaxPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthPart2,
                amount3DSSapmaxPreauthPart2, testGateway, cardHolderName, email);
    }

    // 3DS sapmax full
    @Test
    public void PendingRefund3DSSapmaxFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpending3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPendingPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPendingPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPendingPart3, amount3DSSapmaxPendingPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPendingPart3, amount3DSSapmaxPendingPart3, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefund3DSSapmaxFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefund3DSSapmaxFullMerchant(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart3);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart3);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway, cardHolderName, email);
    }

    // sapmax
    // simple sapmax partial
    @Test
    public void PendingRefundSimpleSapmaxPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleSapmaxamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleSapmaxamountPendingPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPendingPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPendingPart1, simpleSapmaxamountPendingPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPendingPart1, simpleSapmaxamountPendingPart1, testGateway, email);
    }

    @Test
    public void PartialPreauthRefundSimpleSapmaxPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleSapmaxamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleSapmaxamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, email);
    }

    @Test
    public void FullPreauthRefundSimpleSapmaxPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleSapmaxamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleSapmaxamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, email);
    }

    // simple sapmax partial + partial
    @Test
    public void PendingRefundSimpleSapmaxPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpending, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPendingPart1, simpleSapmaxamountPendingPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPendingPart1, simpleSapmaxamountPendingPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPendingPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleSapmaxamountPendingPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPendingPart2, simpleSapmaxamountPendingPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPendingPart2,
                simpleSapmaxamountPendingPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefundSimpleSapmaxPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleSapmaxamountPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart2, simpleSapmaxamountPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart2, simpleSapmaxamountPreauthPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefundSimpleSapmaxPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, simpleSapmaxMIDpreAuth, idTransaction, id + orderID, cardHolderName,
                settledStatus, simpleSapmaxamountPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart1, simpleSapmaxamountPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(simpleSapmaxamountPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, simpleSapmaxamountPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart2, simpleSapmaxamountPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, simpleSapmaxamountPreauthPart2,
                simpleSapmaxamountPreauthPart2, testGateway, cardHolderName, email);
    }

    // simple sapmax full
    @Test
    public void PendingRefundSimpleSapmaxFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleSapmaxamountPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleSapmaxamountPendingPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPendingPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpending, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPendingPart3, simpleSapmaxamountPendingPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpending, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPendingPart3, simpleSapmaxamountPendingPart3, testGateway, email);
    }

    @Test
    public void PartialPreauthRefundSimpleSapmaxFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleSapmaxamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleSapmaxamountPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway, email);
    }

    @Test
    public void FullPreauthRefundSimpleSapmaxFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, simpleSapmaxamountPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(simpleSapmaxamountPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, simpleSapmaxamountPreauthPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, simpleSapmaxMIDpreAuth, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, simpleSapmaxamountPreauthPart3, simpleSapmaxamountPreauthPart3, testGateway, email);
    }

    // sapmax
    // 3DS sapmax partial
    @Test
    public void PendingRefund3DSSapmaxPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSSapmaxPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSSapmaxPendingPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPendingPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPendingPart1, amount3DSSapmaxPendingPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPendingPart1, amount3DSSapmaxPendingPart1, testGateway, email);
    }

    @Test
    public void PartialPreauthRefund3DSSapmaxPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSSapmaxPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSSapmaxPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, email);
    }

    @Test
    public void FullPreauthRefund3DSSapmaxPartialAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSSapmaxPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSSapmaxPreauthPart1);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart1);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, email);
    }

    // 3DS sapmax partial + partial
    @Test
    public void PendingRefund3DSSapmaxPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        //check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpending3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPendingPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPendingPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPendingPart1, amount3DSSapmaxPendingPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPendingPart1, amount3DSSapmaxPendingPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPendingPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSSapmaxPendingPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPendingPart2, amount3DSSapmaxPendingPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPendingPart2,
                amount3DSSapmaxPendingPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void PartialPreauthRefund3DSSapmaxPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSSapmaxPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart2, amount3DSSapmaxPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart2, amount3DSSapmaxPreauthPart2, testGateway, cardHolderName, email);
    }

    @Test
    public void FullPreauthRefund3DSSapmaxPartPartAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        // authorization merchant and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();

        // check registrationRefundForm
        TestUtils.checkRefundFormMerchant(driver, MIDpreAuth3DSSapmax, idTransaction, id + orderID, cardHolderName,
                settledStatus, amount3DSSapmaxPreauthComplete);

        // type amount and refund
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get idRefund
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart1);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, email);

        // admin authorization and transaction card checking
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart1, amount3DSSapmaxPreauthPart1, testGateway, cardHolderName, email);

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
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount3DSSapmaxPreauthPart2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();

        // get second refund id
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund2 = TestUtils.getIdTransactionRefund2(driver, idTransaction, amount3DSSapmaxPreauthPart2);

        // check transaction card
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund2, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart2, amount3DSSapmaxPreauthPart2, testGateway, email);

        // admin authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        // go to all transactions list
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        // check provided fields
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund2, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount3DSSapmaxPreauthPart2,
                amount3DSSapmaxPreauthPart2, testGateway, cardHolderName, email);
    }

    // 3DS sapmax full
    @Test
    public void PendingRefund3DSSapmaxFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

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

        //authorization admin and open registrationRefundForm
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.linkText("Вернуть деньги")).click();

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSSapmaxPending);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSSapmaxPendingPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPendingPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpending3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPendingPart3, amount3DSSapmaxPendingPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPendingPart3, amount3DSSapmaxPendingPart3, testGateway, email);
    }

    @Test
    public void PartialPreauthRefund3DSSapmaxFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSSapmaxPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSSapmaxPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway, email);
    }

    @Test
    public void FullPreauthRefund3DSSapmaxFullAdmin(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
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

        TestUtils.checkRefundFormAdmin(driver, completedRefundsAmount0, amount3DSSapmaxPreauthComplete);

        //type amount and refund
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).clear();
        driver.findElement(By.name("ctl00$content$actions$refundAmount")).sendKeys(amount3DSSapmaxPreauthPart3);
        driver.findElement(By.name("ctl00$content$actions$cmdRefund")).click();
        TestUtils.closeAlertAndGetItsText(driver);

        //authorization admin and get idRefund
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.linkText("Транзакции")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();

        idRefund = TestUtils.getIdTransactionRefund(driver, idTransaction, amount3DSSapmaxPreauthPart3);

        //check cardTransaction
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, lastActionRefund, pendingStatus,
                cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway, cardHolderName, email);

        //authorization merchant and check cardTransaction
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth3DSSapmax, idRefund, id + orderID, typeRefund, pendingStatus,
                cardHolderName, amount3DSSapmaxPreauthPart3, amount3DSSapmaxPreauthPart3, testGateway, email);
    }
}
