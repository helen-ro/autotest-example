package pages;

import org.openqa.selenium.By;
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
 * Страница /law/found/
 */
public class SearchResultPage {

    private WebDriver driver;

    private WebDriverWait wait;

    private static final String RESULT_PAGE_URL = "/found";

    private static final String TYPE_REGION_LOCATOR = "//div[contains(@class,'search-extended-string')]//span[contains(text(),'%s')]";

    @FindBy(id = "searchResultsSection")
    private WebElement searchResultsSection;

    @FindBy(xpath = "//div[contains(@class,'main__in')]//*[contains(text(),'Результаты поиска по реквизитам')]")
    private WebElement resultHeader;

    @FindBy(id = "ex-search-string")
    private WebElement exSearchString;

    @FindBy(css = "div.menu__title")
    private WebElement sidebarItems;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = (WebDriverWait) new WebDriverWait(driver, Constants.ELEMENT_TIMEOUT).withMessage(Constants.ELEMENT_ERROR_MESSAGE);
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        try {
            wait.until(ExpectedConditions.urlContains(RESULT_PAGE_URL));
        } catch (TimeoutException e) {
            throw new IllegalStateException(Constants.CAN_NOT_GET_TO_PAGE_ERROR_MESSAGE);
        }
    }

    private void checkResultsSection() {
        try {
            wait.until (ExpectedConditions.visibilityOf (searchResultsSection));
        } catch (TimeoutException e) {
            throw new IllegalStateException (Constants.SEARCH_RESULT_SECTION_IS_MISSING);
        }
    }

    private void checkResultHeader(String searchWord) {
        try {
            wait.until(ExpectedConditions.visibilityOf (resultHeader));
        } catch (TimeoutException e) {
            throw new IllegalStateException (Constants.SEARCH_RESULT_HEADER_IS_MISSING);
        }
        try {
            wait.until(ExpectedConditions.visibilityOf (exSearchString));
        } catch (TimeoutException e) {
            throw new IllegalStateException (Constants.SEARCH_STRING_HEADER_IS_MISSING);
        }
        if (!exSearchString.getText().contains(searchWord))
            throw new IllegalStateException (Constants.SEARCH_STRING_HEADER_IS_WRONG);
    }

    private void checkResultType(String type) {
        By typeLocator = By.xpath(String.format(TYPE_REGION_LOCATOR, type));
        WebElement typeEntry = driver.findElement(typeLocator);
        try {
            wait.until (ExpectedConditions.visibilityOf (typeEntry));
        } catch (TimeoutException e) {
            throw new IllegalStateException (Constants.SEARCH_RESULT_TYPE_IS_MISSING);
        }
    }

    private void checkResultRegion(String region) {
        By regionLocator = By.xpath(String.format(TYPE_REGION_LOCATOR, region));
        WebElement regionEntry = driver.findElement(regionLocator);
        try {
            wait.until (ExpectedConditions.visibilityOf (regionEntry));
        } catch (TimeoutException e) {
            throw new IllegalStateException (Constants.SEARCH_RESULT_REGION_IS_MISSING);
        }
    }

    private void checkSidebar() {
        try {
            wait.until (ExpectedConditions.visibilityOf (sidebarItems));
        } catch (TimeoutException e) {
            throw new IllegalStateException (Constants.SIDEBAR_IS_MISSING);
        }
    }

    @Step("Проверка результатов поиска")
    public void checkResults(String searchWord, String type, String region) throws IllegalStateException{
        checkResultsSection();
        checkResultHeader(searchWord);
        checkResultType(type);
        checkResultRegion(region);
        checkSidebar();
    }

}
