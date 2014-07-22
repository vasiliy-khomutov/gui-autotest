package medium.filters;

import model.Captcha;
import model.Utils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestUtilsFilters {


    public static String [] getUserAgentData(WebDriver driver){
        String [] result = {"","","","",""};
        result[0] = driver.findElement(By.xpath(".//*[@id='view-top']/table[2]/tbody/tr[11]/td")).getText();
        String raw = driver.findElement(By.xpath(".//*[@id='view-top']/table[2]/tbody/tr[13]/td")).getText();
        result[1] = raw.split(" ")[0];
        result[2] = raw.split(" ")[2];
        String localTimeRaw = driver.findElement(By.xpath(".//*[@id='view-top']/table[2]/tbody/tr[14]/td")).getText();
        String localTime = localTimeRaw.split(" ")[2];
        if(localTime.charAt(1) == '0'){
            StringBuilder sb = new StringBuilder(localTime);
            sb.deleteCharAt(1);
            result[3] = sb.toString();
        }else {
            result[3] = localTime;
        }
        result[4] = driver.findElement(By.xpath(".//*[@id='view-top']/table[2]/tbody/tr[12]/td")).getText();
        return result;
    }

    // TODO. BIG TODO
    public static void enableFilter(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String MID, String filterName){

        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
        driver.findElement(By.id("ctl00_content_filter_merchantId")).clear();
        driver.findElement(By.id("ctl00_content_filter_merchantId")).sendKeys(MID);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(MID)).click();
        driver.findElement(By.xpath("//*[@id='tabs']/div[3]/div/a")).click();

        if(filterName.equals("Match.Email.CardHolderName")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[23]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[23]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl22_lnkTurnOn")).click();
            }
            WebElement day = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day"));
            day.clear();
            day.sendKeys("2");
            WebElement hour = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour"));
            hour.clear();
            hour.sendKeys("2");
            WebElement minute = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute"));
            minute.clear();
            minute.sendKeys("2");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_IncludeDeclinedTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Match.Email.Zip")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[24]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[24]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl23_lnkTurnOn")).click();
            }
            WebElement day = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day"));
            day.clear();
            day.sendKeys("2");
            WebElement hour = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour"));
            hour.clear();
            hour.sendKeys("2");
            WebElement minute = driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute"));
            minute.clear();
            minute.sendKeys("2");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_IncludeDeclinedTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Match.Card.CardHolderName")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[25]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[25]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl24_lnkTurnOn")).click();
            }
            //driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_IncludeDeclinedTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }

        // stop lists
        if(filterName.equals("StopList.BillingCountry")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[7]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[7]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl06_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.Card")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[8]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[8]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl07_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.Email")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[9]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[9]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl08_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.IpAddress")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[10]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[10]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl09_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.IpCountry")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[11]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[11]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl10_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.IssuerBinCountry")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[12]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[12]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl11_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_stopListIssuerBinCountryFilterSettings_unknownCountryAcceptanceSettings"))).selectByVisibleText("Принять");
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_stopListIssuerBinCountryFilterSettings_behavior"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_stopListIssuerBinCountryFilterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.IssuerBin")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[13]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[13]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl12_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.EmailDomain")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[14]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[14]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl13_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.UserAgent")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[15]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[15]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl14_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.Merchant.IssuerBinCountry")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[16]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[16]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl15_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("StopList.Merchant.IpCountry")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[17]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[17]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl16_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }

        // limits
        if(filterName.equals("Limit.Transaction.Amount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[26]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[26]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl25_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxAmount")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxAmount")).sendKeys("333");
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxAmountCurrencyType"))).selectByVisibleText("RUB");
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Ip.EmailCount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[27]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[27]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl26_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxDifferentEmailsCount")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxDifferentEmailsCount")).sendKeys("1");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).sendKeys("2");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_IncludeAllTransactionResults")).click();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_UseOnlyMerchantTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Ip.CardCount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[28]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[28]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl27_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxCardNumberCount")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxCardNumberCount")).sendKeys("1");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).sendKeys("2");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_IncludeAllTransactionResults")).click();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_UseOnlyMerchantTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Ip.DeclinedAttemptsCount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[29]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[29]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl28_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTryCount")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTryCount")).sendKeys("1");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).sendKeys("2");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_UseOnlyMerchantTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Card.PurchaseAmount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[30]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[30]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl29_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTransactionsAmount")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTransactionsAmount")).sendKeys("333");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).sendKeys("2");
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTransactionsAmountCurrencyType"))).selectByVisibleText("RUB");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_UseOnlyMerchantTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Card.PurchaseCount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[31]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[31]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl30_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTransactionsCount")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTransactionsCount")).sendKeys("1");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).sendKeys("2");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_UseOnlyMerchantTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Card.DeclinedAttemptsCount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[32]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[32]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl31_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTryCount")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_MaxTryCount")).sendKeys("1");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_day")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_hour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_minute")).sendKeys("2");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_UseOnlyMerchantTransactions")).click();
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Merchant.PurchaseCount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[33]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[33]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl32_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_limitCountValue")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_limitCountValue")).sendKeys("1");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_TimeSpan_timeDay")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_TimeSpan_timeDay")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_TimeSpan_timeHour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_TimeSpan_timeHour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_TimeSpan_timeMinute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_TimeSpan_timeMinute")).sendKeys("2");
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_behavior"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseCountFilterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Merchant.PurchaseAmount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[34]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[34]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl33_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_limitAmountValue")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_limitAmountValue")).sendKeys("333");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_TimeSpan_timeDay")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_TimeSpan_timeDay")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_TimeSpan_timeHour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_TimeSpan_timeHour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_TimeSpan_timeMinute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_TimeSpan_timeMinute")).sendKeys("2");
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_behavior"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantPurchaseAmountFilterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.Merchant.RefundAmount")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[34]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[34]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl34_lnkTurnOn")).click();
            }
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_amountValue")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_amountValue")).sendKeys("333");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_TimeSpan_timeDay")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_TimeSpan_timeDay")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_TimeSpan_timeHour")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_TimeSpan_timeHour")).sendKeys("0");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_TimeSpan_timeMinute")).clear();
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_TimeSpan_timeMinute")).sendKeys("2");
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_action"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_limitMerchantRefundAmountFilterSettings_cmdSave")).click();
        }
        if(filterName.equals("Limit.AccountId.PaymentParameters")){
            if(driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[36]")).getText().contains("Настройка")){
                driver.findElement(By.xpath("//*[@id='filtersView']/table/tbody/tr[36]/td[2]/a[1]")).click();
            }else {
                driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl35_lnkTurnOn")).click();
            }
            new Select(driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_BehaviorSettings"))).selectByVisibleText("Блокировать транзакцию");
            driver.findElement(By.id("ctl00_content_filtersContainer_filterSettings_cmdSave")).click();
        }

    }

    public static void disableFilter(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String MID, String filterName){
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
        WebElement idField = driver.findElement(By.id("ctl00_content_filter_merchantId"));
        idField.clear();
        idField.sendKeys(MID);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(MID)).click();
        driver.findElement(By.xpath("//*[@id='tabs']/div[3]/div/a")).click();

        String filterTr = "";

        if(filterName.equals("StopList.BillingCountry")){
            filterTr = "06";
        }
        if(filterName.equals("StopList.Card")){
            filterTr = "07";
        }
        if(filterName.equals("StopList.Email")){
            filterTr = "08";
        }
        if(filterName.equals("StopList.IpAddress")){
            filterTr = "09";
        }
        if(filterName.equals("StopList.IpCountry")){
            filterTr = "10";
        }
        if(filterName.equals("StopList.IssuerBinCountry")){
            filterTr = "11";
        }
        if(filterName.equals("StopList.IssuerBin")){
            filterTr = "12";
        }
        if(filterName.equals("StopList.EmailDomain")){
            filterTr = "13";
        }
        if(filterName.equals("StopList.UserAgent")){
            filterTr = "14";
        }
        if(filterName.equals("StopList.Merchant.IssuerBinCountry")){
            filterTr = "15";
        }
        if(filterName.equals("StopList.Merchant.IpCountry")){
            filterTr = "16";
        }

        if(filterName.equals("Match.Email.CardHolderName")){
            filterTr = "22";
        }
        if(filterName.equals("Match.Email.Zip")){
            filterTr = "23";
        }
        if(filterName.equals("Match.Card.CardHolderName")){
            filterTr = "24";
        }

        //limits
        if(filterName.equals("Limit.Transaction.Amount")){
            filterTr = "25";
        }
        if(filterName.equals("Limit.Ip.EmailCount")){
            filterTr = "26";
        }
        if(filterName.equals("Limit.Ip.CardCount")){
            filterTr = "27";
        }
        if(filterName.equals("Limit.Ip.DeclinedAttemptsCount")){
            filterTr = "28";
        }
        if(filterName.equals("Limit.Card.PurchaseAmount")){
            filterTr = "29";
        }
        if(filterName.equals("Limit.Card.PurchaseCount")){
            filterTr = "30";
        }
        if(filterName.equals("Limit.Card.DeclinedAttemptsCount")){
            filterTr = "31";
        }
        if(filterName.equals("Limit.Merchant.PurchaseCount")){
            filterTr = "32";
        }
        if(filterName.equals("Limit.Merchant.PurchaseAmount")){
            filterTr = "33";
        }
        if(filterName.equals("Limit.Merchant.RefundAmount")){
            filterTr = "33";
        }
        if(filterName.equals("Limit.AccountId.PaymentParameters")){
            filterTr = "35";
        }


        driver.findElement(By.id("ctl00_content_filtersContainer_filterList_viewList_ctl" + filterTr + "_lnkTurnOff")).click();
    }

    public static void addToStopList(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String filter, String parameter){
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlBlackList")).click();

        if(filter.equals("StopList.BillingCountry")){
            driver.findElement(By.xpath(".//*[@id='params']/table/tbody/tr[2]/td[1]/a")).click();
            // todo - add get value or country method - TEMP
            new Select(driver.findElement(By.id("ctl00_content_ctl00_dlCountry"))).selectByValue(parameter);
            new Select(driver.findElement(By.id("ctl00_content_ctl00_stopListData_reasonType"))).selectByValue("5");
            driver.findElement(By.id("ctl00_content_ctl00_cmdSave")).click();
        }
        if(filter.equals("StopList.Card")){
            driver.findElement(By.id("ctl00_content_numbers")).click();
            driver.findElement(By.xpath(".//*[@id='linkAddCard']")).click();
            driver.findElement(By.id("ctl00_content_ctl00_addNumber")).sendKeys(parameter);
            new Select(driver.findElement(By.id("ctl00_content_ctl00_stopListData_reasonType"))).selectByValue("5");
            driver.findElement(By.id("ctl00_content_ctl00_cmdSave")).click();
        }
        if(filter.equals("StopList.Email")){
            driver.findElement(By.id("ctl00_content_emails")).click();
            driver.findElement(By.xpath(".//*[@id='params']/table[2]/tbody/tr/td[1]/a")).click();
            driver.findElement(By.id("ctl00_content_ctl00_addEmail")).sendKeys(parameter);
            new Select(driver.findElement(By.id("ctl00_content_ctl00_stopListData_reasonType"))).selectByValue("5");
            driver.findElement(By.id("ctl00_content_ctl00_cmdSave")).click();
        }
        if(filter.equals("StopList.IpAddress")){
            driver.findElement(By.id("ctl00_content_ipaddresses")).click();
            driver.findElement(By.xpath(".//*[@id='params']/table[2]/tbody/tr/td[1]/a")).click();
            driver.findElement(By.id("ctl00_content_ctl00_ipFrom")).sendKeys(parameter);
            new Select(driver.findElement(By.id("ctl00_content_ctl00_stopListData_reasonType"))).selectByValue("5");
            driver.findElement(By.id("ctl00_content_ctl00_cmdSave")).click();
        }
        if(filter.equals("StopList.IpCountry")){
            // todo 1
            /* отрефакторить шаги
            привязка/удаление ip к стране
            добавление/удаление страны в стоп-лист
            текущая ситуация: привязка ip к стране которая уже в стоп-листе */
        }
        if(filter.equals("StopList.IssuerBinCountry")){
            // см. туду 1.
        }
        if(filter.equals("StopList.IssuerBin")){
            driver.findElement(By.id("ctl00_content_issuerbins")).click();
            driver.findElement(By.xpath(".//*[@id='params']/table[2]/tbody/tr/td[1]/a")).click();
            driver.findElement(By.id("ctl00_content_ctl00_addIssuerBIN")).sendKeys(parameter);
            new Select(driver.findElement(By.id("ctl00_content_ctl00_stopListData_reasonType"))).selectByValue("5");
            driver.findElement(By.id("ctl00_content_ctl00_cmdSave")).click();
        }
        if(filter.equals("StopList.EmailDomain")){
            driver.findElement(By.id("ctl00_content_emaildomains")).click();
            driver.findElement(By.xpath(".//*[@id='params']/table[2]/tbody/tr/td[1]/a")).click();
            driver.findElement(By.id("ctl00_content_ctl00_addEmailDomain")).sendKeys(parameter);
            new Select(driver.findElement(By.id("ctl00_content_ctl00_stopListData_reasonType"))).selectByValue("5");
            driver.findElement(By.id("ctl00_content_ctl00_cmdSave")).click();
        }
    }

    public static void addToStopListUserAgent(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String MID,
                                              String userAgent, String displayInfo, String bbpx, String localTime, String browserLang){

        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlBlackList")).click();
        driver.findElement(By.id("ctl00_content_useragents")).click();
        driver.findElement(By.xpath(".//*[@id='params']/table/tbody/tr[4]/td[1]/a")).click();

        driver.findElement(By.id("ctl00_content_ctl00_txtUserAgent")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_txtUserAgent")).sendKeys(userAgent);
        driver.findElement(By.id("ctl00_content_ctl00_txtDisplayInfoResolution")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_txtDisplayInfoResolution")).sendKeys(displayInfo);
        driver.findElement(By.id("ctl00_content_ctl00_txtDisplayInfoBppx")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_txtDisplayInfoBppx")).sendKeys(bbpx);
        new Select(driver.findElement(By.id("ctl00_content_ctl00_ddlLocalTime"))).selectByValue(localTime);
        driver.findElement(By.id("ctl00_content_ctl00_txtBrowserLanguage")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_txtBrowserLanguage")).sendKeys(browserLang);
        driver.findElement(By.id("ctl00_content_ctl00_txtMerchants")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_txtMerchants")).sendKeys(MID);
        new Select(driver.findElement(By.id("ctl00_content_ctl00_stopListData_reasonType"))).selectByValue("5");
        driver.findElement(By.id("ctl00_content_ctl00_cmdSave")).click();
    }

    public static void removeFromStopListUserAgent(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String MID,
                                              String userAgent, String displayInfo, String localTime, String browserLang){
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlBlackList")).click();
        driver.findElement(By.id("ctl00_content_useragents")).click();
        driver.findElement(By.id("ctl00_content_ctl00_userAgentFilter")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_userAgentFilter")).sendKeys(userAgent);
        //driver.findElement(By.id("ctl00_content_ctl00_displayInfoFilter")).clear();
        //driver.findElement(By.id("ctl00_content_ctl00_displayInfoFilter")).sendKeys(displayInfo);
        driver.findElement(By.id("ctl00_content_ctl00_localTimeFilter")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_localTimeFilter")).sendKeys(localTime);
        driver.findElement(By.id("ctl00_content_ctl00_browserLanguageFilter")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_browserLanguageFilter")).sendKeys(browserLang);
        driver.findElement(By.id("ctl00_content_ctl00_merchantIdFilter")).clear();
        driver.findElement(By.id("ctl00_content_ctl00_merchantIdFilter")).sendKeys(MID);
        driver.findElement(By.id("ctl00_content_ctl00_cmdSelect")).click();
        driver.findElement(By.id("ctl00_content_ctl00_list_ctl00_lnkDelete")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }


    public static void addIpAddressLookup(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String filter, String parameter){

        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlLookups")).click();
        driver.findElement(By.xpath(".//*[@id='mainContent']/table/tbody/tr[4]/td/span/a")).click();
        driver.findElement(By.xpath(".//*[@id='mainContent']/table/tbody/tr/td[2]/a")).click();
        String parts[] = parameter.split("\\.");
        driver.findElement(By.id("ctl00$content$newItem$txtIp_octet_1")).clear();
        driver.findElement(By.id("ctl00$content$newItem$txtIp_octet_1")).sendKeys(parts[0]);
        driver.findElement(By.id("ctl00$content$newItem$txtIp_octet_2")).clear();
        driver.findElement(By.id("ctl00$content$newItem$txtIp_octet_2")).sendKeys(parts[1]);
        driver.findElement(By.id("ctl00$content$newItem$txtIp_octet_3")).clear();
        driver.findElement(By.id("ctl00$content$newItem$txtIp_octet_3")).sendKeys(parts[2]);
        driver.findElement(By.id("ctl00$content$newItem$txtIp_octet_4")).clear();
        driver.findElement(By.id("ctl00$content$newItem$txtIp_octet_4")).sendKeys(parts[3]);
        // TODO
        new Select(driver.findElement(By.id("ctl00_content_newItem_ddlCountry"))).selectByValue("CO");
        driver.findElement(By.id("ctl00_content_newItem_cmdSave")).click();
    }

    public static void removeIpAddressLookup(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String filter, String parameter){
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlLookups")).click();
        driver.findElement(By.xpath(".//*[@id='mainContent']/table/tbody/tr[4]/td/span/a")).click();
        String parts[] = parameter.split("\\.");
        driver.findElement(By.id("ctl00$content$filter$txtIp_octet_1")).clear();
        driver.findElement(By.id("ctl00$content$filter$txtIp_octet_1")).sendKeys(parts[0]);
        driver.findElement(By.id("ctl00$content$filter$txtIp_octet_2")).clear();
        driver.findElement(By.id("ctl00$content$filter$txtIp_octet_2")).sendKeys(parts[1]);
        driver.findElement(By.id("ctl00$content$filter$txtIp_octet_3")).clear();
        driver.findElement(By.id("ctl00$content$filter$txtIp_octet_3")).sendKeys(parts[2]);
        driver.findElement(By.id("ctl00$content$filter$txtIp_octet_4")).clear();
        driver.findElement(By.id("ctl00$content$filter$txtIp_octet_4")).sendKeys(parts[3]);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(parameter)).click();
        driver.findElement(By.id("ctl00_content_editViewItem_Button1")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        driver.findElement(By.id("cmdClear")).click();
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
    }
    public static void addBINLookup(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String filter, String parameter){
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlLookups")).click();
        driver.findElement(By.xpath(".//*[@id='mainContent']/table/tbody/tr[3]/td/span/a")).click();
        driver.findElement(By.xpath(".//*[@id='tabs']/div[4]/a")).click();
        driver.findElement(By.xpath(".//*[@id='ctl00_content_newBin_txtBin']")).clear();
        driver.findElement(By.xpath(".//*[@id='ctl00_content_newBin_txtBin']")).sendKeys(parameter);
        new Select(driver.findElement(By.id("ctl00_content_newBin_ddlBankCountry"))).selectByValue("UY");
        driver.findElement(By.id("ctl00_content_newBin_cmdSave")).click();
    }

    public static void removeBINLookup(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String filter, String parameter){
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlLookups")).click();
        driver.findElement(By.xpath(".//*[@id='mainContent']/table/tbody/tr[3]/td/span/a")).click();
        driver.findElement(By.id("ctl00_content_filter_txtBin")).clear();
        driver.findElement(By.id("ctl00_content_filter_txtBin")).sendKeys(parameter);
        driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
        driver.findElement(By.linkText(parameter)).click();
        driver.findElement(By.id("ctl00_content_editViewBin_Button1")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public static void removeFromStopList(WebDriver driver, String baseUrl, String loginAdmin, String passwordAdmin, String filter, String parameter){
        Utils.login(driver, baseUrl, loginAdmin, passwordAdmin);
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlBlackList")).click();

        if(filter.equals("StopList.BillingCountry")){
            String countryCode = "";
            // TODO - move to another method (getCountryCode(country))
            // ...
            if(parameter.equals("Россия")){
                countryCode = "RU";
            }
            if(parameter.equals("Украина")){
                countryCode = "UA";
            }
            if(parameter.equals("Колумбия")){
                countryCode = "CO";
            }
            driver.findElement(By.id("ctl00_content_ctl00_countryCode")).clear();
            driver.findElement(By.id("ctl00_content_ctl00_countryCode")).sendKeys(countryCode);
            driver.findElement(By.id("ctl00_content_ctl00_cmdSelect")).click();
            driver.findElement(By.id("ctl00_content_ctl00_list_ctl00_lnkDelete")).click();

            Alert alert = driver.switchTo().alert();//Creating object for Alert class
            alert.accept();//Clicking OK button
        }
        if(filter.equals("StopList.Card")){
            driver.findElement(By.id("ctl00_content_numbers")).click();
            driver.findElement(By.id("ctl00_content_ctl00_numberQuery")).clear();
            driver.findElement(By.id("ctl00_content_ctl00_numberQuery")).sendKeys(parameter);
            driver.findElement(By.id("ctl00_content_ctl00_cmdSelect")).click();
            driver.findElement(By.id("ctl00_content_ctl00_list_ctl00_lnkDelete")).click();
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
        if(filter.equals("StopList.Email")){
            driver.findElement(By.id("ctl00_content_emails")).click();
            driver.findElement(By.id("ctl00_content_ctl00_filter_email")).clear();
            driver.findElement(By.id("ctl00_content_ctl00_filter_email")).sendKeys(parameter);
            driver.findElement(By.id("ctl00_content_ctl00_filter_cmdSelect")).click();
            driver.findElement(By.id("ctl00_content_ctl00_list_ctl00_lnkDelete")).click();
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
        if(filter.equals("StopList.IpAddress")){
            driver.findElement(By.id("ctl00_content_ipaddresses")).click();
            driver.findElement(By.id("ctl00_content_ctl00_filter_ipAddress")).clear();
            driver.findElement(By.id("ctl00_content_ctl00_filter_ipAddress")).sendKeys(parameter);
            driver.findElement(By.id("ctl00_content_ctl00_filter_cmdSelect")).click();
            driver.findElement(By.id("ctl00_content_ctl00_list_ctl00_lnkDelete")).click();
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
        if(filter.equals("StopList.IpCountry")){
            // todo 2
        }
        if(filter.equals("StopList.IssuerBinCountry")){
            // todo
        }
        if(filter.equals("StopList.IssuerBin")){
            driver.findElement(By.id("ctl00_content_issuerbins")).click();
            driver.findElement(By.id("ctl00_content_ctl00_filter_issuerBin")).clear();
            driver.findElement(By.id("ctl00_content_ctl00_filter_issuerBin")).sendKeys(parameter);
            driver.findElement(By.id("ctl00_content_ctl00_filter_cmdSelect")).click();
            driver.findElement(By.id("ctl00_content_ctl00_list_ctl00_lnkDelete")).click();
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
        if(filter.equals("StopList.EmailDomain")){
            driver.findElement(By.id("ctl00_content_emaildomains")).click();
            driver.findElement(By.id("ctl00_content_ctl00_filter_emailDomain")).clear();
            driver.findElement(By.id("ctl00_content_ctl00_filter_emailDomain")).sendKeys(parameter);
            driver.findElement(By.id("ctl00_content_ctl00_filter_cmdSelect")).click();
            driver.findElement(By.id("ctl00_content_ctl00_list_ctl00_lnkDelete")).click();
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    public static String getNewIdTransaction_Filter(WebDriver driver, String merchant, String optionMerchant, String orderId, String amount,
                                             String numberCardA, String numberCardB, String numberCardC, String numberCardD, String expDateMonth,
                                             String expDateYear, String cvc, String bank, String address, String city, String zip, String country,
                                             String phone, String email, String currency, String cardHolderName, String baseUrl, String loginAdmin,
                                             String passwordAdmin){

        generateTransaction(driver, merchant, optionMerchant, currency, amount, orderId, email);

        //type value in  payment form
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberA")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberA")).sendKeys(numberCardA);
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberB")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberB")).sendKeys(numberCardB);
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberC")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberC")).sendKeys(numberCardC);
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberD")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardNumberD")).sendKeys(numberCardD);
        new Select(driver.findElement(By.id("ctl00_content_cardForm_expDateMonth"))).selectByVisibleText(expDateMonth);
        new Select(driver.findElement(By.id("ctl00_content_cardForm_expDateYear"))).selectByVisibleText(expDateYear);
        driver.findElement(By.id("ctl00_content_cardForm_cardHolderName")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardHolderName")).sendKeys(cardHolderName);
        driver.findElement(By.id("ctl00_content_cardForm_cardCVC")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_cardCVC")).sendKeys(cvc);
        driver.findElement(By.id("ctl00_content_cardForm_bankName")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_bankName")).sendKeys(bank);
        driver.findElement(By.id("ctl00_content_cardForm_address")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_address")).sendKeys(address);
        driver.findElement(By.id("ctl00_content_cardForm_city")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_city")).sendKeys(city);
        driver.findElement(By.id("ctl00_content_cardForm_zipCode")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_zipCode")).sendKeys(zip);
        new Select(driver.findElement(By.id("ctl00_content_cardForm_country"))).selectByVisibleText(country);
        driver.findElement(By.id("ctl00_content_cardForm_phone")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_phone")).sendKeys(phone);
        driver.findElement(By.id("ctl00_content_cardForm_email")).clear();
        driver.findElement(By.id("ctl00_content_cardForm_email")).sendKeys(email);
        driver.findElement(By.id("ctl00_content_cardForm_cmdProcess")).click();

        String result = getTransactionId(driver, orderId);
        // TODO - "failed" parameter
        if(result.equals("failed")){

            driver.get(baseUrl + "login/");
            Utils.authorized(driver, loginAdmin, passwordAdmin, Captcha.getCaptcha(driver));
            driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
            driver.findElement(By.id("ctl00_content_all")).click();
            driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
            driver.findElement(By.id("ctl00_content_filter_orderNumber")).sendKeys(orderId);
            driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
            String result1 = driver.findElement(By.xpath("//table[@id='list_transactions']/tbody/tr[2]/td[2]")).getText();
            driver.findElement(By.id("ctl00_content_filter_orderNumber")).clear();
            driver.findElement(By.id("ctl00_content_filter_cmdSelect")).click();
            return result1;
        }
        return result;
    }

    public static void checkTransactionCard_Admin_Filter(WebDriver driver, String idTransaction, String parameter1, String parameter2, String code, String filterName){

        //driver.findElement(By.linkText(idTransaction)).click();
        waitForLogs(driver, idTransaction);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(filterName.equals("Match.Email.CardHolderName")){
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='tran-email']/a", parameter1), "Incorrect email field on transaction card (Admin Login).");
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[1]/td", parameter2), "Incorrect cardholdername field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Match.Email.CardHolderName')]")).getText().contains(code), "Incorrect Match.Email.CardHolderName Result Code.");
        }
        if(filterName.equals("Match.Email.Zip")){
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='tran-email']/a", parameter1), "Incorrect email field on transaction card (Admin Login).");
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[2]/tbody/tr[4]/td", parameter2.substring(0, parameter2.length()-4)), "Incorrect ZIP field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Match.Email.Zip')]")).getText().contains(code), "Incorrect Match.Email.Zip Result Code.");
        }
        if(filterName.equals("Match.Card.CardHolderName")){
            driver.findElement(By.xpath("//*[@id='bi-pan']/a")).click();
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", "//*[@id='pan-view']", parameter1), "Incorrect field on transaction card (Admin Login).");
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", "//*[@id='view-top']/table[2]/tbody/tr[1]/td", parameter2), "Incorrect Cardholder field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Match.Card.CardHolderName')]")).getText().contains(code), "Incorrect Match.Card.CardHolderName Result Code.");
        }
        if(filterName.equals("StopList.BillingCountry")){
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", "//*[@id='view-top']/table[2]/tbody/tr[6]/td", parameter1), "Incorrect parameter on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.BillingCountry')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.Card")){
            driver.findElement(By.xpath("//*[@id='bi-pan']/a")).click();
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", "//*[@id='pan-view']", parameter1), "Incorrect field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.Card')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.Email")){
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='tran-email']/a", parameter1), "Incorrect parameter on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.Email')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.IpAddress")){
            //Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='tran-ip']", parameter1), "Incorrect parameter on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.IpAddress')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.IpCountry")){
            // check country
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.IpCountry')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.IssuerBinCountry")){
            // check country (transaction rd ?)
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.IssuerBinCountry')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.IssuerBin")){
            // check bin
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.IssuerBin')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.EmailDomain")){
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.EmailDomain')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.UserAgent")){
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.UserAgent')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.Merchant.IssuerBinCountry")){
            // check country
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.Merchant.IssuerBinCountry')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("StopList.Merchant.IpCountry")){
            // check country
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'StopList.Merchant.IpCountry')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        //limits
        if(filterName.equals("Limit.Transaction.Amount")){
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", parameter1), "Incorrect field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Transaction.Amount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Ip.EmailCount")){
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='tran-email']/a", parameter1), "Incorrect field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Ip.EmailCount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Ip.CardCount")){
            driver.findElement(By.xpath("//*[@id='bi-pan']/a")).click();
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", "//*[@id='pan-view']", parameter1), "Incorrect field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Ip.CardCount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Ip.DeclinedAttemptsCount")){
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", parameter1), "Incorrect field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Ip.DeclinedAttemptsCount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Card.PurchaseAmount")){
            Assert.assertTrue(Utils.universalCheck(driver, "xpath", ".//*[@id='view-top']/table[1]/tbody/tr[17]/td", parameter1), "Incorrect field on transaction card (Admin Login).");
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Card.PurchaseAmount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Card.PurchaseCount")){
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Card.PurchaseCount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Card.DeclinedAttemptsCount")){
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Card.DeclinedAttemptsCount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Merchant.PurchaseCount")){
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Merchant.PurchaseCount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Merchant.PurchaseAmount")){
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Merchant.PurchaseAmount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.Merchant.RefundAmount")){

            //Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.Merchant.RefundAmount')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }
        if(filterName.equals("Limit.AccountId.PaymentParameters")){
            Assert.assertTrue(driver.findElement(By.xpath("//tr[contains(.,'Limit.AccountId.PaymentParameters')]")).getText().contains(code), "Incorrect Filter Result Code.");
        }

    }

    private static void generateTransaction(WebDriver driver, String merchant, String optionMerchant, String currency, String amount, String orderId, String email){

        //generate transaction and open link
        driver.findElement(By.id("ctl00_ctl11_mhlTools")).click();
        new Select(driver.findElement(By.id("ctl00_content_ddlSiteList"))).selectByVisibleText(merchant);
        driver.findElement(By.cssSelector(optionMerchant)).click();
        new Select(driver.findElement(By.id("ctl00_content_ddlCurrencies"))).selectByVisibleText(currency);
        driver.findElement(By.id("ctl00_content_tbAmount")).clear();
        driver.findElement(By.id("ctl00_content_tbAmount")).sendKeys(amount);
        driver.findElement(By.name("ctl00$content$tbOrderId")).clear();
        driver.findElement(By.name("ctl00$content$tbOrderId")).sendKeys(orderId);
        driver.findElement(By.id("ctl00_content_tbCardHolderEmail")).clear();
        driver.findElement(By.id("ctl00_content_tbCardHolderEmail")).sendKeys(email);
        driver.findElement(By.name("ctl00$content$cmdProcess")).click();
        driver.findElement(By.id("ctl00_content_lnkPayment")).click();
        driver.close();
    }

    private static void waitForLogs(WebDriver driver, String idTransaction){
        boolean showed = false;
        // wait 3 second (total waiting time is 1 minute)
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        for(int i = 0; i < 20; i++){
            // click
            driver.findElement(By.linkText(idTransaction)).click();
            // search
            showed = driver.findElements(By.xpath("//td[contains(.,'Close')]")).size() > 0;
            // if result found - break
            if(showed == true){
                break;
            }
        }
    }

    public static String getTransactionId(WebDriver driver, String orderId){
        // TODO - refactor. return failed trx id
        if (!(driver.findElements(By.xpath("//tr[6]/td[2]")).size() > 0)){
            return "failed";
        }
        return driver.findElement(By.xpath("//tr[6]/td[2]")).getText();
    }

    public static String generateRandomName(int nameLength){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < nameLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static String generateCardNumber(String mps){
        int pos = 2;
        int len = 16;
        int [] str = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        int sum = 0;
        int final_digit = 0;
        int t = 0;
        int len_offset = 0;

        if(mps.equals("Visa")){
            str[0] = 4;
            str[1] = 4;
        }else
        if(mps.equals("MasterCard")){
            str[0] = 5;
            str[1] = 5;
        }else{
            System.out.println("Only Visa or MasterCard is supported!");
        }

        while (pos < len - 1) {
            Random random = new Random();
            str[pos++] = random.nextInt(9 - 0) + 0;
        }

        len_offset = (len + 1) % 2;

        for (pos = 0; pos < len - 1; pos++) {
            if ((pos + len_offset) % 2 == 1) {
                t = str[pos] * 2;
                if (t > 9) {
                    t -= 9;
                }
                sum += t;
            }
            else {
                sum += str[pos];
            }
        }

        final_digit = (10 - (sum % 10)) % 10;
        str[len - 1] = final_digit;
        StringBuilder sb = new StringBuilder(str.length);
        for (int i : str) {
            sb.append(i);
        }
        String s = sb.toString();
        return s.toString();
    }

}
