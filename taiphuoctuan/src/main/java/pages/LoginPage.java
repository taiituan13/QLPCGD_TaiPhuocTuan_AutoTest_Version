package pages;

import org.openqa.selenium.By;

// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.Set;

// import org.openqa.selenium.By;
// import org.openqa.selenium.Cookie;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.support.PageFactory;
// import utils.WaitUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import utils.WaitUtils;

import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;
    private By openIdConnect = By.name("provider");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(id = "OpenIdConnect")
    private WebElement IdConnect;

    public void openIdConnect() {
        IdConnect.click();
    }

    public void clickOpenIdConnect() {
        WebElement openIdConnectButton = WaitUtils.waitForElement(driver, openIdConnect, 10);
        openIdConnectButton.click();
    }

}