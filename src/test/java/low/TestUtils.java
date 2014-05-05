package low;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestUtils {

    public static boolean check(WebDriver driver,String flag, String selector, String parameter){

        if(flag.equals("css")){
            return driver.findElement(By.cssSelector(selector)).getText().contains(parameter);
        }

        if(flag.equals("linkText")){
            return driver.findElement(By.linkText(selector)).getText().contains(parameter);
        }

        if(flag.equals("xpath")){
            return driver.findElement(By.xpath(selector)).getText().contains(parameter);
        }
        return false;
    }
}
