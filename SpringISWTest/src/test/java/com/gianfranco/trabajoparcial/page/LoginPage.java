package com.gianfranco.trabajoparcial.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "username")
    private WebElement inputUsername;

    @FindBy(id = "password")
    private WebElement inputPassword;

    @FindBy(id = "button-login")
    private WebElement buttonLogin;

    @FindBy(id = "error-message")
    private WebElement errorMessage;

    @FindBy(id = "button-logout")
    private WebElement buttonLogout;

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isErrorMessagePresent() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getTittle() {
        return driver.getTitle();
    }

    public void logout() {
        buttonLogout.click();
    }

    public void loginAs(String username, String password) {
        typeUser(username);
        typePassword(password);
        clickLoginButton();
    }

    private void typeUser(String username) {
        inputUsername.clear();
        inputUsername.sendKeys(username);
    }

    private void typePassword(String password) {
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    private void clickLoginButton() {
        buttonLogin.click();
    }
}
