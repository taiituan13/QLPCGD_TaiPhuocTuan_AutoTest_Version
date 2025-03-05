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
import pages.Rankpage;
// import pages.Ranktest;

public class Ranktest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private Rankpage rankPage;

    private MicrosoftLoginPage microsoftLoginPage;
    String username = ConfigReader.getProperty("username");
    String password = ConfigReader.getProperty("password");

    @BeforeClass
    public void setup() {
        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseUrl"));
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        microsoftLoginPage = new MicrosoftLoginPage(driver);
        rankPage = new Rankpage(driver);



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
    public void assignLecturer() throws InterruptedException{
        loginTest();
        rankPage.remunerationClick();


 }

}
