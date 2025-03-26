import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import config.ConfigReader;
import config.DriverManager;
import pages.militaryRank;
import pages.HomePage;
import pages.LoginPage;
import pages.MicrosoftLoginPage;

public class MilitaryRankTest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private MicrosoftLoginPage microsoftLoginPage;
    private militaryRank militaryRankPage;
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
        militaryRankPage = new militaryRank(driver);

        // Đăng nhập một lần trước khi chạy test
        login();
    }

    public void login() {
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

    @Test(priority = 1)
    public void testNavigateToMilitaryRank() {
        militaryRankPage.clickParentAcademicTitle();
        militaryRankPage.clickSecondMenuItem();
        System.out.println("Navigated to Military Rank page successfully.");
    }

    @Test(priority = 2)
    public void testCreateMilitaryRank() {
        militaryRankPage.clickParentAcademicTitle();
        militaryRankPage.clickSecondMenuItem();
        // Mở lại code tạo rank nếu có
        // militaryRankPage.clickCreateAcademicDegreeRank();
        // militaryRankPage.enterAcademicDegreeRankDetails("00122", "CNCdSATT");
        // militaryRankPage.clickSaveButton();

        // Kiểm tra thông báo hiển thị
        // if (militaryRankPage.isSuccessPopupDisplayed()) {
        // System.out.println("Thông báo hiển thị: " +
        // militaryRankPage.getToastMessageText());
        // } else {
        // System.out.println("Thông báo không hiển thị.");
        // }
    }

    @Test(priority = 3)
    public void testUpdateMilitaryRank() {
        militaryRankPage.clickParentAcademicTitle();
        militaryRankPage.clicklearnJaw();
        militaryRankPage.clickSecondMenuItem();
        militaryRankPage.clickaddRank();
        // Mở lại code update rank nếu có
        // militaryRankPage.clickUpdateAcademicTitleButton();
        // militaryRankPage.updateAcademicTitleName("CSfdDT");
        // militaryRankPage.clickSaveButton();

        // Kiểm tra thông báo hiển thị
        // if (militaryRankPage.isSuccessPopupDisplayed()) {
        // System.out.println("Thông báo hiển thị: " +
        // militaryRankPage.getToastMessageText());
        // } else {
        // System.out.println("Thông báo không hiển thị.");
        // }
    }

    @Test(priority = 4)
    public void testSelectMilitaryRankOption() {
        militaryRankPage.clickParentAcademicTitle();
        militaryRankPage.clickSecondMenuItem();
        militaryRankPage.clickaddRank();

        // Chọn giá trị "0012" trong dropdown
        militaryRankPage.selectRankOption("0012");
        militaryRankPage.clickOnNewXPath();
        System.out.println("Successfully selected military rank option '0012'.");
    }


    @Test(priority = 6)
    public void testDeleteMilitaryRank() {
        militaryRankPage.clickParentAcademicTitle();
        militaryRankPage.clickSecondMenuItem();
        // Mở lại code xóa rank nếu có
        // militaryRankPage.clickDeleteAcademicTitleButton();
        // militaryRankPage.confirmDelete();

        // Kiểm tra thông báo hiển thị
        // if (militaryRankPage.isSuccessPopupDisplayed()) {
        // System.out.println("Thông báo hiển thị: " +
        // militaryRankPage.getToastMessageText());
        // } else {
        // System.out.println("Thông báo không hiển thị.");
        // }
    }

    // @AfterClass
    // public void tearDown() {
    // DriverManager.closeDriver();
    // }
}
