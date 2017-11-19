package com.gianfranco.trabajoparcial.functionaltest;

import com.gianfranco.trabajoparcial.page.LoginPage;
import com.gianfranco.trabajoparcial.utils.ExcelReader;
import com.gianfranco.trabajoparcial.utils.Screenshot;
import com.gianfranco.trabajoparcial.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.gianfranco.trabajoparcial.utils.Listener;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Listeners(Listener.class)
public class LoginTest {

    private String url;
    private LoginPage loginPage;
    private WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {
        url = "http://localhost:8080/";
        driver = WebDriverFactory.getWebDriver(browser);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage = new LoginPage(driver);
    }

    @BeforeMethod
    public void goToLoginPage() {
        driver.get(url);
    }

    @Test
    public void loginWithCorrectCredentialsShowsDashboard() {
        String username = "admin";
        String password = "password";
        loginPage.loginAs(username, password);

        assertEquals("Dashboard", loginPage.getTittle());

        loginPage.logout();
    }

    @Test(dataProvider = "data")
    public void loginWithIncorrectCredentialsShowsErrorMessage(String username, String password) throws Exception {
        loginPage.loginAs(username, password);

        assertTrue(loginPage.isErrorMessagePresent());
        assertEquals("Incorrect username and/or password. Please try again", loginPage.getErrorMessage());
    }

    @DataProvider(name = "data")
    public static Object[][] data() {
        return ExcelReader.read("src/test/resources/sheets/InvalidLogin.xls");
    }

    @AfterMethod
    public void takeScreenshotAndWriteFile(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE)
            Screenshot.takeScreenshotAndWriteFile(testResult, driver);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
