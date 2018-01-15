package pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.Constants;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.TimeUnit;

/**
 * Страница /customer/auth
 */
public class AuthorizationPage {

    private WebDriver driver;

    private WebDriverWait wait;

    private static final String CUSTOMER_AUTH_PAGE_URL = "/customer/auth";

    @FindBy(id = "wf-enter")
    private WebElement enterWithEmailTab;

    @FindBy(id="email")
    private WebElement emailField;

    @FindBy(id="password")
    private WebElement passwordField;

    @FindBy(id="customer-enter")
    private WebElement enterButton;

    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = (WebDriverWait) new WebDriverWait(driver, Constants.ELEMENT_TIMEOUT).withMessage(Constants.ELEMENT_ERROR_MESSAGE);
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        try {
            wait.until(ExpectedConditions.urlContains(CUSTOMER_AUTH_PAGE_URL));
        } catch (TimeoutException e) {
            throw new IllegalStateException(Constants.CAN_NOT_GET_TO_PAGE_ERROR_MESSAGE);
        }
    }

    @Step("Войдите на сайт под своей почтой и паролем")
    public void enterWithEmail() {
        wait.until(ExpectedConditions.elementToBeClickable(enterWithEmailTab));
        enterWithEmailTab.click();
        wait.until(ExpectedConditions.visibilityOf(emailField));
    }

    @Step("Заполнение полей формы входа")
    public StartPage fillFields(String email, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailField.sendKeys(email);
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(enterButton));
        enterButton.click();
        return new StartPage(driver);
    }

}
