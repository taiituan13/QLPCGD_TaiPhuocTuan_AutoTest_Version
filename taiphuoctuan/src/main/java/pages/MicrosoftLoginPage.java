package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

public class MicrosoftLoginPage {
    private WebDriver driver;

    private By emailField = By.name("loginfmt");
    private By passwordField = By.name("passwd");
    private By loginButton = By.id("idSIButton9");

    public MicrosoftLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        WebElement emailElement = WaitUtils.waitForElement(driver, emailField, 10);
        emailElement.clear();
        emailElement.sendKeys(email, Keys.ENTER);
    }

    public void enterPassword(String email) {
        WebElement passwordElement = WaitUtils.waitForElement(driver, passwordField, 10);
        passwordElement.clear();
        passwordElement.sendKeys(email, Keys.ENTER);
    }

    public void clickLoginButton() {
        WebElement loginBtn = WaitUtils.waitForElement(driver, loginButton, 15);
        loginBtn.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

}