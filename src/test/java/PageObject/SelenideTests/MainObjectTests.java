package PageObject.SelenideTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static PageObject.SelenideTests.MainAssertPage.assertThatPage;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.refresh;

@Slf4j
public  class MainObjectTests extends GoodsTestBase {
    protected final AdminPage adminPage = page(AdminPage.class);
    protected final MainPage mainPage = page(MainPage.class);


    String expectedText = "[SmartShop]: Денег не хватает! Сумма 1500 ₽ превышает лимит 300 ₽";
    String expectedText600 = "[SmartShop]: Денег не хватает! Сумма 600 ₽ превышает лимит 300 ₽";

    @BeforeEach
    void setup() {
        log.info("Запуск автотестов");
        Configuration.browser = prop("browser_name");
        Configuration.browserSize = prop("browser_size");
        open(prop("baseUrl"));
    }

    @AfterEach
    void tearDown() {
        cleanUpTestData();
        closeWebDriver();
        log.info("Браузер закрыт");
    }

    @Test
    @Tag("MainPageTests")
    void addProduct() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addOneGood(startProductName, startProductPrice);
        assertThatPage(mainPage)
                .productIsOnPage(startProductName);
    }

    @Test
    @Tag("MainPageTests")
    void addToCartAndVerifyInModal() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addOneGood(startProductName, startProductPrice);
        addToCart(startProductName);
        assertThatPage(mainPage)
                .toastContains(startProductName)
                .productIsInCart(startProductName);
    }

    @Test
    @Tag("MainPageTests")
    void verifyCartPersistenceAfterRefresh() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addOneGood(startProductName, startProductPrice);
        addToCart(startProductName);
        assertThatPage(mainPage)
                .toastContains(startProductName)
                .productIsInCart(startProductName);
        refresh();
        mainPage.openCart();
        assertThatPage(mainPage)
                .productIsInCart(startProductName);

    }

    @Test
    @Tag("MainPageTests")
    void putInCartItemsOver300RUB() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addListOfGoods(allProducts, startProductPrice);
        mainPage.addAllToCart(allProducts);
        assertThatPage(mainPage)
                .elementVisible(mainPage.cartButton, "Кнопка корзина отображена на странице");
        mainPage.openCart();
        assertThatPage(mainPage)
                .titleModalOfCart("Корзина SmartShop")
                .totalPriceOfGoods(1500)
                .totalCountOfGoods(5);
        assertThatPage(mainPage)
                .productsAreInCart(allProducts)
                .elementVisible(mainPage.makeOrderButton, "Кнопка 'Оформить заказ'");
        mainPage.clickMakeOrder();
        assertThatPage(mainPage)
                .handleAlert(expectedText);
        mainPage.closeModal();
    }

    @Test
    @Tag("MainPageTests")
    void putInCartItemsFor300RUB() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addOneGood(startProductName, startProductPrice);
        addOneGood(startProductName + "1", startProductPrice);
        addToCart(startProductName);
        addToCart(startProductName + "1");
        mainPage.openCart();
        assertThatPage(mainPage)
                .titleModalOfCart("Корзина SmartShop")
                .totalPriceOfGoods(200)
                .totalCountOfGoods(2);
        assertThatPage(mainPage)
                .elementVisible(mainPage.makeOrderButton, "Кнопка 'Оформить заказ'");
        mainPage.clickMakeOrder();
        assertThatPage(mainPage)
                .successOrderModalVisible()
                .toastContains("Заказ успешно оформлен!");
        mainPage.closeModal();
    }

    @Test
    @Tag("MainPageTests")
    void putInCartDifferentItems() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addOneGood(startProductName, startProductPrice);
        addOneGood(startProductName + "1", startProductPrice * 3);
        addOneGood(startProductName + "2", startProductPrice * 2);
        addToCart(startProductName);
        addToCart(startProductName + "1");
        addToCart(startProductName + "2");
        mainPage.openCart();
        assertThatPage(mainPage)
                .titleModalOfCart("Корзина SmartShop")
                .totalPriceOfGoods(600)
                .totalCountOfGoods(3);
        assertThatPage(mainPage)
                .elementVisible(mainPage.makeOrderButton, "Кнопка 'Оформить заказ'");
        mainPage.clickMakeOrder();
        checkModalOfTooBigPurchase(expectedText600);
        mainPage.closeModal();
    }

    @Test
    @Tag("MainPageTests")
    void addProductInCartAndCheckModal() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addOneGood(startProductName, startProductPrice);
        addToCart(startProductName);
        assertThatPage(mainPage)
                .toastContains(startProductName + " 1 шт.) добавлен в корзину");
    }

    @Test
    @Tag("MainPageTests")
    void addProductAndCheckModal() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addOneGood(startProductName, startProductPrice);
        assertThatPage(mainPage)
                .toastContains("Товар успешно добавлен");
    }

    @Test
    @Tag("MainPageTests")
    void updateProduct() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addOneGood(startProductName, startProductPrice);
        mainPage.setUpdateGoodField(startProductName + "1");
        assertThatPage(mainPage)
                .setUpdateGoodFieldComplete(startProductName + "1");
        mainPage.setUpdateGoodField(startProductName + "1");
        assertThatPage(mainPage).setUpdateGoodFieldComplete(startProductName + "1");
        mainPage.updateGoodButton();
        assertThatPage(mainPage).newNameIsOnProductAfterUpdating(startProductName + "1");
        mainPage.clickReturn();
    }
}
