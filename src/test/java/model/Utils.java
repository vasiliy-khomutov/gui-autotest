package model;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String getA2SSecurityKey(String...params){
        StringBuilder result = new StringBuilder();
        for(String param : params){
            result.append(param).append("&");
        }
        String source = result.substring(0, result.toString().length() - 1);
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(source.getBytes());
            String md5 = convertToHex(md.digest());
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertToHex(byte[] srcData) {
        StringBuilder sbuffer = new StringBuilder();
        for (int i = 0; i < srcData.length; i++) {
            int flag = (srcData[i] >>> 4) & 0x0F;
            int twoHalf = 0;
            do {
                if ((0 <= flag) && (flag <= 9)) {
                    sbuffer.append((char) ('0' + flag));
                } else {
                    sbuffer.append((char) ('a' + (flag - 10)));
                }
                flag = srcData[i] & 0x0F;
            } while (twoHalf++ < 1);
        }
        return sbuffer.toString();
    }
}
