package resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    public static WebDriver createDriver(String browser, String driverPath)
            throws MalformedURLException, IllegalArgumentException {
        WebDriver driver;
            if (!driverPath.isEmpty()) {
                System.setProperty("webdriver.chrome.driver", driverPath);
            }
            if ("chrome".equals(browser) || "opera".equals(browser)) {
                driver = new ChromeDriver();
            } else if ("firefox".equals(browser)) {
                driver = new FirefoxDriver();
            } else {
                throw new IllegalArgumentException("Chosen browser not supported");
            }
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        return driver;
    }
}

