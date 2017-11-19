package com.gianfranco.trabajoparcial.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditClientPage {
    @FindBy(id = "firstname")
    private WebElement inputFirstname;

    @FindBy(id = "lastname")
    private WebElement inputLastname;

    @FindBy(id = "dni")
    private WebElement inputDni;

    @FindBy(id = "radio-male")
    private WebElement radioMale;

    @FindBy(id = "radio-female")
    private WebElement radioFemale;

    @FindBy(id = "description")
    private WebElement inputDescription;

    @FindBy(id = "error-message")
    private WebElement errorMessage;

    @FindBy(id = "ok-message")
    private WebElement okMessage;

    @FindBy(id = "error-dni")
    private WebElement dniMessage;

    @FindBy(id = "error-hobby")
    private WebElement hobbyMessage;

    @FindBy(id = "button-add")
    private WebElement buttonAdd;

    @FindBy(id = "button-submit")
    private WebElement buttonSubmit;

    @FindBy(id = "button-cancel")
    private WebElement buttonCancel;

    private WebDriver driver;

    public EditClientPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void inputDni(String dni) {
        inputDni.clear();
        inputDni.sendKeys(dni);
    }

    public void inputFirstname(String firstname) {
        inputFirstname.clear();
        inputFirstname.sendKeys(firstname);
    }

    public void inputLastname(String lastname) {
        inputLastname.clear();
        inputLastname.sendKeys(lastname);
    }

    public void uncheckHobby(int hobby) {
        WebElement selectHobby = driver.findElement(By.id("hobby-" + hobby));
        selectHobby.click();
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

    public String getDniMessage() {
        return dniMessage.getText();
    }

    public boolean isDniMessagePresent() {
        return dniMessage.isDisplayed();
    }

    public String getHobbyMessage() {
        return hobbyMessage.getText();
    }

    public boolean isHobbyMessagePresent() {
        return hobbyMessage.isDisplayed();
    }

    public void clickEditClientButton(String dni) {
        WebElement client = driver.findElement(By.id("button-edit-" + dni));
        client.click();
    }

    public void edit() {
        buttonSubmit.click();
    }

    public void cancel() {
        buttonCancel.click();
    }
}
