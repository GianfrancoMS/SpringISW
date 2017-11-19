package com.gianfranco.trabajoparcial.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteClientPage {
    @FindBy(id = "buttonRemove")
    private WebElement buttonRemove;

    @FindBy(id = "buttonCancel")
    private WebElement buttonCancel;

    @FindBy(id = "error-message")
    private WebElement errorMessage;

    @FindBy(id = "ok-message")
    private WebElement okMessage;

    @FindBy(id = "myModalLabel")
    private WebElement modal;

    private WebDriver driver;

    public DeleteClientPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isModalPresent() {
        return modal.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isErrorMessagePresent() {
        return errorMessage.isDisplayed();
    }

    public String getOkMessage() {
        return okMessage.getText();
    }

    public boolean isOkMessagePresent() {
        return okMessage.isDisplayed();
    }

    public void clickButtonDelete(String dni) {
        WebElement client = driver.findElement(By.id("button-delete-" + dni));
        client.click();
    }

    public void deleteClient() {
        buttonRemove.click();
    }

    public void cancelDelete() {
        buttonCancel.click();
    }
}
