package tests;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import resources.Constants;
import resources.DriverFactory;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

import java.net.MalformedURLException;

public class SearchingTest {

    private WebDriver driver;

    @BeforeClass
    @Parameters({"browser", "autUrl", "driverPath"})
    public void setEnvironment(String browserName, String autUrl,
                               @Optional("") String driverPath) throws MalformedURLException {
        this.driver = DriverFactory.createDriver(browserName, driverPath);
        Dimension dimension = new Dimension(Constants.WIDTH, Constants.LENGTH);
        driver.manage().window().setSize(dimension);
        driver.get(autUrl);
    }

    @Severity(SeverityLevel.NORMAL)
    @Title("Тест поиска в Правовой базе")
    @Test(description = "Проверка отображения результатов поиска по реквизитам")
    @Parameters({"email", "password"})
    public void searchTest(String email, String password) {
        MainPage mainPage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainPage.goToAuthorization();
        authorizationPage.enterWithEmail();
        StartPage startPage = authorizationPage.fillFields(email, password);
        LawPage lawPage = startPage.goToContentLaw();
        SearchResultPage searchResultPage = lawPage.search("налог", "Постановление", "Москва");
        try {
            searchResultPage.checkResults("налог", "Постановление", "Москва");
        } catch (IllegalStateException e) {
            Assert.assertTrue(false, e.getMessage());
        }
        Assert.assertTrue(true);
    }
    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
