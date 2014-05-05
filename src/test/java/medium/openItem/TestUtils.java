package medium.openItem;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestUtils {

    public static boolean checkItem(WebDriver driver,  String tag, String parameter){
        for (WebElement e: driver.findElements(By.tagName(tag))){
            if( e.getText().contains(parameter)){
                return true;
            }
        }
        return false;
    }
}
