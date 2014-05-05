package medium.openItem;


import critical.callbacks.DriverFactory;
import model.Environment;
import model.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class AdminLK {

    private String baseUrl = "https://secure.payonlinesystem.com/";
    private String loginAdmin = "v.khomutov";
    private String passwordAdmin = "tester123";
    private String captcha = "ability";

    @Test
    public void itemClient(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Информация о клиентах"), "Fail open item Client!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Merchant ID"), "Fail open item Client!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Изменение с"), "Fail open item Client!");

    }

    @Test
    public void itemFilters(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlFilters")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Фильтры"), "Fail open item Filters!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Системные фильтры"), "Fail open item Filters!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Шаблоны настроек"), "Fail open item Filters!");

    }

    @Test
    public void itemGateways(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_MutableHyperLink1")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Gateways list"), "Fail open item Gateways!");
        Assert.assertTrue(TestUtils.checkItem(driver, "div", "VTB Gateway"), "Fail open item Gateways!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Свойства"), "Fail open item Gateways!");

    }

    @Test
    public void itemAllPayment(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlPayments")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Платежи"), "Fail open item AllPayment!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Оплачен"), "Fail open item AllPayment!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Инструмент"), "Fail open item AllPayments!");

    }

    @Test
    public void itemTransactions(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactions")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Транзакции"), "Fail open item Transactions!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "ID заказа"), "Fail open item Transactions!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "ID"), "Fail open item Transactions!");

    }

    @Test
    public void itemQiwi(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlPaymentsQiwi")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета в QIWI Кошельке"), "Fail open item Qiwi!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Создан"), "Fail open item Qiwi!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Возвраты"), "Fail open item Qiwi!");

    }

    @Test
    public void itemWebMoney(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlPaymentsWm")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета WebMoney"), "Fail open item WebMoney!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Создан"), "Fail open item WebMoney!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "WM кошелек"), "Fail open item WebMoney!");
    }

    @Test
    public void itemPayMaster(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlPaymentPayMaster")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета PayMaster"), "Fail open item PayMaster!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Оплачен"), "Fail open item PayMaster!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "ID заказа"), "Fail open item PayMaster!");
    }

    @Test
    public void itemPlatron(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlPaymentsPlatron")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета Platron"), "Fail open item Platron!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "ID заказа"), "Fail open item Platron!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Оплачен"), "Fail open item Platron!");
    }

    @Test
    public void itemYandex(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlPaymentsYm")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Счета Яндекс.Деньги"), "Fail open item Yandex!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Отмененные"), "Fail open item Yandex!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "YM счет"), "Fail open item Yandex!");
    }

    @Test
    public void itemCardToCard(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlCard2Card")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Переводы с карты на карту"), "Fail open item CardToCard!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Карта отправителя"), "Fail open item CardToCard!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Карта получателя"), "Fail open item CardToCard!");
    }


    @Test
    public void itemCreditRequest(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlCreditRequests")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Кредитные заявки"), "Fail open item CardToCard!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Название товара"), "Fail open item CardToCard!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Согласованные"), "Fail open item CardToCard!");
    }

    @Test
    public void itemTransactionRD(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactionsFlat")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Merchant ID"), "Fail open item TransactionRD!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Транзакции RD"), "Fail open item TransactionRD!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Flags"), "Fail open item TransactionRD!");
    }

    @Test
     public void itemTransactionRDOld(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlTransactionsFlatOld")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Merchant ID"), "Fail open item TransactionRDOld!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Транзакции RD"), "Fail open item TransactionRDOld!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Flags"), "Fail open item TransactionRDOld!");
    }

    @Test
    public void itemPaymentsPos(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlPresentments")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Выплаты по данным POS"), "Fail open item PaymentsPos!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "По операциям"), "Fail open item PaymentsPos!");
        Assert.assertTrue(TestUtils.checkItem(driver, "div", "По дням"), "Fail open item PaymentsPos!");
    }

    @Test
    public void itemPaymentsMerchant(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlPresentmentsMerchant")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Выплаты по данным мерчанта"), "Fail open item PaymentsMerchant!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "По операциям"), "Fail open item PaymentsMerchant!");
        Assert.assertTrue(TestUtils.checkItem(driver, "div", "По дням"), "Fail open item PaymentsMerchant!");
    }

    @Test
    public void itemChargeback(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlChargebacks")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Опротестованные транзакции"), "Fail open item Chargeback!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Индикатор"), "Fail open item Chargeback!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "ПС"), "Fail open item Chargeback!");
    }

    @Test
    public void itemFraudReport(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlFraudReports")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Fraud Report"), "Fail open item FraudReport!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Тип"), "Fail open item FraudReport!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "ПС"), "Fail open item FraudReport!");
    }

    @Test
    public void itemRetrievalRequest(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlRetrievalRequest")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Retrieval Request"), "Fail open item RetrievalRequest!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Документы предоставлены"), "Fail open item RetrievalRequest!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Шлюз"), "Fail open item RetrievalRequest!");
    }

    @Test
    public void itemUsers(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlUsers")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Пользователи"), "Fail open item Users!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", loginAdmin), "Fail open item Users!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Последний вход"), "Fail open item Users!");
    }

    @Test
    public void itemWritingOut(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlStatements")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Финансовые выписки клиентов"), "Fail open item WritingOut!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "В работе"), "Fail open item WritingOut!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "№ пл. док."), "Fail open item WritingOut!");
    }

    @Test
    public void itemRevenuesSources(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlRevenues")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Обороты по источникам"), "Fail open item RevenuesSources!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Интервал"), "Fail open item RevenuesSources!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Кол-во транзакций"), "Fail open item RevenuesSources!");
    }

    @Test
    public void itemRevenuesDifferential(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlDifferentialRevenues")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Дифференциальные обороты по клиентам"), "Fail open item RevenuesDifferential!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Чувствительность"), "Fail open item RevenuesDifferential!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Валюта"), "Fail open item RevenuesDifferential!");
    }

    @Test
    public void itemLookups(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlLookups")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Системные справочники"), "Fail open item Lookups!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "IP адреса"), "Fail open item Lookups!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Производственный календарь"), "Fail open item Lookups!");
    }

    @Test
    public void itemStopList(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlBlackList")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Стоп листы"), "Fail open item StopList!");
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "UserAgent"), "Fail open item StopList!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Код страны (две буквы)"), "Fail open item StopList!");
    }

    @Test
    public void itemWhiteList(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlWhiteList")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "WhiteList"), "Fail open item WhiteList!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Номер"), "Fail open item WhiteList!");
        Assert.assertTrue(TestUtils.checkItem(driver, "span", "Подтвержденные карты"), "Fail open item WhiteList!");
    }

    @Test
    public void itemSettings(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlSettings")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "a", "Настройки Whitelist"), "Fail open item Settings!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Limit.Ip.CardCount"), "Fail open item Settings!");
        Assert.assertTrue(TestUtils.checkItem(driver, "th", "Проверять транзакции с картой из белого списка"), "Fail open item Settings!");
    }

    @Test
    public void itemAudit(){

        WebDriver driver = DriverFactory.getInstance().getDriver();
        //authorized
        driver.get(baseUrl + "login/");
        Utils.authorized(driver, loginAdmin, passwordAdmin, captcha);

        //check
        driver.findElement(By.id("ctl00_content_LeftMenu1_mhlAudit")).click();
        Assert.assertTrue(TestUtils.checkItem(driver, "h2", "Журнал аудита"), "Fail open item Settings!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "ID объекта"), "Fail open item Settings!");
        Assert.assertTrue(TestUtils.checkItem(driver, "td", "Категория"), "Fail open item Settings!");
    }
}
