import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class AllPages {
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

    @FindBy(id = "n-name")
    private SelenideElement nameOfGood;
    @FindBy(xpath = "//*[@id='n-name']")
    private SelenideElement nameOfGood1;
    @FindBy(xpath = "//th[normalize-space(text())='Название']")
    private SelenideElement nameOfGood2;
    @FindBy(css = "#n-name")
    private SelenideElement nameOfGood3;
    @FindBy(id = "n-price")
    private SelenideElement price;
    @FindBy(xpath = "//*[@id='n-price']")
    private SelenideElement price1;
    @FindBy(xpath = "//input[@placeholder='Цена']")
    private SelenideElement price2;
    @FindBy(css = "#n-price")
    private SelenideElement price3;
    @FindBy(id = "nm-40")
    private SelenideElement nameOfMyNewGood;
    @FindBy(xpath = "//*[@id='nm-40']")
    private SelenideElement nameOfMyNewGood1;
    @FindBy(css = "#nm-40")
    private SelenideElement nameOfMyNewGood2;
    @FindBy(id = "pr-40")
    private SelenideElement priceOfMyNewGood;
    @FindBy(xpath = "//*[@id='pr-40']")
    private SelenideElement priceOfMyNewGood1;
    @FindBy(xpath = "@//input[@value='25']")
    private SelenideElement priceOfMyNewGood2;
    @FindBy(css = "#pr-40")
    private SelenideElement priceOfMyNewGood3;
    @FindBy(xpath = "@//a[normalize-space(text())='Вернуться на сайт']")
    private SelenideElement returnButton;
    @FindBy(xpath = "@//button[@class='btn']")
    private SelenideElement addToBasketXpath;
    @FindBy(id = "open-cart-btn")
    private SelenideElement basketId;
    @FindBy(xpath = "@//button[@id='open-cart-btn']")
    private SelenideElement basketXpath;
    @FindBy(css = "#cart-count")
    private SelenideElement countOfGoods;
    @FindBy(id = "cart-item-40")
    private SelenideElement listOfGoodById;
    @FindBy(xpath = "@//*[@id='cart-item-40']")
    private SelenideElement listOfGoodByXpath;
    @FindBy(css = "#cart-item-40")
    private SelenideElement listOfGoodByCss;

}

