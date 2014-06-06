package model;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class DriverFactory {


    private static DriverFactory instance = new DriverFactory();

    private DriverFactory(){
        //Do-nothing..Do not allow to initialize this class from outside
    }

    public static DriverFactory getInstance(){
        return instance;
    }

    ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() // thread local driver object for webdriver
    {
        @Override
        protected WebDriver initialValue(){
            WebDriver driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            return driver;
        }
    };

    public WebDriver getDriver(){// call this method to get the driver object and launch the browser
        return driver.get();
    }

    /*public void removeDriver() // Quits the driver and closes the browser
    {
        driver.get().quit();
    }*/

}
