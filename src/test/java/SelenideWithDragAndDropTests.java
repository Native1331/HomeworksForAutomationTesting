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
        try {
            System.out.println(" Начинаем очистку тестовых данных...");

            String[] testProductNames = {
                    "Первый товар"

            };

            for (String name : testProductNames) {
                var productRow = $$x("//div[contains(text(), '" + name + "')]")
                        .filter(visible)
                        .first();

                if (productRow.exists()) {
                    var deleteBtn = productRow.find("button[data-action='delete']");

                    if (deleteBtn.exists()) {
                        deleteBtn.click();
                        System.out.println(" Товар '" + name + "' удалён.");

                    } else {
                        System.out.println(" Кнопка удаления не найдена для '" + name + "'. Товар есть, но удалить не удалось.");
                    }
                } else {
                    System.out.println(" Товар '" + name + "' не найден на странице. Возможно, он не создавался в этом прогоне.");
                }
            }
        } catch (Exception e) {
            System.err.println(" Ошибка при очистке тестовых данных: " + e.getMessage());
        }
    }

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
