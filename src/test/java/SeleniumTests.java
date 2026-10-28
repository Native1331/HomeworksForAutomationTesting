import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class SeleniumTests {

    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void clickLoginButton() {
        wait.until(elementToBeClickable(By.cssSelector(".btn-outline"))).click();
    }

    private void fillLoginForm(String login, String password) {
        driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys(login);
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(password);
    }

    private void submitLogin() {
        driver.findElement(By.cssSelector("button.primary")).click();
    }


    @Test
    @Tag("SeleniumTests")
    void addProduct() {
        driver.get(Credentials.BASE_URL);
        clickLoginButton();
        fillLoginForm(Credentials.ADMIN_LOGIN, Credentials.ADMIN_PASSWORD);
        submitLogin();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("n-name")))
                .sendKeys("Самый лучший");
        driver.findElement(By.id("n-price")).sendKeys("50");
        driver.findElement(By.xpath("//a[normalize-space(text())='Вернуться на сайт']")).click();
        String gridText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products-grid")))
                .getText();
        assertThat(gridText)
                .as("Новый товар должен отображаться на витрине")
                .contains("Самый лучший");
    }


    @Test
    @Tag("SeleniumTests")
    void addToCartAndVerifyInModal() {
        driver.get(Credentials.BASE_URL);
        clickLoginButton();
        fillLoginForm(Credentials.ADMIN_LOGIN, Credentials.ADMIN_PASSWORD);
        submitLogin();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("n-name")))
                .sendKeys("Самый лучший");
        driver.findElement(By.id("n-price")).sendKeys("50");
        driver.findElement(By.id("add-btn")).click();
        driver.findElement(By.xpath("//a[normalize-space(text())='Вернуться на сайт']")).click();

        wait.until(elementToBeClickable(By.cssSelector( "button.btn[data-action='add-to-cart'][data-name='Самый лучший']"))).click();
        wait.until(elementToBeClickable(By.id("open-cart-btn"))).click();

        String modalText = driver.findElement(By.xpath("//div[@class='modal-content']")).getText();

        assertThat(modalText)
                .as("Товар должен отображаться в корзине")
                .contains("Самый лучший");
    }

    @Test
    @Tag("SeleniumTests")
    void loginWithInvalidCredentials() {
        driver.get(Credentials.BASE_URL);
        clickLoginButton();
        fillLoginForm("wrong_login", "wrong_password");
        submitLogin();
        String modalText = driver.findElement(By.xpath("//div[@class='alert alert-danger']")).getText();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-danger")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-danger")));
        assertThat(modalText)
                .as("Сообщение об ошибке должно отображаться")
                .isEqualTo("Неверные учетные данные пользователя");
    }

    @Test
    @Tag("SeleniumTests")
    void verifyCartPersistenceAfterRefresh() {
        driver.get(Credentials.BASE_URL);
        clickLoginButton();
        fillLoginForm(Credentials.ADMIN_LOGIN, Credentials.ADMIN_PASSWORD);
        submitLogin();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("n-name")))
                .sendKeys("Товар для проверки корзины");
        driver.findElement(By.id("n-price")).sendKeys("100");
        driver.findElement(By.id("add-btn")).click();
        driver.findElement(By.xpath("//a[normalize-space(text())='Вернуться на сайт']")).click();

        wait.until(elementToBeClickable(By.xpath("//*[@data-action='add-to-cart']"))).click();
        wait.until(elementToBeClickable(By.id("open-cart-btn"))).click();
           // Обновляем страницу
        driver.navigate().refresh();

        // Открываем корзину снова (модалка могла закрыться)
        wait.until(elementToBeClickable(By.id("open-cart-btn"))).click();

        String modalText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-content")))
                .getText();

        assertThat(modalText)
                .as("Товары должны сохраняться в корзине после обновления страницы")
                .contains("Товар для проверки корзины");
    }
}