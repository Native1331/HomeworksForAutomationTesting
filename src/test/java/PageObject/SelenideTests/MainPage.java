package PageObject.SelenideTests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    @FindBy(id = "n-name")
    SelenideElement nameOfGoodField;
    @FindBy(id = "n-price")
    SelenideElement priceField;
    @FindBy(id = "add-btn")
    SelenideElement addNewGoodButton;
    @FindBy(id = "toast-container")
    SelenideElement returnButton;
    @FindBy(css = ".products-grid")
    SelenideElement listOfGoods;
    @FindBy(id = "open-cart-btn")
    public SelenideElement cartButton;
    @FindBy(id = "cartModal")
    SelenideElement cartModal;
    @FindBy(id = "cart-count")
    SelenideElement countOfGoods;
    @FindBy(id = "add-to-cart")
    SelenideElement addGoodToCartButton;
    @FindBy(id = "total-price")
    SelenideElement countOfPrice;
    @FindBy(id = "makeOrder")
    SelenideElement makeOrderButton;
    @FindBy(id = "modal-content")
    SelenideElement modalCart;
    @FindBy(id = "close-modal")
    SelenideElement closeModalButton;
    @FindBy(css = "div.toast")
    SelenideElement toast;
    @FindBy(id = "main-title")
    SelenideElement successOrderTitle;
    @FindBy(css = "input[id^='nm-']")
    SelenideElement updateGoodField;
    @FindBy(css = "button[data-action='update']")
    SelenideElement successUpdateGood;
    @FindBy(css = "input[id^='nm-']")
    ElementsCollection listOfProductsOnPage;

    private static final By ADD_TO_CART_BUTTON =
            By.cssSelector("button[data-action='add-to-cart']");


    // Создание товара
    public MainPage setNameOfGood(String name) {
        nameOfGoodField.setValue(name);
        return this;
    }

    public MainPage setPrice(String price) {
        priceField.setValue(price);
        return this;
    }

    public MainPage clickAddGood() {
        addNewGoodButton.click();
        return this;
    }

    public MainPage clickReturn() {
        returnButton.click();
        return this;
    }

    public MainPage clickGoodToCartButton() {
        addGoodToCartButton.click();
        return this;
    }

    // Добавление в корзину
    public void addAllToCart(List<String> productNames) {
        for (String name : productNames) {
            $$("div.product-card")
                    .findBy(Condition.text(name))
                    .$(ADD_TO_CART_BUTTON)
                    .click();
        }
    }

    public MainPage openCart() {
        cartButton.click();
        return this;
    }

    public MainPage clickMakeOrder() {
        makeOrderButton.click();
        return this;
    }

    public void closeModal() {
        closeModalButton.click();
    }

    public void setUpdateGoodField(String goodName) {
        updateGoodField.setValue(goodName);
    }

    public MainPage updateGoodButton() {
        successUpdateGood.click();
        return this;
    }
   }