package com.gianfranco.trabajoparcial.functionaltest;

import com.gianfranco.trabajoparcial.page.LoginPage;
import com.gianfranco.trabajoparcial.utils.ExcelReader;
import com.gianfranco.trabajoparcial.utils.Screenshot;
import com.gianfranco.trabajoparcial.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.gianfranco.trabajoparcial.utils.Listener;
import com.gianfranco.trabajoparcial.model.Client;
import com.gianfranco.trabajoparcial.model.Client.ClientBuilder;
import com.gianfranco.trabajoparcial.page.AddClientPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Listeners(Listener.class)
public class AddClientTest {
    private String url;
    private LoginPage loginPage;
    private AddClientPage addClientPage;
    private WebDriver driver;
    private Client client;

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {
        url = "http://localhost:8080/";
        driver = WebDriverFactory.getWebDriver(browser);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        loginPage = new LoginPage(driver);
        addClientPage = new AddClientPage(driver);
        driver.get(url);

        String username = "admin";
        String password = "password";
        loginPage.loginAs(username, password);
    }

    @BeforeMethod
    public void goToAddClientPageAndSetUpClient() throws Exception {
        client = new ClientBuilder().withFirstName("Gianfranco")
                .withLastname("Monzon")
                .withDni("77777777")
                .isMale(true)
                .withCity("Lima")
                .withHobby(2)
                .withDescription("Myself")
                .build();

        driver.get(url);

        addClientPage.clickButtonAdd();
    }

    @DataProvider(name = "validDni")
    public Object[][] validDni() {
        return ExcelReader.read("src/test/resources/sheets/ValidDni.xls");
    }

    @DataProvider(name = "invalidDni")
    public Object[][] invalidDni() {
        return ExcelReader.read("src/test/resources/sheets/InvalidDni.xls");
    }

    @Test(dependsOnMethods = "addClientIsCanceled", dataProvider = "validDni")
    public void addClientShowsOkMessage(String dni, String firstname) throws Exception {
        client.setDni(dni);
        client.setFirstName(firstname);

        addClientPage.setNewClient(client);
        addClientPage.submit();

        assertTrue(addClientPage.isOkMessagePresent());
        assertEquals("Client was successfully added", addClientPage.getOkMessage());
    }

    @Test(dataProvider = "validDni")
    public void addClientIsCanceled(String dni, String firstname) throws Exception {
        client.setDni(dni);
        client.setFirstName(firstname);

        addClientPage.setNewClient(client);
        addClientPage.cancel();

        assertEquals(url, driver.getCurrentUrl());
    }

    @Test(dataProvider = "validDni")
    public void addClientShowsErrorMessage(String dni, String firstname) throws Exception {
        client.setDni(dni);
        client.setFirstName(firstname);

        addClientPage.setNewClient(client);
        addClientPage.uncheckHobby(client.getHobby());
        addClientPage.submit();

        assertTrue(addClientPage.isErrorMessagePresent());
        assertEquals("There was an error with the form. See below for more details", addClientPage.getErrorMessage());

        assertTrue(addClientPage.isHobbyMessagePresent());
        assertEquals("Client must have at least 1 hobby", addClientPage.getHobbyMessage());
    }

    @Test(dataProvider = "invalidDni")
    public void addClientShowsDniInvalidFormatMessage(String dni, String firstname) throws Exception {
        client.setDni(dni);
        client.setFirstName(firstname);

        addClientPage.setNewClient(client);
        addClientPage.submit();

        assertTrue(addClientPage.isErrorMessagePresent());
        assertEquals("There was an error with the form. See below for more details", addClientPage.getErrorMessage());

        assertTrue(addClientPage.isDniMessagePresent());
        assertEquals("DNI must followed a certain pattern. e.g, 74123456", addClientPage.getDniMessage());
    }

    @Test(dependsOnMethods = "addClientShowsOkMessage", dataProvider = "validDni")
    public void addClientShowsDniIsRepeatedMessage(String dni, String firstname) throws Exception {
        client.setDni(dni);
        client.setFirstName(firstname);

        addClientPage.setNewClient(client);
        addClientPage.submit();

        assertTrue(addClientPage.isErrorMessagePresent());
        assertEquals("The DNI is duplicated. Please, enter another DNI", addClientPage.getErrorMessage());
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
