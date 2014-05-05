package critical.transaction;


import critical.TestUtils;
import critical.callbacks.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Simple {

    private long id = System.currentTimeMillis();
    private String idTransaction;
    private String baseUrl;
    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin; //= "v.khomutov";
    private String passwordAdmin; //= "tester123";
    private String captcha = "ability";

    private String pendingMercahnt = "#57482 - www.test1.ru";
    private String optionPendingMerchant = "option[value=\"57482\"]";
    private String MIDpending = "57482";

    private String preAuthMercahnt = "#57483 - www.transactions.com";
    private String optionPreAuthMerchant = "option[value=\"57483\"]";
    private String MIDpreAuth = "57483";

    private String currencyRUB = "RUB";

    private String amount = "222";
    private String partialCompleteAmount = "22";

    private String orderID = "GUI-autotest";
    private String email = "autoTEST@test.test";
    private String numberCardA = "5555";
    private String numberCardB = "5555";
    private String numberCardC = "3333";
    private String numberCardD = "3333";
    private String expDateMonth = "05";
    private String expDateYear = "2015";

    //private String address = "3-я ул.Строителей, д.25, корп.1, кв.12";
    //private String city = "Москва";
    //private String zipCode = "123456";
    //private String country = "Россия";
    //private String phone = "+7 495 1234567";

    private String pendingMercahntUrl = "http://www.test1.ru";
    private String preAuthMercahntUrl = "http://www.transactions.com";

    private String expDate = "05 / 2015";
    private String cardHolderName = "MR.AUTOTEST";
    private String cvc = "111";
    private String bank = "QA-BANK";


    private String pendingStatus = "Pending";
    private String preAuthStatus = "PreAuthorized";
    private String testGateway = "Test gateway";
    private String typePurchase = "Purchase";

    //private String lastActionPreAuth = "PreAuth";
    private String lastActionComplete = "Complete";
    private String cardType = "MasterCard";

    @BeforeTest
    public void createParameters(){

       String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
    }

    @Test
    public void pendingTransaction(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, pendingMercahnt, optionPendingMerchant, id + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, pendingMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(pendingMercahntUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName, amount, amount, testGateway, email);

        //check in lk administrator
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpending, idTransaction, id + orderID, typePurchase, pendingStatus, cardType, numberCardA + numberCardB + numberCardC + numberCardD,
                expDate, bank, amount, amount, testGateway, cardHolderName, email);
    }

    @Test
    public void preAuthTransactionPartial(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, preAuthStatus, cardHolderName, amount, amount, testGateway, email);

        // partial preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(partialCompleteAmount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        TestUtils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + orderID, typePurchase, pendingStatus, cardHolderName, partialCompleteAmount, partialCompleteAmount, testGateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id + orderID, lastActionComplete, pendingStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, partialCompleteAmount, partialCompleteAmount, testGateway, cardHolderName, email);

    }

    @Test
    public void preAuthTransactionFull(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //generate link and payment
        idTransaction = TestUtils.getNewIdTransaction(driver, preAuthMercahnt, optionPreAuthMerchant, id + "1" + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD,
                expDateMonth, expDateYear, cvc, bank, email, currencyRUB,cardHolderName);

        //check result page
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, amount));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, id + "1" + orderID));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, preAuthMercahntUrl));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, cardHolderName));
        Assert.assertTrue(Utils.checkResultPageTransaction(driver, email));

        //check link
        driver.findElement(By.linkText("Завершить")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains(preAuthMercahntUrl));

        //check in lk merchant
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionMerchant(driver, MIDpreAuth, idTransaction, id + "1" + orderID, typePurchase, preAuthStatus, cardHolderName, amount, amount, testGateway, email);

        // full preauth complete
        driver.findElement(By.id("ctl00_content_view_cmdComplete")).click();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).clear();
        driver.findElement(By.id("ctl00_content_completeTransaction_amount")).sendKeys(amount);
        driver.findElement(By.id("ctl00_content_completeTransaction_cmdComplete")).click();
        Assert.assertTrue( driver.findElement(By.xpath("./*//*[@id='mainContent']/div[4]")).getText().contains("Транзакция подтверждена"));

        TestUtils.checkCompletedPreauth(driver, MIDpreAuth, idTransaction, id + "1" + orderID, typePurchase, pendingStatus, cardHolderName, amount, amount, testGateway, email);

        //check completed preauth at administrator backend
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        driver.findElement(By.id("ctl00_content_all")).click();
        TestUtils.checkCardTransactionAdmin(driver, MIDpreAuth, idTransaction, id +"1" + orderID, lastActionComplete, pendingStatus, cardType,
                numberCardA + numberCardB + numberCardC + numberCardD, expDate, bank, amount, amount, testGateway, cardHolderName, email);

    }
}
