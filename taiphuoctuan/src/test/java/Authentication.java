
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.ConfigReader;
import config.DriverManager;
import pages.HomePage;
import pages.LoginPage;
import pages.MicrosoftLoginPage;

public class Authentication {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private MicrosoftLoginPage microsoftLoginPage;
    String username = ConfigReader.getProperty("username");
    String password = ConfigReader.getProperty("password");

    // public Authentication(WebDriver driver) {
    //     this.driver = driver;
    //     this.driver = DriverManager.getDriver();
    // }

    @BeforeClass
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("baseUrl"));
        loginPage = new LoginPage(driver);
        microsoftLoginPage = new MicrosoftLoginPage(driver);
        homePage = new HomePage(driver);
    }

    @Test
    public void loginTest() {
        homePage.loadCookies();
        driver.navigate().refresh();

        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("/Phancong02/Account/Login")) {
            System.out.println("Login with cookies test passed. Current URL: " + currentUrl);
        } else {
            loginPage.clickOpenIdConnect();
            microsoftLoginPage.login(username, password);

            currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("Phancong02"), "Login failed or did not redirect to dashboard.");

            homePage.saveCookies();

            System.out.println("Login with username and password test passed. Current URL: " + currentUrl);
        }
    }

    @AfterClass
    public void tearDown() {
        DriverManager.closeDriver();
    }
}