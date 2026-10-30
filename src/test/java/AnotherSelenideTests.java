import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnotherSelenideTests {

    @BeforeEach
    void setUp() {
        open(Credentials.BASE_URL);
        $(By.cssSelector("body")).shouldBe(exist);
    }


    @AfterEach
    void tearDown() {
        cleanUpTestData();
        closeWebDriver();
    }


    private void cleanUpTestData() {
       /* Первый вариант-удалить все товары, без конкретики
              given()
                .spec(postBasicRQ)
                .auth().basic("admin", "secret123")
                .when()
                .delete("/goods")
                .then()
                .log().all()
                .statusCode(200); */
       //Вариант 2.Конкретные продукты.
        List<String> targetNames = Arrays.asList(
                "Дорогой товар", "Очень дорогой товар", "Товар для успеха",
                "Ещё один товар", "Самый лучший", "Не дорогой товар",
                "Не очень дорогой товар", "Совсем не дорогой товар",
                "Проверка модального окна", "Первый товар", "Второй товар",
                "Третий товар", "Модальное окно", "Dolls"
        );

        List<Map<String, Object>> allGoods = given()
                .spec(postBasicRQ)
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("goods");

        if (allGoods == null || allGoods.isEmpty()) {
            System.out.println("Нет товаров для очистки — ничего не удаляем.");
            return;}

        for (Map<String, Object> good : allGoods) {
            String name = (String) good.get("name");
            if (targetNames.contains(name)) {
                int id = ((Number) good.get("id")).intValue();

                given()
                        .spec(postBasicRQ)
                        .auth().basic("admin", "secret123")
                        .pathParam("id", id)
                        .when()
                        .delete("/goods/{id}")
                        .then()
                        .log().all()
                        .statusCode(200);
            }
        }}




    private RequestSpecification postBasicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .log(LogDetail.ALL)
            .build();


    @Test
    @Tag("SelenideTests")
    void addProduct() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Самый лучший");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $(".products-grid").shouldBe(visible).shouldHave(text("Самый лучший"));
    }

    @Test
    @Tag("SelenideTests")
    void addToCartAndVerifyInModal() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Самый лучший");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт"), Duration.ofSeconds(5)).click();
        $("button.btn[data-action='add-to-cart'][data-name='Самый лучший']").click();
        $("#open-cart-btn").shouldBe(Condition.clickable).click();
        $("div[style*='flex:1'] b")
                .shouldHave(text("Самый лучший"), Duration.ofSeconds(10));
        $("#close-modal")
                .shouldBe(visible)
                .click();
        $("a.btn-outline").click();
    }


    @Test
    @Tag("SelenideTests")
    void loginWithInvalidCredentials() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $(".alert.alert-danger")
                .shouldHave(text("Неверные учетные данные пользователя"), Duration.ofSeconds(10));
    }

    @Test
    @Tag("SelenideTests")
    void verifyCartPersistenceAfterRefresh() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
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
    @Tag("SelenideTests")
    void putInBasketItemsOver300RUB() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
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
    @Tag("SelenideTests")
    void putInBasketItemsFor300RUB() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
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
    @Tag("SelenideTests")
    void putInBasketDifferentItems() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
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
    @Tag("SelenideTests")
    void addProductInBasketAndCheckModal() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Проверка модального окна");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $("button.btn[data-action='add-to-cart'][data-name='Проверка модального окна']").click();
        $("div.toast").shouldHave(
                text("Проверка модального окна (1 шт.) добавлен в корзину"),
                Duration.ofSeconds(10)
        );

    }

    @Test
    @Tag("SelenideTests")
    void addProductAndCheckModal() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $$("#n-name").first().setValue("Проверка модального окна");
        $("#n-price").setValue(String.valueOf("50"));
        $("#add-btn").click();
        ElementsCollection toastCandidates = $$("div.toast");

        toastCandidates.filter(text("Товар успешно добавлен"))
                .shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));

        System.out.println(" Тост найден: " + toastCandidates.first().getText());
        $("a").shouldHave(text("Вернуться на сайт")).click();
        $("#main-container").shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @Tag("SelenideTests")
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
                .shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));
    }

    @Test
    @Tag("SelenideTests")
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
                .shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));

    }}
