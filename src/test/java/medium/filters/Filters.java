package medium.filters;

import critical.TestUtils;
import model.Connect;
import model.DriverFactory;
import model.Environment;
import model.Utils;
import org.apache.http.client.methods.HttpPost;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class Filters {

    private String idTransaction;
    private String baseUrl;
    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin;
    private String passwordAdmin;
    private String pendingMercahnt = "#62838 - www.test-filters.com";
    private String optionPendingMerchant = "option[value=\"62838\"]";
    private String MID = "62838";
    private String currencyRUB = "RUB";
    private String amount1 = "222";
    private String amount2 = "444";
    private String amountOdd = "333";
    private String orderID = "";

    private String numberCardA = "5555";
    private String numberCardB = "5555";
    private String numberCardC = "3333";
    private String numberCardD = "3333";
    private String BIN = "555555";

    private String numberCardA1 = "4532";
    private String numberCardB1 = "2532";
    private String numberCardC1 = "4716";
    private String numberCardD1 = "8820";
    private String BIN1 = "453225";

    private String numberCardA2 = "5286";
    private String numberCardB2 = "0456";
    private String numberCardC2 = "8003";
    private String numberCardD2 = "2207";
    private String BIN2 = "528604";

    private String expDateMonth = "05";
    private String expDateYear = "2015";
    private String expDate = "05 / 2015";
    private String cvc = "111";
    private String bank = "QA-BANK";
    private String address = "ул. Строителей, д.25, корп.1, кв.12";
    private String city = "Москва";
    private String country = "Россия";
    private String phone = "+74951234567";

    private String zip = "123456";
    private String zip1;
    private String zip2;

    private String email = "autotest@test.test";
    private String emailDomain = "test.test";
    private String email1;
    private String email2;

    private String cardHolderName = "MR.AUTOTEST";
    private String cardHolder1;
    private String cardHolder2;

    // filters
    private String filterMatchEmailCardHolderName = "Match.Email.CardHolderName";
    private String filterMatchEmailZip = "Match.Email.Zip";
    private String filterMatchCardCardHolderName = "Match.Card.CardHolderName";

    private String filterStopListBillingCountry = "StopList.BillingCountry";
    private String filterStopListCard = "StopList.Card";
    private String filterStopListEmail = "StopList.Email";
    private String filterStopListIpAddress = "StopList.IpAddress";
    private String filterStopListIpCountry = "StopList.IpCountry";
    private String filterStopListIssuerBinCountry = "StopList.IssuerBinCountry";
    private String filterStopListIssuerBin = "StopList.IssuerBin";
    private String filterStopListEmailDomain = "StopList.EmailDomain";
    private String filterStopListUserAgent = "StopList.UserAgent";
    private String filterStopListMerchantIssuerBinCountry = "StopList.Merchant.IssuerBinCountry";
    private String filterStopListMerchantIpCountry = "StopList.Merchant.IpCountry";

    private String filterLimitTransactionAmount = "Limit.Transaction.Amount";
    private String filterLimitIpEmailCount	= "Limit.Ip.EmailCount";
    private String filterLimitIpCardCount	= "Limit.Ip.CardCount";
    private String filterLimitIpDeclinedAttemptsCount	= "Limit.Ip.DeclinedAttemptsCount";
    private String filterLimitCardPurchaseAmount	= "Limit.Card.PurchaseAmount";
    private String filterLimitCardPurchaseCount	= "Limit.Card.PurchaseCount";
    private String filterLimitCardDeclinedAttemptsCount	= "Limit.Card.DeclinedAttemptsCount";
    private String filterLimitMerchantPurchaseCount	= "Limit.Merchant.PurchaseCount";
    private String filterLimitMerchantPurchaseAmount	= "Limit.Merchant.PurchaseAmount";
    private String filterLimitMerchantRefundAmount	= "Limit.Merchant.RefundAmount";
    private String filterLimitAccountIdPaymentParameters	= "Limit.AccountId.PaymentParameters";

    // codes
    private String code200 = "200";
    private String code2001 = "2001";
    private String code2002 = "2002";
    private String code2003 = "2003";
    private String code2004 = "2004";
    private String code2005 = "2005";
    private String code2006 = "2006";
    private String code2007 = "2007";
    private String code2008 = "2008";
    private String code2013 = "2013";
    private String code2050 = "2050";
    private String code2051 = "2051";
    private String code2052 = "2052";
    private String code2061 = "2061";
    private String code2062 = "2062";
    private String code2063 = "2063";
    private String code2064 = "2064";
    private String code2067 = "2067";
    private String code2100 = "2100";
    private String code2101 = "2101";
    private String code2102 = "2102";
    private String code2110 = "2110";
    private String code2111 = "2111";

    @BeforeTest
    public void setUp(){
        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
        cardHolder1 = "JOHN JUNIOR SMITH";
        cardHolder2 = "JOHN JUNIOR BUSH";

        long idzipemail = System.currentTimeMillis();
        zip1 = "1" + idzipemail;
        zip2 = "2" + idzipemail;
        email1 = "autotest1_" + idzipemail + "@test.ru";
        email2 = "autotest2_" + idzipemail + "@test.ru";
    }

    @Test (enabled = true)
    public void MatchEmailZip(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        long idzipemail = System.currentTimeMillis();
        zip1 = "1" + idzipemail;
        zip2 = "2" + idzipemail;
        email1 = "autotest1_" + idzipemail + "@test.ru";
        email2 = "autotest2_" + idzipemail + "@test.ru";

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterMatchEmailZip);

        // case 1: transaction 1: zip 1 - email 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip1, country, phone, email1, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email1, zip1, code200, filterMatchEmailZip);

        // case 2: transaction 2: zip 2 - email 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip2, country, phone, email1, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        driver.findElement(By.id("ctl00_content_filter_cmdClear")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email1, zip2, code2102, filterMatchEmailZip);

        // case 3: transaction 2: zip 2 - email 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "3",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip2, country, phone, email2, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email2, zip2, code200, filterMatchEmailZip);

        // case 4: transaction 2: zip 1 - email 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "4",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip1, country, phone, email2, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        driver.findElement(By.id("ctl00_content_filter_cmdClear")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email2, zip1, code2102, filterMatchEmailZip);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterMatchEmailZip);
    }

    @Test (enabled = false)
    public void MatchCardCardHolderName(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();
        String cardNumberFull1 = TestUtilsFilters.generateCardNumber("Visa");
        String numberCardArandom1 = cardNumberFull1.substring(0, 4);
        String numberCardBrandom1 = cardNumberFull1.substring(4, 8);
        String numberCardCrandom1 = cardNumberFull1.substring(8, 12);
        String numberCardDrandom1 = cardNumberFull1.substring(12, cardNumberFull1.length());

        String cardNumberFull2 = TestUtilsFilters.generateCardNumber("MasterCard");
        String numberCardArandom2 = cardNumberFull2.substring(0, 4);
        String numberCardBrandom2 = cardNumberFull2.substring(4, 8);
        String numberCardCrandom2 = cardNumberFull2.substring(8, 12);
        String numberCardDrandom2 = cardNumberFull2.substring(12, cardNumberFull2.length());

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterMatchCardCardHolderName);

        // case 1: transaction 1: cardholder 1 - card 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardArandom1, numberCardBrandom1, numberCardCrandom1, numberCardDrandom1, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolder1, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, numberCardArandom1+numberCardBrandom1+numberCardCrandom1+numberCardDrandom1,
                cardHolder1, code200, filterMatchCardCardHolderName);

        // case 2: transaction 2: cardholder 2 - card 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardArandom1, numberCardBrandom1, numberCardCrandom1, numberCardDrandom1, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolder2, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        driver.findElement(By.id("ctl00_content_filter_cmdClear")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, numberCardArandom1+numberCardBrandom1+numberCardCrandom1+numberCardDrandom1,
                cardHolder2, code2100, filterMatchCardCardHolderName);

        // case 3: transaction 2: cardholder 2 - card 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "3",
                amount1, numberCardArandom2, numberCardBrandom2, numberCardCrandom2, numberCardDrandom2, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolder2, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, numberCardArandom2+numberCardBrandom2+numberCardCrandom2+numberCardDrandom2,
                cardHolder2, code200, filterMatchCardCardHolderName);

        // case 4: transaction 2: cardholder 1 - card 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "4",
                amount2, numberCardArandom2, numberCardBrandom2, numberCardCrandom2, numberCardDrandom2, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolder1, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        driver.findElement(By.id("ctl00_content_filter_cmdClear")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, numberCardArandom2+numberCardBrandom2+numberCardCrandom2+numberCardDrandom2,
                cardHolder1, code2100, filterMatchCardCardHolderName);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterMatchCardCardHolderName);
    }

    @Test (enabled = false)
    public void StopListBillingCountry() {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListBillingCountry);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, country, null, code200, filterStopListBillingCountry);

        // add to stop list
        // TODO
        country = "417";
        TestUtilsFilters.addToStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListBillingCountry, country);
        country = "Россия";

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, country, null, code2004, filterStopListBillingCountry);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListBillingCountry);

        // remove from stop list
        TestUtilsFilters.removeFromStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListBillingCountry, country);
    }

    @Test (enabled = false)
    public void StopListCard() {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListCard);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, numberCardA+numberCardB+numberCardC+numberCardD, null, code200, filterStopListCard);

        // add to stop list
        TestUtilsFilters.addToStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListCard, numberCardA+numberCardB+numberCardC+numberCardD);

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, numberCardA+numberCardB+numberCardC+numberCardD, null, code2002, filterStopListCard);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListCard);

        // remove from stop list
        TestUtilsFilters.removeFromStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListCard, numberCardA+numberCardB+numberCardC+numberCardD);
    }

    @Test (enabled = false)
    public void StopListEmail() {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListEmail);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email, null, code200, filterStopListEmail);

        // add to stop list
        TestUtilsFilters.addToStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListEmail, email);

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email, null, code2003, filterStopListEmail);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListEmail);

        // remove from stop list
        TestUtilsFilters.removeFromStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListEmail, email);
    }

    @Test (enabled = false)
    public void StopListIpAddress () {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListIpAddress);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code200, filterStopListIpAddress);

        String ipAddress = driver.findElement(By.xpath(".//*[@id='tran-ip']")).getText();

        // add to stop list
        TestUtilsFilters.addToStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListIpAddress, ipAddress);

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email, null, code2001, filterStopListIpAddress);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListIpAddress);

        // remove from stop list
        TestUtilsFilters.removeFromStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListIpAddress, ipAddress);
    }

    @Test (enabled = false)
    public void StopListIpCountry() {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListIpCountry);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code200, filterStopListIpCountry);

        // add to stop list
        String ipAddress = driver.findElement(By.xpath(".//*[@id='tran-ip']")).getText();
        TestUtilsFilters.addIpAddressLookup(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListIpCountry, ipAddress);

        // TODO 1 - добавление и удаление страны из стоп-листа IP страны
        // на текущий момент страна в списке - ip привязывается к стране через справочник + удаляется

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code2005, filterStopListIpCountry);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListIpCountry);

        // remove from stop list
        // TODO - см. todo 1

        TestUtilsFilters.removeIpAddressLookup(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListIpCountry, ipAddress);
    }

    @Test (enabled = false)
    public void StopListIssuerBinCountry () {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListIssuerBinCountry);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code200, filterStopListIssuerBinCountry);

        // TODO 1 - добавление и удаление (привязка BIN к стране, которая в стоп-листе)
        TestUtilsFilters.addBINLookup(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListIssuerBinCountry, BIN);

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code2006, filterStopListIssuerBinCountry);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListIssuerBinCountry);

        // remove from stop list
        // TODO - см. todo 1

        TestUtilsFilters.removeBINLookup(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListIssuerBinCountry, BIN);
    }

    @Test (enabled = false)
    public void StopListIssuerBin () {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListIssuerBin);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code200, filterStopListIssuerBin);

        TestUtilsFilters.addToStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListIssuerBin, BIN);

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code2008, filterStopListIssuerBin);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListIssuerBin);

        // remove from stop list
        TestUtilsFilters.removeFromStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListIssuerBin, BIN);

    }

    @Test (enabled = false)
    public void StopListEmailDomain () {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListEmailDomain);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code200, filterStopListEmailDomain);

        TestUtilsFilters.addToStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListEmailDomain, emailDomain);

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code2007, filterStopListEmailDomain);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListEmailDomain);

        // remove from stop list
        TestUtilsFilters.removeFromStopList(driver, baseUrl, loginAdmin, passwordAdmin, filterStopListEmailDomain, emailDomain);
    }

    @Test (enabled = false)
    public void StopListUserAgent () {

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListUserAgent);

        // case 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code200, filterStopListUserAgent);

        String [] uaData = TestUtilsFilters.getUserAgentData(driver);
        TestUtilsFilters.addToStopListUserAgent(driver, baseUrl, loginAdmin, passwordAdmin, MID, uaData[0], uaData[1], uaData[2], uaData[3], uaData[4]);

        // case 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, null, null, code2013, filterStopListUserAgent);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterStopListUserAgent);

        // remove from stop list
        TestUtilsFilters.removeFromStopListUserAgent(driver, baseUrl, loginAdmin, passwordAdmin, MID, uaData[0], uaData[1], uaData[3], uaData[4]);
    }

    @Test (enabled = false)
    public void StopListMerchantIssuerBinCountry () {
        // check filter algorithm
    }

    @Test (enabled = false)
    public void StopListMerchantIpCountry () {
        // check filter algorithm
    }

    @Test (enabled = false)
    public void LimitTransactionAmount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitTransactionAmount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount1, null, code200, filterLimitTransactionAmount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount2, null, code2050, filterLimitTransactionAmount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitTransactionAmount);
    }

    @Test (enabled = false)
    public void LimitIpEmailCount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitIpEmailCount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email1, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email1, null, code200, filterLimitIpEmailCount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email2, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, email2, null, code2063, filterLimitIpEmailCount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitIpEmailCount);
    }

    @Test (enabled = false)
    public void LimitIpCardCount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        String cardNumberFull1 = TestUtilsFilters.generateCardNumber("Visa");
        String numberCardArandom1 = cardNumberFull1.substring(0, 4);
        String numberCardBrandom1 = cardNumberFull1.substring(4, 8);
        String numberCardCrandom1 = cardNumberFull1.substring(8, 12);
        String numberCardDrandom1 = cardNumberFull1.substring(12, cardNumberFull1.length());

        String cardNumberFull2 = TestUtilsFilters.generateCardNumber("MasterCard");
        String numberCardArandom2 = cardNumberFull2.substring(0, 4);
        String numberCardBrandom2 = cardNumberFull2.substring(4, 8);
        String numberCardCrandom2 = cardNumberFull2.substring(8, 12);
        String numberCardDrandom2 = cardNumberFull2.substring(12, cardNumberFull2.length());

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitIpCardCount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardArandom1, numberCardBrandom1, numberCardCrandom1, numberCardDrandom1, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, cardNumberFull1, null, code200, filterLimitIpCardCount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardArandom2, numberCardBrandom2, numberCardCrandom2, numberCardDrandom2, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, cardNumberFull2, null, code2062, filterLimitIpCardCount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitIpCardCount);
    }

    @Test (enabled = false)
    public void LimitIpDeclinedAttemptsCount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitIpDeclinedAttemptsCount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amountOdd, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email1, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amountOdd, null, code200, filterLimitIpDeclinedAttemptsCount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amountOdd, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email2, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amountOdd, null, code2111, filterLimitIpDeclinedAttemptsCount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitIpDeclinedAttemptsCount);
    }

    @Test (enabled = false)
    public void LimitCardPurchaseAmount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitCardPurchaseAmount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount1, null, code200, filterLimitCardPurchaseAmount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount2, null, code2051, filterLimitCardPurchaseAmount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitCardPurchaseAmount);
    }

    @Test (enabled = false)
    public void LimitCardPurchaseCount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitCardPurchaseCount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount1, null, code200, filterLimitCardPurchaseCount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount2, null, code2061, filterLimitCardPurchaseCount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitCardPurchaseCount);
    }

    @Test (enabled = false)
    public void LimitCardDeclinedAttemptsCount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitCardDeclinedAttemptsCount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amountOdd, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email1, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amountOdd, null, code200, filterLimitCardDeclinedAttemptsCount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amountOdd, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email2, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amountOdd, null, code2110, filterLimitCardDeclinedAttemptsCount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitCardDeclinedAttemptsCount);
    }

    @Test (enabled = false)
    public void LimitMerchantPurchaseCount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitMerchantPurchaseCount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount1, null, code200, filterLimitMerchantPurchaseCount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount2, null, code2064, filterLimitMerchantPurchaseCount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitMerchantPurchaseCount);

    }

    @Test (enabled = false)
    public void LimitMerchantPurchaseAmount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitMerchantPurchaseAmount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount1, null, code200, filterLimitMerchantPurchaseAmount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount2, null, code2052, filterLimitMerchantPurchaseAmount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitMerchantPurchaseAmount);
    }

    @Test (enabled = false)
    public void LimitMerchantRefundAmount(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitMerchantRefundAmount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        //change status transaction
        Connect connectDb = new Connect();
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // make refund
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount1);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        String idRefund = Utils.getIdTransactionRefund(driver, idTransaction, amount1);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idRefund, amount1, null, code200, filterLimitMerchantRefundAmount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        //change status transaction
        connectDb.executeQuery(idTransaction);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // make refund
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        driver.findElement(By.name("ctl00$content$view$cmdRefund")).click();
        driver.findElement(By.name("ctl00$content$refundTransaction$amount")).sendKeys(amount2);
        driver.findElement(By.name("ctl00$content$refundTransaction$cmdRefund")).click();
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        String idRefund2 = Utils.getIdTransactionRefund(driver, idTransaction, amount2);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idRefund2, amount2, null, code2067, filterLimitMerchantRefundAmount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitMerchantRefundAmount);
    }

    @Test (enabled = false)
    public void LimitAccountIdPaymentParameters(){
        // api trx
    }


    // matches
    @Test (enabled = false)
    public void MatchBrowserLanguageBillingCountry(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable Filter
        TestUtilsFilters.enableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitMerchantPurchaseAmount);

        // trx 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount1, null, code200, filterLimitMerchantPurchaseAmount);

        // trx 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip, country, phone, email, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_Filter(driver, idTransaction, amount2, null, code2052, filterLimitMerchantPurchaseAmount);

        //disable Filter
        TestUtilsFilters.disableFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID, filterLimitMerchantPurchaseAmount);
    }

    @Test (enabled = false)
    public void MatchUserAgentTimeZone(){
    }

    @Test (enabled = false)
    public void MatchBillingCountryIpCountry(){
    }

    @Test (enabled = false)
    public void MatchBillingCountryBinCountry(){
    }

    @Test (enabled = false)
    public void MatchBinCountryIpCountry(){
    }

}
