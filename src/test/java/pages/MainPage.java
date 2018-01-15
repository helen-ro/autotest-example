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
 * Основная страница сайта, до авторизации
 */
public class MainPage {

    private WebDriver driver;

    private WebDriverWait wait;

    @FindBy(id = "user-enter")
    private WebElement logInButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = (WebDriverWait) new WebDriverWait(driver, Constants.ELEMENT_TIMEOUT).withMessage(Constants.ELEMENT_ERROR_MESSAGE);
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        try {
            wait.until(ExpectedConditions.visibilityOf(logInButton));
        } catch (TimeoutException e) {
            throw new IllegalStateException(Constants.CAN_NOT_GET_TO_PAGE_ERROR_MESSAGE);
        }
    }

    @Step("Переход на страницу ввода учетных данных")
    public AuthorizationPage goToAuthorization() {
        wait.until(ExpectedConditions.elementToBeClickable(logInButton));
        logInButton.click();
        return new AuthorizationPage(driver);
    }
}
