package low;


import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class LocalizationX {

    private WebDriver driver = Environment.createDriver();
    private String baseUrl = "https://secure.payonlinesystem.com/";
    private String loginMerchant = "info@smart-travel.ru";
    private String passwordMerchant = "123QWE!@#";
    private String captcha = "ability";

    @Test (dataProviderClass = model.DataProviders.class, dataProvider = "listPayments")
    public void itemPayments (String flag,String selector, String parameter){

        //authorized
        driver.get(baseUrl+"login");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //open item and change language
        /*
        hard code
         */
        driver.findElement(By.id("ctl00_ctl10_mhlAllPayments")).click();
        driver.findElement(By.id("ctl00_ctl10_mhlSites")).click();
        //driver.findElement(By.id("ctl00_ctl10_mhlAllPayments")).click();
        //driver.findElement(By.id("ctl00$mhlRU")).click();

        //check
        Assert.assertTrue(TestUtils.check(driver,flag, selector, parameter), "Fail localization parameter!!!!");
    }
}
