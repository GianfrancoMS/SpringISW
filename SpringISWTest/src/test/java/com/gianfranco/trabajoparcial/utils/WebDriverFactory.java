package com.gianfranco.trabajoparcial.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDriverFactory {
    public static WebDriver getWebDriver(String browser) {
        switch (browser) {
            case "CHROME":
                return getChromeDriver();
            case "FIREFOX":
                return getFirefoxDriver();
            default:
                return getInternetExplorerDriver();
        }
    }

    private static WebDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        return new ChromeDriver();
    }

    private static WebDriver getFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
        return new FirefoxDriver();
    }

    private static WebDriver getInternetExplorerDriver() {
        System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
        return new InternetExplorerDriver();
    }
}
