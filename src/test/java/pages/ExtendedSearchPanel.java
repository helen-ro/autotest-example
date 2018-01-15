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
 * Всплывающий блок расширенного поиска
 */
public class ExtendedSearchPanel {

    private WebDriver driver;

    private WebDriverWait wait;

    private static final String TYPE_LOCATOR = "//*[@id='typelist']/li[text()='%s']";

    private static final String REGION_LOCATOR = "//*[@id='regionlist']/li[text()='%s']";

    @FindBy(id = "button-search-extended")
    private WebElement searchButton;

    public ExtendedSearchPanel(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = (WebDriverWait) new WebDriverWait(driver, Constants.ELEMENT_TIMEOUT).withMessage(Constants.ELEMENT_ERROR_MESSAGE);
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        try {
            wait.until(ExpectedConditions.visibilityOf(searchButton));
        } catch (TimeoutException e) {
            throw new IllegalStateException(Constants.CAN_NOT_OPEN_PANEL_ERROR_MESSAGE);
        }
    }

    @Step("Выбор типа документа")
    public void chooseType(String type) {
        By typeLocator = By.xpath(String.format(TYPE_LOCATOR, type));
        WebElement typeEntry = driver.findElement(typeLocator);
        wait.until(ExpectedConditions.visibilityOf(typeEntry));
        wait.until(ExpectedConditions.elementToBeClickable(typeEntry));
        typeEntry.click();
    }

    @Step("Выбор региона")
    public void chooseRegion(String region) {
        By regionLocator = By.xpath(String.format(REGION_LOCATOR, region));
        WebElement regionEntry = driver.findElement(regionLocator);
        wait.until(ExpectedConditions.visibilityOf(regionEntry));
        wait.until(ExpectedConditions.elementToBeClickable(regionEntry));
        regionEntry.click();
    }

    @Step("Осуществление поиска")
    public SearchResultPage startSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
        return new SearchResultPage(driver);
    }
}
