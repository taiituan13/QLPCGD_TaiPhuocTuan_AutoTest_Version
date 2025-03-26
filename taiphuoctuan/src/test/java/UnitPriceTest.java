import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.ConfigReader;
import config.DriverManager;
import pages.PrinceCoefficientPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MicrosoftLoginPage;

public class UnitPriceTest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private MicrosoftLoginPage microsoftLoginPage;
    private PrinceCoefficientPage princeCoefficientPage;
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
        princeCoefficientPage = new PrinceCoefficientPage(driver);
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

    @Test()
    public void testEditUnitPrice() {
        loginTest();
        princeCoefficientPage.clickThuTab();
        princeCoefficientPage.clickDonGiaHeSoTab();
        princeCoefficientPage.clickChinhButton();
        
        String newPrice = "100000";
        princeCoefficientPage.enterDonGia(newPrice);
        princeCoefficientPage.clickSaveButton();
        
        System.out.println("Successfully updated unit price to: " + newPrice);
    }

    @AfterClass
    public void tearDown() {
        DriverManager.closeDriver();
    }
}
