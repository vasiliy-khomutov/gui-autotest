package model;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Captcha {

    private static String [] path = getPath();
    private static Map allCaptches = new HashMap <String, String>();

    public static String getCaptcha(WebDriver driver){

        String str = driver.findElement(By.xpath(path[0])).getAttribute(path[1]);
        return getCode(str);
    }

    private static Map<String, String> getList(){

        try {
            FileReader reader = new FileReader("C:\\SecureParametersAutotest\\captcha_list.txt");
            BufferedReader in = new BufferedReader(reader);
            String str;
            while ((str = in.readLine()) != null){
                String parts[] = str.split(";");
                allCaptches.put(parts[0], parts[1]);
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return allCaptches;
    }

    private static String [] getPath(){

        try {
            FileReader reader = new FileReader("C:\\SecureParametersAutotest\\captcha_param.txt");
            BufferedReader in = new BufferedReader(reader);
            path = in.readLine().split("\\t");
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return path;
    }

    private static String getCode(String str){
        String code = str.split("=")[1];
        return getList().get(code);
    }
}
