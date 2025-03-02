
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

    // @Test(dependsOnMethods = {"loginTest"})
    // public void testLogout() {
    // // Thực hiện hành động đăng xuất
    // homePage.userNavClick();
    // homePage.logOutClick();
    // driver.navigate().refresh();

    // // Kiểm tra URL hiện tại để xác nhận đăng xuất thành công
    // String currentUrl = driver.getCurrentUrl();
    // Assert.assertTrue(currentUrl.contains("/Phancong02/Account/Login"), "Logout
    // failed or did not redirect to login page.");
    // System.out.println("Logout test passed. Current URL: " + currentUrl);
    // }

    @AfterClass
    public void tearDown() {
        // DriverManager.closeDriver();
    }
}