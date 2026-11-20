package PageObject.SelenideTests;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class AdminAssertPage extends AbstractAssert<AdminAssertPage, AdminPage> {

    public AdminAssertPage(AdminPage actual) {
        super(actual, AdminAssertPage.class);
    }

    public static AdminAssertPage assertThatPage(AdminPage actual) {
        return new AdminAssertPage(actual);
    }

    public  AdminAssertPage adminButtonClickable() {
        log.info("На странице есть кнопка \"Администрирование\"");
        isNotNull();
        actual.adminButton.shouldBe(com.codeborne.selenide.Condition.clickable);
        return this;
    }

    public AdminAssertPage usernameFieldHasValue(String expected) {
        log.info("Проверка значения поля \"Логин\"");
        isNotNull();
        assertThat(actual.userNameField.getValue())
                .as("Поле Username должно содержать: " + expected)
                .isEqualTo(expected);
        return this;
    }

    public AdminAssertPage passwordFieldHasValue(String expected) {
        log.info("Проверка значения поля \"Пароль\"");
        isNotNull();
        assertThat(actual.passwordField.getValue())
                .as("Поле Password должно содержать: " + expected)
                .isEqualTo(expected);
        return this;
    }
    public AdminAssertPage acceptButtonClickable() {
        log.info("Проверка кнопки \"Подписать\"");
        isNotNull();
        actual.acceptButton.shouldBe(com.codeborne.selenide.Condition.clickable);
        return this;
    }
}