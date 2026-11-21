import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.*;
import org.openqa.selenium.interactions.Actions;
import static com.codeborne.selenide.Selenide.$;


public class SelenideWithDragAndDropTests {
    SelenideElement firstElement = $(".product-card[data-name='Первый товар']");
    SelenideElement cartButton = $("#open-cart-btn");

    @BeforeEach
    void setUp() {
        open(Credentials.BASE_URL);
        $("body").shouldBe(exist);
    }


    @AfterEach
    void tearDown() {
        cleanUpTestData();
        closeWebDriver();
    }


    
    private void cleanUpTestData() {
    
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

    @Test
    @Tag("DrAndDrTests")
    void dragAndDropGood() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Первый товар");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();

        firstElement.dragAndDrop(DragAndDropOptions.to(cartButton));

        $("div.toast").shouldHave(
                text("Первый товар (1 шт.) добавлен в корзину"),
                Duration.ofSeconds(10));
        $(".products-grid").shouldBe(visible).shouldHave(text("Первый товар"));

    }

    @Test
    @Tag("DrAndDrTests")
    void anotherDragAndDropGood() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Первый товар");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        actions().moveToElement(firstElement)
                .clickAndHold()
                .moveToElement(cartButton)
                .release()
                .perform();
        //firstElement.dragAndDrop(DragAndDropOptions.to(cardButton));

        $("div.toast").shouldHave(
                text("Первый товар (1 шт.) добавлен в корзину"),
                Duration.ofSeconds(10));
        $(".products-grid").shouldBe(visible).shouldHave(text("Первый товар"));

    }

    @Test
    @Tag("DrAndDrTests")
    void thirdDragAndDropGood() {
        $(".btn-outline").shouldBe(Condition.clickable).click();
        $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
        $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").shouldBe(visible).setValue("Второй товар");
        $("#n-price").setValue("50");
        $("#add-btn").click();
        $("a").shouldHave(text("Вернуться на сайт")).click();
        int firstCardX=firstElement.getCoordinates().inViewPort().getX()+firstElement.getSize().getWidth()/2;
        int firstCardY =firstElement.getCoordinates().inViewPort().getY()+ firstElement.getSize().getHeight()/2;
        int cartButtonX=cartButton.getCoordinates().inViewPort().getX()+cartButton.getSize().getWidth()/2;;
        int cartButtonY=cartButton.getCoordinates().inViewPort().getY()+cartButton.getSize().getHeight()/2;;
        int diffX=cartButtonX-firstCardX;
        int diffY=cartButtonY- firstCardY;
        sleep(1000);
        Actions drapNdropActions=actions().moveToElement(firstElement).clickAndHold();
        for (int i=0; i<50; i++){
           drapNdropActions=drapNdropActions.moveByOffset(diffX/50,diffY/50);
        }
        drapNdropActions.release().perform();
        sleep(2000);
        $("div.toast").shouldHave(
                text("Первый товар (1 шт.) добавлен в корзину"),
                Duration.ofSeconds(10));
        $(".products-grid").shouldBe(visible).shouldHave(text("Первый товар"));

    }

@Test
@Tag("DrAndDrTests")
void deletedFromCartGood() {
    $(".btn-outline").shouldBe(Condition.clickable).click();
    $("input[placeholder='Username']").setValue(Credentials.ADMIN_LOGIN);
    $("input[placeholder='Password']").setValue(Credentials.ADMIN_PASSWORD);
    $("button.primary").click();
    $("#n-name").shouldBe(visible).setValue("Первый товар");
    $("#n-price").setValue("50");
    $("#add-btn").click();
    $("a").shouldHave(text("Вернуться на сайт")).click();
    firstElement.dragAndDrop(DragAndDropOptions.to(cartButton));
    $("div.toast").shouldHave(
            text("Первый товар (1 шт.) добавлен в корзину"),
            Duration.ofSeconds(10));
    $("#open-cart-btn").click();
    ElementsCollection cards = $$(".cart-item");
    SelenideElement product = cards.filter(text("Первый товар")).get(0);
    product.$( "button[data-action='remove']" ).click();
     product.shouldNot(Condition.visible);
}
}
