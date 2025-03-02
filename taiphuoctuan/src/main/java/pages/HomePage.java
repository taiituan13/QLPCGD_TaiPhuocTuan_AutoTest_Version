package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

public class HomePage {
    private WebDriver driver;
    private String cookieFile = "src/main/resources/cookie.data";
    private By userNav = By.id("dropdown-user");
    private By logOutButton = By.xpath("/html/body/div[2]/nav/div/ul/li[2]/div/a[2]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void userNavClick() {
        WebElement userNavElement = WaitUtils.waitForElement(driver, userNav, 2);
        userNavElement.click();
    }

    public void logOutClick(){
        WebElement logOutElement = WaitUtils.waitForElement(driver, logOutButton, 2);
        logOutElement.click();
    }

    public boolean isLoggedIn() {
        try {
            WebElement userNavElement = WaitUtils.waitForElement(driver, userNav, 10);
            return userNavElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void saveCookies() {
        Set<Cookie> cookies = driver.manage().getCookies();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cookieFile))) {
            for (Cookie cookie : cookies) {
                writer.write(cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";"
                        + cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loadCookies() {
        try (BufferedReader reader = new BufferedReader(new FileReader(cookieFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    String name = parts[0];
                    String value = parts[1];
                    String domain = parts[2];
                    String path = parts[3];
                    boolean isSecure = Boolean.parseBoolean(parts[5]);

                    Cookie cookie = new Cookie.Builder(name, value)
                            .domain(domain)
                            .path(path)
                            .isSecure(isSecure)
                            .build();
                    driver.manage().addCookie(cookie);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
