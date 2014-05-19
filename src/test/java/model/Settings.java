package model;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Settings {

    private String baseUrl;
    private String loginMerchant;
    private String passwordMerchant;
    private String loginAdmin;
    private String passwordAdmin;
    private String captcha = "ability";
    //private int [] list = {57482, 57483, 59514, 59523, 59528, 59529, 59530, 59531};
    private int [] list = {57482, 57483};

    @BeforeClass()
    public void setTestParameters(){

        String [] parameters = Environment.readFile();
        baseUrl = parameters[0];
        loginAdmin = parameters[1];
        passwordAdmin = parameters[2];
        loginMerchant = parameters[3];
        passwordMerchant = parameters[4];
    }

    @Test
    public void ChangeTestGWToAzGW(){

        WebDriver driver = DriverFactory.getInstance().getDriver();

        //authorization
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant,captcha);

        Utils.changeGW(driver, list);
    }
}
