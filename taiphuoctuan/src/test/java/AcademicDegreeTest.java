import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.ConfigReader;
import config.DriverManager;
import pages.AcademicDegreePage;
import pages.HomePage;
import pages.LoginPage;
import pages.MicrosoftLoginPage;

public class AcademicDegreeTest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private MicrosoftLoginPage microsoftLoginPage;
    private AcademicDegreePage academicDegreeRank;
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
        academicDegreeRank = new AcademicDegreePage(driver);
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
    public void testCreateAcademicDegreeRank() {
        loginTest();
        academicDegreeRank.navigateToAcademicDegreeRank();
        academicDegreeRank.clickCreateAcademicDegreeRank();
        academicDegreeRank.enterAcademicDegreeRankDetails("00122", "CNCdSATT");
        academicDegreeRank.clickSaveButton();

        if (academicDegreeRank.isSuccessPopupDisplayed()) {
            System.out.println("Thông báo hiển thị: " + academicDegreeRank.getToastMessageText());
        } else {
            System.out.println("Thông báo không hiển thị.");
        }
    }

    @Test
    public void testUpdateAcademicTitle() {
        loginTest();

        academicDegreeRank.navigateToAcademicDegreeRank();
        academicDegreeRank.searchAcademicTitle("0012");
        academicDegreeRank.clickUpdateAcademicTitleButton();
        academicDegreeRank.updateAcademicTitleName("CSfdDT");
        academicDegreeRank.clickSaveButton();

        if (academicDegreeRank.isSuccessPopupDisplayed()) {
            System.out.println("Thông báo hiển thị: " + academicDegreeRank.getToastMessageText());
        } else {
            System.out.println("Thông báo không hiển thị.");
        }
    }

    @Test
    public void testDeleteAcademicTitle() {
        loginTest();
        academicDegreeRank.navigateToAcademicTitle();
        academicDegreeRank.searchAcademicTitle("0012");
        academicDegreeRank.clickDeleteAcademicTitleButton();
        academicDegreeRank.confirmDelete();

        if (academicDegreeRank.isSuccessPopupDisplayed()) {
            System.out.println("Thông báo hiển thị: " + academicDegreeRank.getToastMessageText());
        } else {
            System.out.println("Thông báo không hiển thị.");
        }
    }

    @AfterClass
    public void tearDown() {
        DriverManager.closeDriver();
    }
}