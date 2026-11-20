package PageObject.SelenideTests;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminPage {

    @FindBy(css = "a.btn-outline")
    SelenideElement adminButton;
    @FindBy(css = "input[placeholder='Username']")
    SelenideElement userNameField;
    @FindBy(css = "input[placeholder='Password']")
    SelenideElement passwordField;
    @FindBy(css = "button.primary")
    SelenideElement acceptButton;

    public AdminPage openAdmin() {
        log.info("Переход в админку");
        adminButton.click();
        return this;
    }

    public AdminPage login(String login, String password) {
        log.info("Логин администратора: {}", login);
        userNameField.setValue(login);
        passwordField.setValue(password);
        return this;
    }
    public AdminPage clickAcceptButton() {
        log.info("Нажата кнопка \"Подписать\"");
                acceptButton.click();
        return this;
    }

}