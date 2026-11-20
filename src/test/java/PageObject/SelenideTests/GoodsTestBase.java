package PageObject.SelenideTests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.switchTo;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public abstract class GoodsTestBase {

    public final AdminPage adminPage = page(AdminPage.class);
    public final MainPage mainPage = page(MainPage.class);
    public final String adminLogin = prop("admin_login");
    public final String adminPassword = prop("admin_password");
    public final String startProductName = prop("start_product_name");
    public final int startProductPrice = Integer.parseInt(prop("start_product_price"));
    public static final RequestSpecification postBasicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .log(LogDetail.ALL)
            .build();
    protected final List<String> allProducts = List.of(
            startProductName,
            startProductName + "1",
            startProductName + "2",
            startProductName + "3",
            startProductName + "4");

    protected static final Properties defaults = new Properties();

    static {
        try (InputStream in = GoodsTestBase.class.getResourceAsStream("/test.properties");
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            if (in != null) {
                defaults.load(reader);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не найден test.properties", e);
        }
    }

    protected static String prop(String name) {
        return Objects.requireNonNull(
                System.getProperty(name, defaults.getProperty(name)),
                "Не задано свойство -D" + name + " и нет значения в test.properties");
    }

       public static void cleanUpTestData() {
        List<String> targetNames = Arrays.asList(
                "Дорогой товар", "Очень дорогой товар", "Товар для успеха",
                "Ещё один товар", "Самый лучший", "Не дорогой товар",
                "Не очень дорогой товар", "Совсем не дорогой товар",
                "Проверка модального окна", "Первый товар", "Второй товар",
                "Третий товар", "Модальное окно", "Dolls");

        List<Map<String, Object>> allGoods = given()
                .spec(postBasicRQ)
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("goods");


        if (allGoods == null || allGoods.isEmpty()) {
            System.out.println("Нет товаров для очистки — ничего не удаляем.");
            return;
        }

        for (Map<String, Object> good : allGoods) {
            String name = String.valueOf(good.get("name"));
            if (targetNames.contains(name)) {
                int id = ((Number) good.get("id")).intValue();
                given()
                        .spec(postBasicRQ)
                        .auth().basic("admin", "secret123")
                        .pathParam("id", id)
                        .when()
                        .delete("/goods/{id}")
                        .then()
                        .statusCode(200);
            }
        }
    }




    protected void fillLoginAndPassword(String login, String password) {
        AdminAssertPage.assertThatPage(adminPage)
                .adminButtonClickable();
        adminPage.openAdmin();
        adminPage.login(adminLogin, adminPassword);
        AdminAssertPage.assertThatPage(adminPage)
                .usernameFieldHasValue(adminLogin)
                .passwordFieldHasValue(adminPassword);
        AdminAssertPage.assertThatPage(adminPage)
                .acceptButtonClickable();
        adminPage.clickAcceptButton();    }

    protected void addOneGood(String startProductName, int startProductPrice) {
        MainAssertPage.assertThatPage(mainPage)
                .elementVisible(mainPage.nameOfGoodField, "Название")
                .elementVisible(mainPage.priceField, "Цена")
                .elementVisible(mainPage.addNewGoodButton, "Создать");
        mainPage.setNameOfGood(startProductName);
        mainPage.setPrice(String.valueOf(startProductPrice));
        MainAssertPage.assertThatPage(mainPage)
                .fieldOfNameCompleted(startProductName)
                .fieldOfPriceCompleted(String.valueOf(startProductPrice));
        mainPage.clickAddGood();
        MainAssertPage.assertThatPage(mainPage)
                .elementVisible(mainPage.toast,"Всплывающее окно об успешном добавлении товара, присутствует на странице")
                .toastContains("Товар успешно добавлен")
                .elementVisible(mainPage.returnButton, "Вернуться на сайт");
        mainPage.clickReturn();
        MainAssertPage.assertThatPage(mainPage)
                .productIsOnPage(startProductName);
    }


    protected void addListOfGoods(List<String> allProducts, int startProductPrice) {
        for (int i = 0; i < allProducts.size(); i++) {
            String name = allProducts.get(i);
            String currentPrice = String.valueOf(startProductPrice + i * 100);

            mainPage.setNameOfGood(name);
            mainPage.setPrice(currentPrice);

            MainAssertPage.assertThatPage(mainPage)
                    .fieldOfNameCompleted(name)
                    .fieldOfPriceCompleted(currentPrice);

            mainPage.clickAddGood();

            MainAssertPage.assertThatPage(mainPage)
                    .elementVisible(mainPage.toast, "Всплывающее окно появляется на странице")
                    .toastContains("Товар успешно добавлен")
                    .toastDisabled();

        }

        mainPage.clickReturn();
        MainAssertPage.assertThatPage(mainPage)
                .productIsOnPage(allProducts.get(0));
    }

    public void addToCart(String startProductName) {
        MainAssertPage.assertThatPage(mainPage)
                .productIsOnPage(startProductName)
                .elementVisible(mainPage.addGoodToCartButton, "Кнопка добавить в корзину активна");
        mainPage.clickGoodToCartButton();
        mainPage.openCart();
    }
    public void checkModalOfTooBigPurchase(String expectedText) {
        Alert alert = switchTo().alert();
        assertThat(alert.getText())
                .as("Текст алерта должен быть '%s'", expectedText)
                .isEqualTo(expectedText);
        alert.accept();
    }
    }