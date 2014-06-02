package medium.filters;

import model.Captcha;
import model.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
    private String orderID = "";

    private String cardHolderName = "MR.AUTOTEST";
    private String numberCardA = "5555";
    private String numberCardB = "5555";
    private String numberCardC = "3333";
    private String numberCardD = "3333";
    private String expDateMonth = "05";
    private String expDateYear = "2015";
    private String expDate = "05 / 2015";
    private String cvc = "111";
    private String bank = "QA-BANK";
    private String address = "ул. Строителей, д.25, корп.1, кв.12";
    private String city = "Москва";
    private String country = "Россия";
    private String phone = "+74951234567";
    private String zip1;
    private String zip2;
    private String email1;
    private String email2;

    private String code200 = "200";
    private String code1100 = "1100";

    @BeforeTest
    public void setUp(){
        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
        long idzipemail = System.currentTimeMillis();
        zip1 = "1" + idzipemail;
        zip2 = "2" + idzipemail;
        email1 = "autotest1_" + idzipemail + "@test.test";
        email2 = "autotest2_" + idzipemail + "@test.test";
    }

    @Test (enabled = true)
    public void MatchEmailZip(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //enable MatchEmailZip Filter
        TestUtilsFilters.enableMatchEmailZipFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID);

        // case 1: transaction 1: zip 1 - email 1
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "1",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip1, country, phone, email1, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_MatchEmailZip(driver, idTransaction, email1, zip1, code200);

        // case 2: transaction 2: zip 2 - email 1
        driver.get(baseUrl + "login/");
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "2",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip2, country, phone, email1, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        driver.findElement(By.id("ctl00_content_filter_cmdClear")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_MatchEmailZip(driver, idTransaction, email1, zip2, code1100);

        // case 3: transaction 2: zip 2 - email 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "3",
                amount1, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip2, country, phone, email2, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtilsFilters.checkTransactionCard_Admin_MatchEmailZip(driver, idTransaction, email2, zip2, code200);

        // case 4: transaction 2: zip 1 - email 2
        Utils.login(driver, baseUrl, loginMerchant, passwordMerchant);
        idTransaction = TestUtilsFilters.getNewIdTransaction_Filter(driver, pendingMercahnt, optionPendingMerchant, id + orderID + "4",
                amount2, numberCardA, numberCardB, numberCardC, numberCardD, expDateMonth, expDateYear, cvc, bank, address,
                city, zip1, country, phone, email2, currencyRUB, cardHolderName, baseUrl, loginAdmin, passwordAdmin);

        // check trx
        driver.findElement(By.id("ctl00_content_filter_cmdClear")).click();
        driver.findElement(By.linkText(idTransaction)).click();
        TestUtilsFilters.checkTransactionCard_Admin_MatchEmailZip(driver, idTransaction, email2, zip1, code1100);

        //disable MatchEmailZip Filter
        TestUtilsFilters.disableMatchEmailZipFilter(driver, baseUrl, loginAdmin, passwordAdmin, MID);
    }
}
