package PageObject.SelenideTests;

import com.codeborne.selenide.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStream;


import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class SelenidePageTests extends GoodsTestBase {

    static {
        try (InputStream in = SelenidePageTests.class
                .getResourceAsStream("/test.properties")) {
            if (in != null) {
                defaults.load(in);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не найден test.properties", e);
        }
    }



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
    @Tag("PageTests")
    void adminAndMainTest() {
        fillLoginAndPassword(adminLogin, adminPassword);
        addListOfGoods(allProducts, startProductPrice);
        MainAssertPage.assertThatPage(mainPage)
                .elementVisible(mainPage.returnButton, "Кнопка \"Вернуться назад\" отображается на странице");
        mainPage.clickReturn();
        MainAssertPage.assertThatPage(mainPage)
                .productIsOnPage(startProductName)
                .elementVisible(mainPage.listOfProductsOnPage.first(), "Все товары на странице");
        ;
        mainPage.addAllToCart(allProducts);
        mainPage.openCart();
        MainAssertPage.assertThatPage(mainPage)
                .productsAreInCart(allProducts);
    }
}