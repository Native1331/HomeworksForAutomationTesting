package PageObject.SelenideTests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.Alert;
import java.time.Duration;
import java.util.List;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class MainAssertPage extends AbstractAssert<MainAssertPage, MainPage> {

    public MainAssertPage(MainPage actual) {
        super(actual, MainAssertPage.class);
    }

    public static MainAssertPage assertThatPage(MainPage actual) {
        return new MainAssertPage(actual);
    }

    public MainAssertPage elementVisible(SelenideElement element, String name) {
        log.info("Проверка, что '{}' отображается на странице", name);
        element.shouldBe(visible.because("элемент '" + name + "' должен быть виден"));
        return this;
    }

    public MainAssertPage fieldOfNameCompleted(String expected) {
        assertThat(actual.nameOfGoodField.getValue())
                .as("Поле 'Название' должно содержать '%s'", expected)
                .isEqualTo(expected);
        return this;
    }

    public MainAssertPage fieldOfPriceCompleted(String expected) {
        assertThat(actual.priceField.getValue())
                .as("Поле 'Цена' должно содержать '%s'", expected)
                .isEqualTo(expected);
        return this;
    }

    public MainAssertPage productIsOnPage(String expected) {
        assertThat(actual.listOfGoods.getText())
                .as("Список товаров должен содержать товар '%s'", expected)
                .contains(expected);
        return this;
    }

    public MainAssertPage productIsInCart(String expected) {
        actual.cartModal.shouldBe(visible);
        assertThat(actual.cartModal.getText())
                .as("Корзина должна содержать товар '%s'", expected)
                .contains(expected);
        return this;
    }

    public MainAssertPage productsAreInCart(List<String> expected) {
        actual.cartModal.shouldBe(visible);
        String cartText = actual.cartModal.getText();

        for (String product : expected) {
            assertThat(cartText)
                    .as("Корзина должна содержать товар '%s'", product)
                    .contains(product);
        }
        return this;
    }

    public MainAssertPage toastContains(String expected) {
        actual.toast.shouldBe(visible.because("тост должен успеть появиться"));
        assertThat(actual.toast.getText())
                .as("Тост должен содержать '%s'", expected)
                .contains(expected);
        return this;
    }

    public MainAssertPage toastDisabled() {
        actual.toast.should(disappear);
        return this;
    }

    public MainAssertPage successOrderModalVisible() {
        actual.successOrderTitle.shouldBe(visible.because("после заказа должно появиться окно успеха"));
        return this;
    }

    public MainAssertPage titleModalOfCart(String expected) {
        assertThat(actual.modalCart.getText())
                .as("Заголовок корзины должен содержать '%s'", expected)
                .contains(expected);
        return this;
    }

    public MainAssertPage totalPriceOfGoods(int expected) {
        int actualValue = Integer.parseInt(
                actual.countOfPrice.getText().replaceAll("[^0-9]", ""));
        assertThat(actualValue)
                .as("Сумма в корзине должна быть '%s'", expected)
                .isEqualTo(expected);
        return this;
    }

    public MainAssertPage totalCountOfGoods(int expected) {
        int actualValue = Integer.parseInt(
                actual.countOfGoods.getText().replaceAll("[^0-9]", ""));
        assertThat(actualValue)
                .as("В корзине должно быть товаров '%s'", expected)
                .isEqualTo(expected);
        return this;
    }

    public MainAssertPage handleAlert(String expected) {
        Alert alert = switchTo().alert();
        assertThat(alert.getText())
                .as("Текст модального окна с ошибкой должен быть '%s'", expected)
                .isEqualTo(expected);
        alert.accept();
        return this;
    }

    public MainAssertPage setUpdateGoodFieldComplete(String expected) {
        assertThat(actual.updateGoodField.getValue())
                .as("Поле обновления названия должно быть заполнено '%s'", expected)
                .isEqualTo(expected);
        return this;
    }

    public MainAssertPage newNameIsOnProductAfterUpdating(String expected) {
        assertThat(actual.listOfProductsOnPage.texts())
                .as("Товар с новым именем должен отображаться на странице")
                .anyMatch(item -> item.contains(expected));
        return this;
    }
    public MainAssertPage elementsVisible(ElementsCollection collection, String name) {
        collection.shouldHave(CollectionCondition.sizeGreaterThan(0),
                Duration.ofSeconds(5));
        collection.first().shouldBe(visible.because("элемент '" + name + "' должен быть виден"));
        return this;
    }
}