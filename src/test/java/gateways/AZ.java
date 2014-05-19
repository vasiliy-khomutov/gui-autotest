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

import javax.rmi.CORBA.Util;

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
    private String numberCardA = "4127";
    private String numberCardB = "2099";
    private String numberCardC = "9999";
    private String numberCardD = "9581";
    private String expDateMonth = "04";
    private String expDateYear = "2015";
    private String expDate = "04 / 2015";
    private String cvc = "291";
    private String cardHolderName = "MR.AUTOTEST";
    private String bank = "QA-BANK";

    // additional parameters
    private String pendingStatus = "Pending";
    private String preauthStatus = "PreAuthorized";

    private String cardTypeVisa = "Visa";
    private String cardTypeMasterCard = "MasterCard";

    private String typePurchase = "Purchase";
    private String typePreAuth = "PreAuth";
    private String typeRefund = "Refund";

    private String gateway = "AzeriCard Test";
    private String amountUSD;
    private String idTransaction;
    private String idRefund;

    @BeforeTest
    public void createParameters(){

        // initialize authorization parameters
        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
    }

    // PENDING, PREAUTH, COMPLETE
    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "pendingAZ", enabled = false)
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

        System.out.println(amount + " " + amountUSD);
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

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "pendingAZ", enabled = true)
    public void preauth(String currency, String amount){

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

        System.out.println(amount + " " + amountUSD);
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardTypeVisa,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amountUSD, gateway, cardHolderName, email);

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePreAuth, preauthStatus, cardHolderName,
                amount, amountUSD, gateway, email);
    }


    /*@Test
    public void preauthAndPartialComplete(){}

    @Test
    public void preauthAndPartPartComplete(){}

    @Test
    public void preauthAndFullComplete(){}

    // VOIDS
    @Test
    public void pendingAndVoid(){}

    @Test
    public void preauthAndVoid(){}

    @Test
    public void preauthAndPartialCompleteAndVoid(){}

    @Test
    public void preauthAndPartPartCompleteAndVoid(){}

    @Test
    public void preauthAndFullCompleteAndVoid(){}


    // CHARGEBACKS
    @Test
    public void pendingAndCBK(){}

    @Test
    public void preauthAndCBK(){}

    @Test
    public void preauthAndPartialCompleteAndCBK(){}

    @Test
    public void preauthAndPartPartCompleteAndCBK(){}

    @Test
    public void preauthAndFullCompleteAndCBK(){}
*/
    // REFUNDS - ?
/*    @Test
    public void pendingAndRefund(){}

    @Test
    public void preauthAndRefund(){}

    @Test
    public void preauthAndPartialCompleteAndRefund(){}

    @Test
    public void preauthAndPartPartCompleteAndRefund(){}

    @Test
    public void preauthAndFullCompleteAndRefund(){}*/

    // 3DS - ?

}

