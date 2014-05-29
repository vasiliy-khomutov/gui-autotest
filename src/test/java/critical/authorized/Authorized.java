package critical.authorized;


import model.Captcha;
import model.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Authorized {

    private String baseUrl;
    private String loginMerchant;
    private String passwordMerchant;

    @BeforeTest
    public void setUp(){

        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
    }


    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "listFailLogin", enabled = true)
    public void failLogin(String failLogin){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, failLogin, passwordMerchant, Captcha.getCaptcha(driver));

        //check error message
        Assert.assertTrue(driver.findElement(By.className("warn")).isDisplayed(), "Error message is not displayed!");
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "listFailLoginAndPassword", enabled = true)
    public void failLoginAndPassword(String failLogin, String failPassword){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, failLogin, failPassword, Captcha.getCaptcha(driver));

        //check error message
        Assert.assertTrue(driver.findElement(By.className("warn")).isDisplayed(), "Error message is not displayed!");
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "listFailCaptcha", enabled = true)
    public void failCaptcha(String failCaptcha){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        long id = System.currentTimeMillis();

        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, failCaptcha);

        //check error message
        Assert.assertTrue(driver.findElement(By.className("warn")).isDisplayed(), "Error message is not displayed!");
    }
}
