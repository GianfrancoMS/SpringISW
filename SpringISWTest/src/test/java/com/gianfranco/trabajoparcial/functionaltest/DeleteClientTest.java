package com.gianfranco.trabajoparcial.functionaltest;

import com.gianfranco.trabajoparcial.page.DeleteClientPage;
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
public class DeleteClientTest {
    private String url;
    private LoginPage loginPage;
    private DeleteClientPage deleteClientPage;
    private WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {
        url = "http://localhost:8080";
        driver = WebDriverFactory.getWebDriver(browser);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        loginPage = new LoginPage(driver);
        deleteClientPage = new DeleteClientPage(driver);
        driver.get(url);

        String username = "admin";
        String password = "password";
        loginPage.loginAs(username, password);
    }

    @DataProvider(name = "validDni")
    public Object[][] validDni() {
        return ExcelReader.read("src/test/resources/sheets/ValidDni.xls");
    }

    @DataProvider(name = "invalidDni")
    public Object[][] invalidDni() {
        return ExcelReader.read("src/test/resources/sheets/InvalidDni.xls");
    }

    @Test(dependsOnMethods = "deleteClientIsCanceled", dataProvider = "validDni")
    public void deleteClientShowsOkMessage(String dni, String firstname) throws Exception {
        deleteClientPage.clickButtonDelete(dni);

        Thread.sleep(1000);
        assertTrue(deleteClientPage.isModalPresent());

        deleteClientPage.deleteClient();
        assertTrue(deleteClientPage.isOkMessagePresent());
        assertEquals("Client was successfully deleted", deleteClientPage.getOkMessage());
    }

    @Test(dataProvider = "validDni")
    public void deleteClientIsCanceled(String dni, String firstname) throws Exception {
        deleteClientPage.clickButtonDelete(dni);

        Thread.sleep(1000);
        assertTrue(deleteClientPage.isModalPresent());

        deleteClientPage.cancelDelete();
    }

    @Test(dataProvider = "invalidDni")
    public void deleteClientShowsErrorMessage(String dni, String firstname) throws Exception {
        driver.get("http://localhost:8080/clients/delete/" + dni);

        assertTrue(deleteClientPage.isErrorMessagePresent());
        assertEquals("Client wasn't deleted. Please try again.", deleteClientPage.getErrorMessage());
    }

    @AfterMethod
    public void goToDashboard(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE)
            Screenshot.takeScreenshotAndWriteFile(testResult, driver);
        driver.get(url);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
