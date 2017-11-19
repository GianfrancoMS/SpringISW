package com.gianfranco.trabajoparcial.functionaltest;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.gianfranco.trabajoparcial.utils.ExcelReader;
import com.gianfranco.trabajoparcial.utils.Listener;
import com.gianfranco.trabajoparcial.utils.Screenshot;
import com.gianfranco.trabajoparcial.utils.WebDriverFactory;
import com.gianfranco.trabajoparcial.page.EditClientPage;
import com.gianfranco.trabajoparcial.page.LoginPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Listeners(Listener.class)
public class EditClientTest {
    private String url;
    private LoginPage loginPage;
    private EditClientPage editClientPage;
    private WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {
        url = "http://localhost:8080/";
        driver = WebDriverFactory.getWebDriver(browser);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        loginPage = new LoginPage(driver);
        editClientPage = new EditClientPage(driver);
        driver.get(url);

        String username = "admin";
        String password = "password";
        loginPage.loginAs(username, password);
    }

    @BeforeMethod
    public void goToDashboard() {
        driver.get(url);
    }

    @DataProvider(name = "validDni")
    public Object[][] validDni() {
        return ExcelReader.read("src/test/resources/sheets/ValidDni.xls");
    }

    @Test(dependsOnMethods = "editClientIsCanceled", dataProvider = "validDni")
    public void editClientShowsOkMessage(String dni, String firstname) throws Exception {
        editClientPage.clickEditClientButton(dni);

        editClientPage.inputFirstname(firstname);

        editClientPage.edit();

        assertTrue(editClientPage.isOkMessagePresent());
        assertEquals("Client was successfully updated", editClientPage.getOkMessage());
    }

    @Test(dataProvider = "validDni")
    public void editClientIsCanceled(String dni, String firstname) throws Exception {
        editClientPage.clickEditClientButton(dni);

        editClientPage.inputFirstname(firstname);

        editClientPage.cancel();
    }

    @Test(dataProvider = "validDni")
    public void editClientShowsErrorMessage(String dni, String firstname) throws Exception {
        editClientPage.clickEditClientButton(dni);

        int hobby = 2;

        editClientPage.inputFirstname(firstname);
        editClientPage.uncheckHobby(hobby);

        editClientPage.edit();

        assertTrue(editClientPage.isErrorMessagePresent());
        assertEquals("There was an error with the form. See below for more details", editClientPage.getErrorMessage());

        assertTrue(editClientPage.isHobbyMessagePresent());
        assertEquals("Client must have at least 1 hobby", editClientPage.getHobbyMessage());
    }

    @Test
    public void editClientShowsDniInvalidFormatMessage() throws Exception {
        String dni = "72192339";
        editClientPage.clickEditClientButton(dni);

        String invalidDni = "322";
        editClientPage.inputDni(invalidDni);

        editClientPage.edit();

        assertTrue(editClientPage.isErrorMessagePresent());
        assertEquals("There was an error with the form. See below for more details", editClientPage.getErrorMessage());

        assertTrue(editClientPage.isDniMessagePresent());
        assertEquals("DNI must followed a certain pattern. e.g, 74123456", editClientPage.getDniMessage());
    }

    @Test(dependsOnMethods = "editClientShowsOkMessage", dataProvider = "validDni")
    public void editClientShowsDniIsRepeatedMessage(String dni, String firstname) throws Exception {
        editClientPage.clickEditClientButton(dni);

        String repeatedDni = "72192339";
        editClientPage.inputFirstname(firstname);
        editClientPage.inputDni(repeatedDni);

        editClientPage.edit();

        assertTrue(editClientPage.isErrorMessagePresent());
        assertEquals("The DNI is duplicated. Please, enter another DNI", editClientPage.getErrorMessage());
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
