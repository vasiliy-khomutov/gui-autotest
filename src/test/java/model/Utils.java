package model;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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

    public static void changeGW(WebDriver driver, int[] list) {
        for(int i = 0; i < list.length; i++){
            String mid = list[i] + "";
            driver.findElement(By.id("ctl00_content_filter_merchantId")).clear();
            driver.findElement(By.id("ctl00_content_filter_merchantId")).sendKeys(mid);
            driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
            driver.findElement(By.linkText(mid)).click();
            driver.findElement(By.linkText("Шлюз")).click();
            driver.findElement(By.linkText("Изменить")).click();
            new Select(driver.findElement(By.id("ctl00_content_gateway_gatewaysList"))).selectByValue("1059");

            driver.findElement(By.id("ctl00_content_filter_merchantId")).clear();
            driver.findElement(By.id("ctl00_content_filter_merchantId")).sendKeys(mid);

        }
    }

    public static String getUSDAmount(String amount, String currency) {
        // TODO - Currency Exchange service
        // rouble
        if(currency.equals("RUB")){
            Connect connectDb = new Connect();
            float rate = connectDb.getRate("USD");
            float amountRUB = Float.parseFloat(amount);
            float amountUSD = amountRUB/rate;
            return Float.toString(round(amountUSD, 2)).replace(".",",");
        }
        // usd
        if(currency.equals("USD")){
            return amount.toString();
        }
        // euro
        if(currency.equals("EUR")){
            Connect connectDb = new Connect();
            float rateUSD = connectDb.getRate("USD");
            float rateEUR = connectDb.getRate("EUR");
            float amountRUB = Float.parseFloat(amount) * rateEUR;
            float amountUSD = amountRUB / rateUSD;
            return Float.toString(round(amountUSD, 2)).replace(".",",");
        }
        System.out.println("Incorrect Currency!");
        return null;
    }

    private static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    }

}
