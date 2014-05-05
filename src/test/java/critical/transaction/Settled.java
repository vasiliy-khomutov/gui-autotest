package critical.transaction;


import critical.TestUtils;
import model.Connect;
import model.Environment;
import model.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Settled {


    private long id = System.currentTimeMillis();
    private String idTransaction;

    private String baseUrl = "https://secure.payonlinesystem.com/";
    private String loginMerchant = "info@smart-travel.ru";
    private String passwordMerchant = "123QWE!@#";
    private String captcha = "ability";
    private String pendingMercahnt = "#55477 - www.autotest.gui.com";
    private String optionPendingMerchant = "option[value=\"55477\"]";
    private String currencyRUB = "RUB";
    private String amount = "444";
    private String orderID = "GUI-autotest";
    private String email = "autoTEST@test.test";
    private String numberCardA = "4111";
    private String numberCardB = "1111";
    private String numberCardC = "1111";
    private String numberCardD = "1111";
    private String expDateMonth = "05";
    private String expDateYear = "2015";
    private String cardHolderName = "MR.AUTOTEST";
    private String cvc = "111";
    private String bank = "QA-BANK";

    @Test
    public void createSettledTransaction(){

    /*WebDriver driver = new FirefoxDriver();
    //authorization
    driver.get(baseUrl + "login/");
    Utils.authorized(driver,loginMerchant,passwordMerchant,captcha);

    //new transaction,payment and get id
    idTransaction =  TestUtils.getNewIdTransaction(driver, pendingMercahnt, optionPendingMerchant, id + orderID, amount, numberCardA, numberCardB, numberCardC, numberCardD,
    expDateMonth, expDateYear, cvc, bank, email, currencyRUB, cardHolderName);*/

    //change status transaction
    Connect connectDb = new Connect();
    connectDb.executeQuery("15206871");
    }
}
