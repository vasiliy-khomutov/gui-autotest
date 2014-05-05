package medium.openItem;


import critical.callbacks.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class MerchantLk {

    private String baseUrl = "https://secure.payonlinesystem.com/";
    private String loginMerchant = "info@smart-travel.ru";
    private String passwordMerchant = "tester123";
    private String captcha = "ability";


    @Test
    public void itemSites(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check item
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "55469"), "Fail open item Sites!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Настройки"), "Fail open item Sites!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Активный"), "Fail open item Sites!");

    }

    @Test
    public void itemPayment(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlAllPayments")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Новые"), "Fail open item Payment!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Оплачен"), "Fail open item Payment!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "ID заказа"), "Fail open item Payments!");

    }

    @Test
    public void itemTransactions(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlTransactions")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Преавторизации"), "Fail open item Transactions!");
        Assert.assertTrue(TestUtils.checkItem(driver, "label", "Full 3-D Secure"), "Fail open item Transactions!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Держатель"), "Fail open item Transactions!");

    }

    /*TODO
    wtf??
    @Test
    public void itemChargebacks(){

        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);


    }*/

    @Test
    public void itemQiwi(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlQiwi")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета в QIWI Кошельке"), "Fail open item Qiwi!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Создан"), "Fail open item Qiwi!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Возвраты"), "Fail open item Qiwi!");

    }

    @Test
    public void itemWebMoney(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlWebMoney")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета WebMoney"), "Fail open item WebMoney!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Создан"), "Fail open item WebMoney!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "WM кошелек"), "Fail open item WebMoney!");
    }

    @Test
    public void itemPayMaster(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlPayMaster")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета PayMaster"), "Fail open item PayMaster!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Оплачен"), "Fail open item PayMaster!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Валюта"), "Fail open item PayMaster!");
    }

    @Test
    public void itemPlatron(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlPlatron")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета Platron"), "Fail open item Platron!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "ID заказа"), "Fail open item Platron!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Сумма"), "Fail open item Platron!");
    }

    @Test
    public void itemYandex(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlYandexMoney")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета Яндекс.Деньги"), "Fail open item Yandex!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Отмененные"), "Fail open item Yandex!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Дата/время"), "Fail open item Yandex!");
    }

    @Test
    public void itemTerminals(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlCashTerminals")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Платежи по терминалам"), "Fail open item Terminals!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Терминал"), "Fail open item Terminals!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Номер консультанта:"), "Fail open item Terminals!");
    }

    @Test
    public void itemCardToCard(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlcard2card")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Переводы с карты на карту"), "Fail open item CardToCard!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Карта отправителя"), "Fail open item CardToCard!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Шлюз"), "Fail open item CardToCard!");
    }

    @Test
    public void itemSms(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlsmsmobile")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Оплата с мобильных телефонов"), "Fail open item Sms!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Сайт"), "Fail open item Sms!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Сумма"), "Fail open item Sms!");
    }

    @Test
    public void itemRevenue(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.linkText("Оборот")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Оборот"), "Fail open item Revenue!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Годовой отчет"), "Fail open item Revenue!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Дата по"), "Fail open item Revenue!");
    }

    @Test
    public void itemUsers(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.linkText("Пользователи")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Пользователи"), "Fail open item Users!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Дата регистрации"), "Fail open item Users!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Роль"), "Fail open item Users!");
    }

    @Test
    public void itemRoles(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.linkText("Роли")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Роли"), "Fail open item Roles!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Оператор"), "Fail open item Roles!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Администратор"), "Fail open item Roles!");
    }

    @Test
    public void itemTools(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlTools")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Инструменты"), "Fail open item Tools!");
        Assert.assertTrue(TestUtils.checkItem(driver, "div", "Имя клиента"), "Fail open item Tools!");
        Assert.assertTrue(TestUtils.checkItem(driver, "div", "Внимание!"), "Fail open item Tools!");
    }

    /*TODO
    @Test
    public void itemNews(){

        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.linkText("Оборот")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Оборот"), "Fail open item Revenue!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Годовой отчет"), "Fail open item Revenue!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Дата по"), "Fail open item Revenue!");
    }*/

    @Test
    public void itemSetting(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlAccount")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Настройки"), "Fail open item Setting!");
        Assert.assertTrue(TestUtils.checkItem(driver, "h3", "Сертификат"), "Fail open item Setting!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Инструкция по работе с сертификатами"), "Fail open item Setting!");
    }

    @Test
    public void itemDocumentation(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlDocs")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Документация"), "Fail open item Documentation!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Технические спецификации"), "Fail open item Documentation!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Персональные документы"), "Fail open item Documentation!");
    }

    @Test
    public void itemSupport(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginMerchant, passwordMerchant, captcha);

        //check
        driver.findElement(By.id("ctl00_ctl11_mhlSupport")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Отправить запрос в службу поддержки"), "Fail open item Support!");
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Контакты"), "Fail open item Support!");
        Assert.assertTrue(TestUtils.checkItem(driver, "div", "Сообщение"), "Fail open item Support!");
    }

    /*@AfterClass
    public void finish(){
        driver.quit();
    }*/
}
