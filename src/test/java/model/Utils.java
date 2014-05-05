package model;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {

    public static boolean checkResultPageTransaction(WebDriver driver, String parameter){
        for (WebElement e: driver.findElements(By.tagName("td"))){
              if(e.getText().contains(parameter)){
                  return true;
              }
        }
        return false;
    }

    public static String getIdTransaction(WebDriver driver){
         return driver.findElement(By.xpath("//tr[6]/td[2]")).getText();
    }

    public static boolean checkTransactionInLK(WebDriver driver, String id){
        for (WebElement e:driver.findElements(By.tagName("td"))){
            if (e.getText().contains(id)){
                return  true;
            }
        }
        return false;
    }

    public static void authorized(WebDriver driver, String login, String password, String captcha) {
        driver.findElement(By.id("ctl00_content_tbLogin")).clear();
        driver.findElement(By.id("ctl00_content_tbLogin")).sendKeys(login);
        driver.findElement(By.id("ctl00_content_tbPassword")).clear();
        driver.findElement(By.id("ctl00_content_tbPassword")).sendKeys(password);
        driver.findElement(By.id("ctl00_content_cptCaptcha_captchaText")).clear();
        driver.findElement(By.id("ctl00_content_cptCaptcha_captchaText")).sendKeys(captcha);
        driver.findElement(By.id("ctl00_content_ibtnLogon")).click();
    }

    /*public static boolean universalCheck(WebDriver driver, String tag, String parameter){
        for(WebElement e: driver.findElements(By.tagName(tag))){
            if(e.getText().contains(parameter)){
                return true;
            }
        }
        return false;
    }*/

    public static boolean universalCheck(WebDriver driver, String flag, String selector, String parameter){

        if(flag.equals("css")){
            return driver.findElement(By.cssSelector(selector)).getText().equals(parameter);
        }

        if(flag.equals("linkText")){
            return driver.findElement(By.linkText(selector)).getText().equals(parameter);
        }

        if(flag.equals("xpath")){
            if(parameter == null){
                return !(driver.findElement(By.xpath(selector)).getText().isEmpty());
            }else{
                return driver.findElement(By.xpath(selector)).getText().contains(parameter);
            }
        }

        return false;
    }
}
