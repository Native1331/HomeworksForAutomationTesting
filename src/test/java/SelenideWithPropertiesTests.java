import com.codeborne.selenide.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import java.time.Duration;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
public class SelenideWithPropertiesTests {


    //private static final String baseUrl = System.getProperty("base_url");
    private static final String baseUrl = ConfigProvider.CONFIG.baseUrl();
    private static final String browser = ConfigProvider.CONFIG.browserName();
    private static final String browserSize = ConfigProvider.CONFIG.browserSize();
    private static final String adminLogin = ConfigProvider.CONFIG.adminLogin();
    private static final String adminPassword = ConfigProvider.CONFIG.adminPassword();
   /* private static final String swaggerUrl = System.getProperty("swagger_url");
    private static final String browser = System.getProperty("browser_name");
    private static final String browserSize = System.getProperty("browser_size");
    private static final String timeout =  ConfigProvider.CONFIG.timeout(); //
    private static final String adminLogin = System.getProperty("admin_login");
    private static final String adminPassword = System.getProperty("admin_password");*/
    private RequestSpecification postBasicRQ;
    private static final long timeoutSeconds = parseTimeout(ConfigProvider.CONFIG.timeout());
    // Для Configuration.timeout нужны миллисекунды
    private static final long timeoutMs = timeoutSeconds * 1000;

