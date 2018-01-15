package pages;

import org.openqa.selenium.Keys;
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
 * Страница /law
 */
public class LawPage {

    private WebDriver driver;

    private WebDriverWait wait;

    private static final String LAW_PAGE_URL = "/law";

    private ExtendedSearchPanel extendedSearchPanel;

    @FindBy(id = "search-button-extended")
    private WebElement extendedSearchButton;

    @FindBy(id = "search-text")
    private WebElement searchTextField;

    public LawPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = (WebDriverWait) new WebDriverWait(driver, Constants.ELEMENT_TIMEOUT).withMessage(Constants.ELEMENT_ERROR_MESSAGE);
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        try {
            wait.until(ExpectedConditions.urlContains(LAW_PAGE_URL));
        } catch (TimeoutException e) {
            throw new IllegalStateException(Constants.CAN_NOT_GET_TO_PAGE_ERROR_MESSAGE);
        }
    }

    @Step("Переход к поиску по реквизитам")
    private ExtendedSearchPanel openExtendedSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(extendedSearchButton));
        extendedSearchButton.click();
        return new ExtendedSearchPanel(driver);
    }

    @Step("Заполнение полей для поиска по реквизитам")
    public SearchResultPage search(String searchWord, String type, String region) {
        this.extendedSearchPanel = openExtendedSearch();
        wait.until(ExpectedConditions.elementToBeClickable(searchTextField));
        searchTextField.sendKeys(searchWord);
        searchTextField.sendKeys(Keys.TAB);
        this.extendedSearchPanel.chooseType(type);
        this.extendedSearchPanel.chooseRegion(region);
        return this.extendedSearchPanel.startSearch();
    }
}
