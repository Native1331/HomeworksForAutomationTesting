import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MainPage {
        /*    Задача 1: написать локаторы (допускаются и CSS-,
    и XPath-локаторы) для следующих элементов:
 1.1. Список названий товаров.
1.2. Список цен товаров.
1.3. Название товара с ценой 25 (добавьте через админку).
            1.4. Цена товара с названием «Стакан» (добавьте через админку).
            1.5. Список карточек товаров в корзине.
1.6. Кнопка корзины.

    Формат результата

    Локаторы отправляются как ответ на платформе. Каждый локатор обёрнут в блок кода.*/


    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get(Credentials.BASE_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement admin = driver.findElement(By.xpath("//*[@class='btn-outline']"));
            admin.click();
            WebElement loginXpath = driver.findElement(By.xpath("//input[@placeholder='Username']"));
            loginXpath.sendKeys(Credentials.ADMIN_LOGIN);
            WebElement passwordById = driver.findElement(By.xpath("//input[@placeholder='Password']"));
            passwordById.sendKeys(Credentials.ADMIN_PASSWORD);
            WebElement signIn = driver.findElement(By.xpath("//button[@class='primary']"));
            signIn.click();

            WebElement nameById = driver.findElement(By.id("n-name"));
            assert nameById.getText().contains("Название");
            WebElement nameXpath = driver.findElement(By.xpath("//*[@id='n-name']"));
            assert nameXpath.getText().contains("Название");
            WebElement nameHeader = driver.findElement(By.xpath("//th[normalize-space(text())='Название']"));
            assert nameHeader.getText().contains("Название");
            WebElement nameCss = driver.findElement(By.cssSelector("#n-name"));
            assert nameCss.getText().contains("Название");
            WebElement priceById = driver.findElement(By.id("n-price"));
            assert priceById.getText().contains("Цена");
            WebElement priceXpath = driver.findElement(By.xpath("//*[@id='n-price']"));
            assert priceXpath.getText().contains("Цена");
            WebElement priceXpath1 = driver.findElement(By.xpath("//input[@placeholder='Цена']"));
            assert priceXpath1.getText().contains("Цена");
            WebElement priceByCss = driver.findElement(By.cssSelector("#n-price"));
            assert priceByCss.getText().contains("Цена");

            WebElement nameOfGood = driver.findElement(By.id("nm-40"));
            assert nameOfGood.getText().contains("Стакан");
            WebElement nameOfGoodXpath = driver.findElement(By.xpath("//*[@id='nm-40']"));
            assert nameOfGoodXpath.getText().contains("Стакан");
            WebElement nameOfGoodCss = driver.findElement(By.cssSelector("#nm-40"));
            assert nameOfGoodCss.getText().contains("Стакан");
            WebElement priceOfGoodById = driver.findElement(By.id("pr-40"));
            assert priceOfGoodById.getText().contains("25");
            WebElement priceOfGoodByXpath = driver.findElement(By.xpath("//*[@id='pr-40']"));
            assert priceOfGoodByXpath.getText().contains("25");
            WebElement priceOfGoodByXpath1 = driver.findElement(By.xpath("//input[@value='25']"));
            assert priceOfGoodByXpath1.getText().contains("25");
            WebElement priceOfGoodByCss = driver.findElement(By.cssSelector("#pr-40"));
            assert priceOfGoodByCss.getText().contains("25");
            WebElement returnXpath = driver.findElement(By.xpath("//a[normalize-space(text())='Вернуться на сайт']"));
            returnXpath.click();
            WebElement addToBasketXpath = driver.findElement(By.xpath("//button[@class='btn']"));
            addToBasketXpath.click();
            WebElement basketId = driver.findElement(By.id("open-cart-btn"));
            WebElement basketXpath = driver.findElement(By.xpath("//button[@id='open-cart-btn']"));
            WebElement countOfGoods = driver.findElement(By.cssSelector("#cart-count"));
            basketId.click();
            WebElement listOfGoodById = driver.findElement(By.id("cart-item-40"));
            WebElement listOfGoodByXpath = driver.findElement(By.xpath("//*[@id='cart-item-40']"));
            WebElement listOfGoodByCss = driver.findElement(By.cssSelector("#cart-item-40"));
        } finally {
            driver.quit();
        }
    }}

