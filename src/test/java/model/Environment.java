package model;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;

import java.util.concurrent.TimeUnit;

public class Environment {

    public static WebDriver createDriver(){
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //other driver and setting
        return driver;
    }

    public static String [] readFile() {
        String [] parameters = new String[5];
            try {
                FileReader reader = new FileReader("C:\\secureParametersAutotest\\parameters.txt");
                BufferedReader in = new BufferedReader(reader);
                String string;
                    while ((string = in.readLine()) != null){
                        parameters=string.split("=");
                    }
                in.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        return parameters;
    }
}