    private static long parseTimeout(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 10L;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            log.warn("Некорректный timeout='{}', используем 10 секунд", value);
            return 10L;
        }
    }
    @BeforeEach
    public void setup() {
        log.info("Запуск автотестов");
        Configuration.browser = browser;
        Configuration.browserSize = browserSize;
        Configuration.timeout = timeoutMs;


        open(baseUrl);
        postBasicRQ = new

                RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .log(LogDetail.ALL)
                .build();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
        log.info("Браузер закрыт");
    }


    @Test
    @Tag("SelenideWithPropertiesTests ")
    void addProduct() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(adminLogin);
        $("input[placeholder='Password']").setValue(adminPassword);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Самый лучший");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $(".products-grid").shouldBe(visible).shouldHave(text("Самый лучший"));
    }

    @Test
    @Tag("SelenideWithPropertiesTests")
    void addToCartAndVerifyInModal() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(adminLogin);
        $("input[placeholder='Password']").setValue(adminPassword);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Самый лучший");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $("button.btn[data-action='add-to-cart'][data-name='Самый лучший']").click();
        $("#open-cart-btn").shouldBe(Condition.clickable).click();
        $("div[style*='flex:1'] b")
                .shouldHave(text("Самый лучший"));
        $("#close-modal")
                .shouldBe(visible)
                .click();
        $("a.btn-outline").click();
    }



    @Test
    @Tag("SelenideWithPropertiesTests")
    void verifyCartPersistenceAfterRefresh() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(adminLogin);
        $("input[placeholder='Password']").setValue(adminPassword);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Товар для проверки корзины");
        $("#n-price").setValue("100");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $x("//*[@data-action='add-to-cart']").shouldBe(Condition.clickable).click();
        $("#open-cart-btn").shouldBe(Condition.clickable).click();
        refresh();
        $("#open-cart-btn").shouldBe(Condition.clickable).click();
        $(".modal-content").shouldBe(visible).shouldNotHave(text("Товар для проверки корзины"));
        $("#close-modal")
                .shouldBe(visible)
                .click();
        $("a.btn-outline").click();
    }

    @Test
    @Tag("SelenideWithPropertiesTests")
    void putInBasketItemsOver300RUB() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(adminLogin);
        $("input[placeholder='Password']").setValue(adminPassword);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Дорогой товар");
        $("#n-price").setValue("100");
        $("#add-btn").click();
        $("#n-name").shouldBe(visible).setValue("Очень дорогой товар");
        $("#n-price").setValue("250");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $("button.btn[data-action='add-to-cart'][data-name='Дорогой товар']").click();
        $("button.btn[data-action='add-to-cart'][data-name='Очень дорогой товар']").click();
        $("#open-cart-btn").shouldBe(Condition.clickable).click();
        $("#cartModal").shouldBe(visible);
        $("#makeOrder").click();
        Alert alert = switchTo().alert();
        boolean message = alert.getText().contains("[SmartShop]: Денег не хватает! Сумма 350 ₽ превышает лимит 300 ₽");
        alert.accept();
        $("#close-modal")
                .shouldBe(visible)
                .click();
        $("a.btn-outline").click();
    }

    @Test
    @Tag("SelenideWithPropertiesTests")
    void putInBasketItemsFor300RUB() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(adminLogin);
        $("input[placeholder='Password']").setValue(adminPassword);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Не дорогой товар");
        $("#n-price").setValue("100");
        $("#add-btn").click();
        $("#n-name").shouldBe(visible).setValue("Не очень дорогой товар");
        $("#n-price").setValue("100");
        $("#add-btn").click();
        $("#n-name").shouldBe(visible).setValue("Совсем не дорогой товар");
        $("#n-price").setValue("100");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $("button.btn[data-action='add-to-cart'][data-name='Не дорогой товар']").click();
        $("button.btn[data-action='add-to-cart'][data-name='Не очень дорогой товар']").click();
        $("button.btn[data-action='add-to-cart'][data-name='Совсем не дорогой товар']").click();
        $("#open-cart-btn").shouldBe(Condition.clickable).click();
        $("#cartModal").shouldBe(visible);
        $("#makeOrder").click();
        $("#main-title").shouldHave(text("Заказ успешно оформлен!"));
        $("a.btn-outline").click();
    }

    @Test
    @Tag("SelenideWithPropertiesTests")
    void putInBasketDifferentItems() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(adminLogin);
        $("input[placeholder='Password']").setValue(adminPassword);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Первый товар");
        $("#n-price").setValue("10");
        $("#add-btn").click();
        $("#n-name").shouldBe(visible).setValue("Второй товар");
        $("#n-price").setValue("20");
        $("#add-btn").click();
        $("#n-name").shouldBe(visible).setValue("Третий товар");
        $("#n-price").setValue("30");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $("button.btn[data-action='add-to-cart'][data-name='Первый товар']").click();
        $("button.btn[data-action='add-to-cart'][data-name='Второй товар']").click();
        $("button.btn[data-action='add-to-cart'][data-name='Третий товар']").click();
        $("#open-cart-btn").shouldBe(Condition.clickable).click();

        SelenideElement price = $("#total-price")
                .shouldBe(visible)
                .shouldHave(text("60"));

        $("#close-modal")
                .shouldBe(visible)
                .click();
        $("a.btn-outline").click();
    }

    @Test
    @Tag("SelenideWithPropertiesTests")
    void addProductInBasketAndCheckModal() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(adminLogin);
        $("input[placeholder='Password']").setValue(adminPassword);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Проверка модального окна");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $("button.btn[data-action='add-to-cart'][data-name='Проверка модального окна']").click();
        $("div.toast").shouldHave(
                text("Проверка модального окна (1 шт.) добавлен в корзину"));

    }

    @Test
    @Tag("SelenideWithPropertiesTests")
    void addProductAndCheckModal() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(adminLogin);
        $("input[placeholder='Password']").setValue(adminPassword);
        $("button.primary").click();
        $$("#n-name").first().setValue("Проверка модального окна");
        $("#n-price").setValue(String.valueOf("50"));
        $("#add-btn").click();
        ElementsCollection toastCandidates = $$("div.toast");

        toastCandidates.filter(text("Товар успешно добавлен"))
                .shouldHave(sizeGreaterThan(0));

        System.out.println(" Тост найден: " + toastCandidates.first().getText());
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $("#main-container").shouldBe(visible);
    }

    @Test
    @Tag("SelenideWithPropertiesTests")
    void updateProduct() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").setValue("Самый лучший");
        $("#n-price").setValue("50");
        $("#add-btn").shouldBe(visible).click();
        $$("input[id^='nm-']").first().setValue("Первый товар");
        $("button[data-action='update']").click();
        String currentValue = $$("input[id^='nm-']").first().getValue();
        assertEquals("Первый товар", currentValue, "Значение в input  сохранилось!");
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $$(".product-card")
                .filter(text("Первый товар"))
                .shouldHave(sizeGreaterThan(0));
    }

    @Test
    @Tag("SelenideWithPropertiesTests")
    void fullGoodsWorkflowTest() {

        given()
                .spec(postBasicRQ)
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name":"Dolls",
                        "price": 0.5
                        }
                        """)
                .auth().basic("admin", "secret123")
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .log().all();
        $$(".product-card")
                .filter(text("Dolls"))
                .shouldHave(sizeGreaterThan(0));

    }
}

