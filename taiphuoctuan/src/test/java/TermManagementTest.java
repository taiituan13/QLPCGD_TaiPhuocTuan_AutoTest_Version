import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.ConfigReader;
import config.DriverManager;
import pages.TermPage;
import pages.MicrosoftLoginPage;
import pages.HomePage;
import pages.LoginPage;

public class TermManagementTest {
    private WebDriver driver;
    private TermPage termPage;
    private HomePage homePage;
    private LoginPage loginPage;
    private MicrosoftLoginPage microsoftLoginPage;
    String username = ConfigReader.getProperty("username");
    String password = ConfigReader.getProperty("password");

    @BeforeClass
    public void setup() {
        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseUrl"));
        termPage = new TermPage(driver);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        microsoftLoginPage = new MicrosoftLoginPage(driver);
    }

    @Test
    public void loginTest() {
        homePage.loadCookies();
        driver.navigate().refresh();

        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("/Phancong02/Account/Login")) {
            System.out.println("Login with cookies test passed. Current URL: " + currentUrl);
        } else {
            loginPage.openIdConnect();
            microsoftLoginPage.login(username, password);

            currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("Phancong02"), "Login failed or did not redirect to dashboard.");

            homePage.saveCookies();

            System.out.println("Login with username and password test passed. Current URL: " + currentUrl);
        }
    }

    @Test
    public void addMajorTest() {
        loginTest();
        termPage.navigateToTermManagement();
        termPage.clickAddMajorButton();
        termPage.enterMajorDetails("001222", "CNCdSATT", "CSfdDT", "Curriculum");
        termPage.clickSaveButton();

        Assert.assertTrue(termPage.isMajorAddedSuccessfully(), "Failed to add major.");
        System.out.println("Add major test passed.");
    }

    @Test
    public void deleteMajorTest() {
        loginTest();

        termPage.navigateToTermManagement();
        termPage.deleteMajor("001222");

        Assert.assertTrue(termPage.isMajorDeletedSuccessfully(), "Failed to delete major.");
        System.out.println("Delete major test passed.");
    }

    @AfterClass
    public void tearDown() {
        DriverManager.closeDriver();
    }
}