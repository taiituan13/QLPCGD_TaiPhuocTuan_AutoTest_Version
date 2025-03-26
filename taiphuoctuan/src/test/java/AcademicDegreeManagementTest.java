import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import config.ConfigReader;
import config.DriverManager;
import pages.AcademicDegreePage;
import pages.HomePage;
import pages.LoginPage;
import pages.MicrosoftLoginPage;

public class AcademicDegreeManagementTest {
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

    @Test(priority = 1)
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

    @DataProvider(name = "AcademicDegreeRankProvider")
    public Object[][] AcademicDegreeRankProvider() {
        return new Object[][] {
                // { "001222", "Curriculum" },
                // { "001223", "CNTT" },
                { "001223", "" },
                { "", "hdhdhd"}
        };
    }

    @Test(dataProvider = "AcademicDegreeRankProvider")
    public void testCreateAcademicDegreeRank(String id, String name) {
        loginTest();
        academicDegreeRank.navigateToAcademicDegreeRankTab();
        academicDegreeRank.navigateToAcademicDegreeRank();
        academicDegreeRank.clickCreateAcademicDegreeRank();
        academicDegreeRank.enterAcademicDegreeRankDetails(id, name);
        academicDegreeRank.clickSaveButton();
        List<WebElement> errorElements = driver.findElements(By.className("error"));

        if (errorElements.isEmpty()) {
            if (academicDegreeRank.isSuccessPopupDisplayed()) {
                System.out.println("Thêm thành công. : " + academicDegreeRank.getToastMessageText());
            } else {
                System.out.println("Thêm thất bại.");
            }

        } else {
            for (WebElement errorElement : errorElements) {
                System.out.println("error: " + errorElement.getText());
            }
        }
        System.out.println("========================================");
    }

    @Test(priority = 3)
    public void testUpdateAcademicTitle() {
        loginTest();
        academicDegreeRank.navigateToAcademicDegreeRankTab();
        academicDegreeRank.navigateToAcademicDegreeRank();
        academicDegreeRank.clickaddAcademicTitle();
        // academicDegreeRank.deleteAcademicTitle("012");
        // academicDegreeRank.clickUpdateAcademicTitleButton();
        academicDegreeRank.updateAcademicTitleName("CSfdDT");
        academicDegreeRank.clickSaveButton();
        if (academicDegreeRank.isSuccessPopupDisplayed()) {
            System.out.println("Cập nhật thành công : " + academicDegreeRank.getToastMessageText());
        } else {
            System.out.println("Cập nhật không thành công.");
        }
    }

    @Test(priority = 4)
    public void testDeleteAcademicTitle() {
        loginTest();
        academicDegreeRank.navigateToAcademicDegreeRankTab();
        academicDegreeRank.navigateToAcademicTitle();
        academicDegreeRank.deleteAcademicTitle("0112");
        // academicDegreeRank.clickDeleteAcademicTitleButton();
        // academicDegreeRank.confirmDelete();
        if (academicDegreeRank.isSuccessPopupDisplayed()) {
            System.out.println("Xóa thành công. : " + academicDegreeRank.getToastMessageText());
        } else {
            System.out.println("Xóa không thành công.");
        }
    }

    @AfterClass
    public void tearDown() {
        DriverManager.closeDriver();
    }
}