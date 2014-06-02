package gateways;

import model.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class A2S {

    String [] parameters = Environment.readFile();
    private String baseUrl = parameters[0];
    private String loginAdmin = parameters[1];;
    private String passwordAdmin = parameters[2];
    private String loginMerchantA2S = parameters[5];
    private String passwordMerchantA2S = parameters[6];
    private String captcha = "ability";

    // merchant details
    private String MIDA2S = "55456";
    private String MIDA2S_3DS = "61599";
    private String MIDA2S_RoutedFrom = "61597";
    private String MIDA2S_RoutedTo = "61598";

    private String avia2SMercahntUrl = "mvm-tour.ru";
    private String avia2SMercahntUrl_RoutedFrom = "mvm-tour2.ru";
    private String avia2SMercahntUrl_RoutedTo = "mvm-tour3.ru";
    private String avia2SMercahntUrl_3DS = "mvm-tour4.ru";

    private String PrivateSecurityKey_A2S = "56d6db6f-c814-4ac3-9aa5-634dff16d223";
    private String PrivateSecurityKey_A2S_3DS = "cf151491-352d-4247-9965-091f140d1796";
    private String PrivateSecurityKey_A2S_RoutedFrom = "f966c6ee-507b-4fa1-8e61-41a311030ed4";

    // trx details
    private String idTransaction;
    private String idTransactionComm;
    private String idTransactionAvia;
    private String idTransactionRouted;
    private String paymentLink;
    private String orderID = "";
    private String email = "autoTEST@test.test";
    private String numberCardA = "4111";
    private String numberCardB = "1111";
    private String numberCardC = "1111";
    private String numberCardD = "1111";
    private String numberCardA_a3ds = "4444";
    private String numberCardB_a3ds = "4444";
    private String numberCardC_a3ds = "4444";
    private String numberCardD_a3ds = "6666";
    private String expDateMonth = "09";
    private String expDateYear = "2014";
    private String expDate = "09 / 2014";
    private String cardHolderName = "MR.AUTOTEST";
    private String cvc = "321";
    private String cvc_a3ds = "001";
    private String bank = "QA-BANK";

    // transaction amounts
    private String commAmountOdd = "31";
    private String commAmountEven = "30";
    private String commAmountZero = "0";
    private String ticketAmountOdd = "471";
    private String ticketAmountEven = "470";
    private String totalAmountOdd = "501";
    private String totalAmountEven = "500";

    // additional info
    private String pendingStatus = "Pending";
    private String declinedStatus = "Declined";
    private String voidedStatus = "Voided";
    private String routedStatus = "Routed";
    private String aw3dsStatus = "Awaiting3DAuthentication";

    private String commTestGateway = " Test gateway";
    private String aviaTestGateway = " Test Gateway";
    private String advancedTestGateway = "Advanced Test Gateway";
    private String a2sGateway = "Avia";

    private String cardTypeVisa = "Visa";
    private String typePurchase = "Purchase";

    private String yaUrl = "https://www.ya.ru/";

    // link generation parameters
    private String URL;
    private String MERCHANT_ID_A2S;
    private String MERCHANT_ID_A2S_A3DS;
    private String MERCHANT_ID_A2S_ROUTEDFROM;
    private String PNR;
    private String PRIVATE_SECURITY_KEY_A2S;
    private String PRIVATE_SECURITY_KEY_A2S_A3DS;
    private String PRIVATE_SECURITY_KEY_A2S_ROUTED;
    private String SecurityKey;
    private String ORDER_ID;
    private String AMOUNT;
    private String COMMISSION;
    private String CURRENCY_RUB;
    private String RETURNURL;
    private String IDATA;

    @BeforeClass
    @Parameters({"url", "MerchantId", "OrderId", "CurrencyRub", "Pnr", "PrivateSecurityKey", "ReturnUrl", "Idata"})
    public void createCorrectParameters(String url, String MerchantId, String OrderId, String CurrencyRub, String Pnr,
                                        String PrivateSecurityKey, String ReturnUrl, String Idata){

        URL = url + "/?";
        MERCHANT_ID_A2S = MerchantId + MIDA2S;
        MERCHANT_ID_A2S_A3DS = MerchantId + MIDA2S_3DS;
        MERCHANT_ID_A2S_ROUTEDFROM = MerchantId + MIDA2S_RoutedFrom;
        ORDER_ID = OrderId;
        CURRENCY_RUB = CurrencyRub;
        PRIVATE_SECURITY_KEY_A2S = PrivateSecurityKey + PrivateSecurityKey_A2S;
        PRIVATE_SECURITY_KEY_A2S_A3DS = PrivateSecurityKey + PrivateSecurityKey_A2S_3DS;
        PRIVATE_SECURITY_KEY_A2S_ROUTED = PrivateSecurityKey + PrivateSecurityKey_A2S_RoutedFrom;
        RETURNURL = ReturnUrl;
        IDATA = Idata;
        PNR = Pnr;
    }

    // case 1
    @Test
    public void commissionGWDeclinedTRX_RoutingOFF(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        AMOUNT  = "Amount=" + totalAmountOdd + ".00";
        COMMISSION  = "Commission=" + commAmountOdd + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // declined trx - intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        //check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, totalAmountOdd));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl));

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransactionComm = TestUtils.getA2STransactionId(driver, commAmountOdd, MIDA2S);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, declinedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, commAmountOdd, commAmountOdd,
                commTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, declinedStatus,
                cardHolderName, commAmountOdd, commAmountOdd, commTestGateway, email);
    }

    // case 2
    @Test
    public void aviaGWDeclinedTRX_RoutingOFF(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();

        AMOUNT  = "Amount=" + totalAmountOdd + ".00";
        COMMISSION  = "Commission=" + commAmountEven + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // declined trx - intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        //check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, totalAmountOdd));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl));

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransactionComm = TestUtils.getA2STransactionId(driver, commAmountEven, MIDA2S);
        idTransactionAvia = TestUtils.getA2STransactionId(driver, ticketAmountOdd, MIDA2S);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, voidedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, commAmountEven, commAmountEven,
                commTestGateway, cardHolderName, email);
        TestUtils.checkCardTransactionAdmin(driver, MIDA2S, idTransactionAvia, id + orderID, typePurchase, declinedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, ticketAmountOdd, ticketAmountOdd,
                aviaTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, voidedStatus,
                cardHolderName, commAmountEven, commAmountEven, commTestGateway, email);
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S, idTransactionAvia, id + orderID, typePurchase, declinedStatus,
                cardHolderName, ticketAmountOdd, ticketAmountOdd, aviaTestGateway, email);
    }

    // case 3
    @Test
    public void successfulTRX_RoutingOFF(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + totalAmountEven + ".00";
        COMMISSION  = "Commission=" + commAmountEven + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        //check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, ticketAmountEven));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl));

        //check link - successful case
        // занимает время из-за ожидания перехода по ссылке
        //driver.findElement(By.linkText("Завершить")).click();
        //Assert.assertTrue(driver.getCurrentUrl().contains(yaUrl));
        driver.findElement(By.linkText("Завершить")).getText().contains(yaUrl);

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransactionComm = TestUtils.getA2STransactionId(driver, commAmountEven, MIDA2S);
        idTransactionAvia = TestUtils.getA2STransactionId(driver, ticketAmountEven, MIDA2S);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, commAmountEven, commAmountEven,
                commTestGateway, cardHolderName, email);
        TestUtils.checkCardTransactionAdmin(driver, MIDA2S, idTransactionAvia, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, ticketAmountEven, ticketAmountEven,
                aviaTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, pendingStatus,
                cardHolderName, commAmountEven, commAmountEven, commTestGateway, email);
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S, idTransactionAvia, id + orderID, typePurchase, pendingStatus,
                cardHolderName, ticketAmountEven, ticketAmountEven, aviaTestGateway, email);
    }

    // case 4
    @Test
    public void zeroCommissionDeclinedTRX_RoutingOFF(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + totalAmountOdd + ".00";
        COMMISSION  = "Commission=" + commAmountZero + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // declined trx - intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        // check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, totalAmountOdd));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl));

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl , loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransactionComm = TestUtils.getA2STransactionId(driver, commAmountZero, MIDA2S);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, declinedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, totalAmountOdd, totalAmountOdd,
                aviaTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, declinedStatus,
                cardHolderName, totalAmountOdd, totalAmountOdd, aviaTestGateway, email);
    }

    // case 5
    @Test
    public void zeroCommissionSuccesfulTRX_RoutingOFF(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + totalAmountEven + ".00";
        COMMISSION  = "Commission=" + commAmountZero + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // declined trx - intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        // check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, totalAmountEven));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl));

        //check link - successful case
        // занимает время из-за ожидания перехода по ссылке
        //driver.findElement(By.linkText("Завершить")).click();
        //Assert.assertTrue(driver.getCurrentUrl().contains(yaUrl));
        driver.findElement(By.linkText("Завершить")).getText().contains(yaUrl);

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransactionComm = TestUtils.getA2STransactionId(driver, commAmountZero, MIDA2S);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, totalAmountEven, totalAmountEven,
                aviaTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S, idTransactionComm, id + orderID, typePurchase, pendingStatus,
                cardHolderName, totalAmountEven, totalAmountEven, aviaTestGateway, email);
    }

    // TODO cases 6,7,8 - add rebill flag checking
    // case 6
    @Test
    public void commissionGWDeclinedTRX_RoutingON(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + totalAmountOdd + ".00";
        COMMISSION  = "Commission=" + commAmountOdd + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S_ROUTEDFROM, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, IDATA, PRIVATE_SECURITY_KEY_A2S_ROUTED);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S_ROUTEDFROM, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, IDATA, PRIVATE_SECURITY_KEY_A2S_ROUTED, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // declined trx - intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        //check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, totalAmountOdd));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl_RoutedTo));

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransactionComm = TestUtils.getA2STransactionId(driver, commAmountOdd, MIDA2S_RoutedFrom);
        idTransactionRouted = TestUtils.getA2STransactionId(driver, totalAmountOdd, MIDA2S_RoutedTo);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_RoutedTo, idTransactionRouted, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, totalAmountOdd, totalAmountOdd,
                advancedTestGateway, cardHolderName, email);
        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_RoutedFrom, idTransactionComm, id + orderID, typePurchase, routedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, commAmountOdd, commAmountOdd,
                commTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_RoutedTo, idTransactionRouted, id + orderID, typePurchase, pendingStatus,
                cardHolderName, totalAmountOdd, totalAmountOdd, advancedTestGateway, email);
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_RoutedFrom, idTransactionComm, id + orderID, typePurchase, routedStatus,
                cardHolderName, commAmountOdd, commAmountOdd, commTestGateway, email);
    }

    // case 7
    @Test
    public void aviaGWDeclinedTRX_RoutingON(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + totalAmountOdd + ".00";
        COMMISSION  = "Commission=" + commAmountEven + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S_ROUTEDFROM, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, IDATA, PRIVATE_SECURITY_KEY_A2S_ROUTED);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S_ROUTEDFROM, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, IDATA, PRIVATE_SECURITY_KEY_A2S_ROUTED, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // declined trx - intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        //check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, totalAmountOdd));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl_RoutedTo));

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransactionComm = TestUtils.getA2STransactionId(driver, commAmountEven, MIDA2S_RoutedFrom);
        idTransaction = TestUtils.getA2STransactionId(driver, totalAmountOdd, MIDA2S_RoutedTo);
        idTransactionAvia = TestUtils.getA2STransactionId(driver, ticketAmountOdd, MIDA2S_RoutedFrom);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_RoutedTo, idTransaction, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, totalAmountOdd, totalAmountOdd,advancedTestGateway, cardHolderName, email);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_RoutedFrom, idTransactionAvia, id + orderID, typePurchase, routedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, ticketAmountOdd, ticketAmountOdd, aviaTestGateway, cardHolderName, email);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_RoutedFrom, idTransactionComm, id + orderID, typePurchase, voidedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, commAmountEven, commAmountEven, commTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_RoutedTo, idTransaction, id + orderID, typePurchase, pendingStatus,
                cardHolderName, totalAmountOdd, totalAmountOdd, advancedTestGateway, email);

        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_RoutedFrom, idTransactionAvia, id + orderID, typePurchase, routedStatus,
                cardHolderName, ticketAmountOdd, ticketAmountOdd, aviaTestGateway, email);

        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_RoutedFrom, idTransactionComm, id + orderID, typePurchase, voidedStatus,
                cardHolderName, commAmountEven, commAmountEven, commTestGateway, email);
    }

    // case 8
    @Test
    public void zeroCommissionDeclinedTRX_RoutingON(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + totalAmountOdd + ".00";
        COMMISSION  = "Commission=" + commAmountZero + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S_ROUTEDFROM, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, IDATA, PRIVATE_SECURITY_KEY_A2S_ROUTED);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S_ROUTEDFROM, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, IDATA, PRIVATE_SECURITY_KEY_A2S_ROUTED, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // declined trx - intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        //check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, totalAmountOdd));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl_RoutedTo));

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver,baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransactionRouted = TestUtils.getA2STransactionId(driver, totalAmountOdd, MIDA2S_RoutedFrom);
        idTransaction = TestUtils.getA2STransactionId(driver, totalAmountOdd, MIDA2S_RoutedTo);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_RoutedFrom, idTransactionRouted, id + orderID, typePurchase, routedStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, totalAmountOdd, totalAmountOdd,
                aviaTestGateway, cardHolderName, email);
        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_RoutedTo, idTransaction, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, totalAmountOdd, totalAmountOdd,
                advancedTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_RoutedFrom, idTransactionRouted, id + orderID, typePurchase, routedStatus,
                cardHolderName, totalAmountOdd, totalAmountOdd, aviaTestGateway, email);
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_RoutedTo, idTransaction, id + orderID, typePurchase, pendingStatus,
                cardHolderName, totalAmountOdd, totalAmountOdd, advancedTestGateway, email);
    }

    // case 9
    @Test
    public void zeroCommissionSuccesfulTRX_RoutingON(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + totalAmountEven + ".00";
        COMMISSION  = "Commission=" + commAmountZero + ".00";

        // get security key
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S_ROUTEDFROM, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, IDATA, PRIVATE_SECURITY_KEY_A2S_ROUTED);

        // generate payment link and open it
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S_ROUTEDFROM, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, IDATA, PRIVATE_SECURITY_KEY_A2S_ROUTED, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // declined trx - intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth,
                expDateYear, cardHolderName, cvc, bank, email);

        //check result page
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, totalAmountEven));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, id + ""));
        Assert.assertTrue(TestUtils.checkA2STransactionResultPage(driver, avia2SMercahntUrl_RoutedFrom));

        //check link - successful case
        // занимает время из-за ожидания перехода по ссылке
        //driver.findElement(By.linkText("Завершить")).click();
        //Assert.assertTrue(driver.getCurrentUrl().contains(yaUrl));
        driver.findElement(By.linkText("Завершить")).getText().contains(yaUrl);

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        idTransaction = TestUtils.getA2STransactionId(driver, totalAmountEven, MIDA2S_RoutedFrom);

        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_RoutedFrom, idTransaction, id + orderID, typePurchase, pendingStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, totalAmountEven, totalAmountEven,
                aviaTestGateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();

        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_RoutedFrom, idTransaction, id + orderID, typePurchase, pendingStatus,
                cardHolderName, totalAmountEven, totalAmountEven, aviaTestGateway, email);
    }

    // case 10
    @Test
    public void getAW3DSStatusTrx(){

        long id = System.currentTimeMillis();
        WebDriver driver = DriverFactory.getInstance().getDriver();
        AMOUNT  = "Amount=" + totalAmountOdd + ".00";
        COMMISSION  = "Commission=" + commAmountOdd + ".00";

        // generate payment link and open it
        SecurityKey = "SecurityKey=" + Utils.getA2SSecurityKey(MERCHANT_ID_A2S_A3DS, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S_A3DS);
        paymentLink = TestUtils.getA2SIdTransactionLink(URL, MERCHANT_ID_A2S_A3DS, ORDER_ID + id, AMOUNT, CURRENCY_RUB, COMMISSION, PNR, PRIVATE_SECURITY_KEY_A2S_A3DS, SecurityKey, RETURNURL);
        driver.get(paymentLink);

        // intitiate trx
        TestUtils.initiateA2STransactionId(driver, numberCardA_a3ds, numberCardB_a3ds, numberCardC_a3ds, numberCardD_a3ds, expDateMonth,
                expDateYear, cardHolderName, cvc_a3ds, bank, email);

        //check 3DS page
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']")).getText().contains(totalAmountOdd));
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']")).getText().contains(id+""));
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='mainContent']")).getText().contains("Ваша карта зарегистрирована в"));

        // trx doesn't completed, testing aw3ds status only

        // administrator interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
        driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(id+"");
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();

        // get trx id
        idTransaction = TestUtils.getA2STransactionId(driver, totalAmountOdd, MIDA2S_3DS);
        TestUtils.checkCardTransactionAdmin(driver, MIDA2S_3DS, idTransaction, id + orderID, typePurchase, aw3dsStatus, cardTypeVisa,
                numberCardA_a3ds + numberCardB_a3ds + numberCardC_a3ds + numberCardD_a3ds, expDate, bank, totalAmountOdd, commAmountOdd,
                a2sGateway, cardHolderName, email);

        // merchant interface
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchantA2S, passwordMerchantA2S);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDA2S_3DS, idTransaction, id + orderID, typePurchase, aw3dsStatus,
                cardHolderName, totalAmountOdd, commAmountOdd, a2sGateway, email);
    }
}