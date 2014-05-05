package critical.authorized;


import critical.callbacks.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class Authorized {


    private String baseUrl = "https://secure.payonlinesystem.com/";
    private String loginMerchant = "info@smart-travel.ru";
    private String passwordMerchant = "tester123";
    private String captcha = "ability";


    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "listFailLogin")
    public void failLogin(String failLogin){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, failLogin, passwordMerchant, captcha);

        //check error message
        Assert.assertTrue(driver.findElement(By.className("warn")).isDisplayed(), "Error message is not displayed!");
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "listFailLoginAndPassword")
    public void failLoginAndPassword(String failLogin, String failPassword){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, failLogin, failPassword, captcha);

        //check error message
        Assert.assertTrue(driver.findElement(By.className("warn")).isDisplayed(), "Error message is not displayed!");
    }

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "listFailCaptcha")
    public void failCaptcha(String failCaptcha){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, failCaptcha);

        //check error message
        Assert.assertTrue(driver.findElement(By.className("warn")).isDisplayed(), "Error message is not displayed!");
    }

   /* @AfterClass
    public void finish(){
        driver.quit();
    }*/
}
