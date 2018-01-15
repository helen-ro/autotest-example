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
 * Страница /recommendations
 */
public class StartPage {

    private WebDriver driver;

    private WebDriverWait wait;

    private static final String START_PAGE_URL = "/recommendations";

    @FindBy(css = "a.btn_content_law")
    private WebElement contentLawButton;

    public StartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = (WebDriverWait) new WebDriverWait(driver, Constants.ELEMENT_TIMEOUT).withMessage(Constants.ELEMENT_ERROR_MESSAGE);
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        try {
            wait.until(ExpectedConditions.urlContains(START_PAGE_URL));
        } catch (TimeoutException e) {
            throw new IllegalStateException(Constants.CAN_NOT_GET_TO_PAGE_ERROR_MESSAGE);
        }
    }

    @Step("Переход к Правовой базе")
    public LawPage goToContentLaw() {
        wait.until(ExpectedConditions.elementToBeClickable(contentLawButton));
        contentLawButton.click();
        return new LawPage(driver);
    }


}
