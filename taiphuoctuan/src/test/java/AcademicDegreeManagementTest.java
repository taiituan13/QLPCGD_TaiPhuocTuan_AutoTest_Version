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
    private AcademicDegreePage AcademicDegreePage;
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
        AcademicDegreePage = new AcademicDegreePage(driver);
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
                { "001222", "Curriculum" },
                { "0012444", "CNTT" },
                { "0012", "" },
                { "", "hdhdhd" }
        };
    }

    // Kiểm thử tạo học hàm, học vị.
    @Test(dataProvider = "AcademicDegreeRankProvider")
    public void testCreateAcademicDegreeRank(String id, String name) {
        loginTest();
        AcademicDegreePage.navigateToAcademicDegreeRankTab();
        AcademicDegreePage.navigateToAcademicDegreeRank();
        AcademicDegreePage.clickCreateAcademicDegreeRank();
        AcademicDegreePage.enterAcademicDegreeRankDetails(id, name);
        AcademicDegreePage.clickSaveButton();
        List<WebElement> errorElements = driver.findElements(By.className("error"));

        if (errorElements.isEmpty()) {
            if (AcademicDegreePage.isSuccessPopupDisplayed()) {
                System.out.println("Thêm thành công. : " + AcademicDegreePage.getToastMessageText());
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

    // Kiểm thử cập nhật học hàm, học vị.
    @Test(dataProvider = "AcademicDegreeRankProvider")
    public void testUpdateAcademicTitle(String id, String name) {
        loginTest();
        AcademicDegreePage.navigateToAcademicDegreeRankTab();
        AcademicDegreePage.navigateToAcademicDegreeRank();
        AcademicDegreePage.clickaddAcademicTitle();
        // academicDegreeRank.deleteAcademicTitle("012");
        // academicDegreeRank.clickUpdateAcademicTitleButton();
        AcademicDegreePage.updateAcademicTitleName(id);
        AcademicDegreePage.clickSaveButton();
        List<WebElement> errorElements = driver.findElements(By.className("error"));
        if (errorElements.isEmpty()) {
            if (AcademicDegreePage.isSuccessPopupDisplayed()) {
                System.out.println(" thành công. : " + AcademicDegreePage.getToastMessageText());
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

    // Kiểm thử xóa học hàm, học vị.
    @Test(dataProvider = "AcademicDegreeRankProvider")
    public void testDeleteAcademicTitle(String id, String name) {
        loginTest();
        AcademicDegreePage.navigateToAcademicDegreeRankTab();
        AcademicDegreePage.navigateToAcademicTitle();
        AcademicDegreePage.deleteAcademicTitle(id);
        if (AcademicDegreePage.searchAcademictitle(id) != null) {
            AcademicDegreePage.clickDeleteAcademicTitleButton();
            AcademicDegreePage.confirmDelete();
            List<WebElement> errorElements = driver.findElements(By.className("error"));
            if (errorElements.isEmpty()) {
                if (AcademicDegreePage.isSuccessPopupDisplayed()) {
                    System.out.println(" thành công. : " + AcademicDegreePage.getToastMessageText());
                } else {
                    System.out.println("Thêm thất bại.");
                }

            } else {
                for (WebElement errorElement : errorElements) {
                    System.out.println("error: " + errorElement.getText());
                }
            }
        }

        System.out.println("========================================");
    }

    @AfterClass
    public void tearDown() {
        DriverManager.closeDriver();
    }
}