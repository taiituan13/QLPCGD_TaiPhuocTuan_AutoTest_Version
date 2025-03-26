import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import config.ApiClient;
import config.ApiClient.Term;
import config.ConfigReader;
import config.DriverManager;
import pages.TermPage;
import utils.ExcelUtils;
import utils.WaitUtils;
import pages.MicrosoftLoginPage;
import pages.HomePage;
import pages.LoginPage;

public class TermManagementTest {
    private WebDriver driver;
    private WebDriver driver2;
    private TermPage termPage;
    private HomePage homePage;
    private LoginPage loginPage;
    private Authentication authentication;
    private TermPage termPage2;
    private HomePage homePage2;
    private LoginPage loginPage2;
    private Authentication authentication2;
    private MicrosoftLoginPage microsoftLoginPage;
    String username = ConfigReader.getProperty("username");
    String password = ConfigReader.getProperty("password");

    private boolean compareTerms(List<TermPage.Term> termsUI, List<ApiClient.Term> termsAPI) {
        if (termsUI.size() != termsAPI.size()) {
            System.out.println("\n⚠️ Số lượng học kỳ không khớp! UI: " + termsUI.size() + " | API: " + termsAPI.size());
        }

        for (int i = 0; i < Math.min(termsUI.size(), termsAPI.size()); i++) {
            TermPage.Term uiTerm = termsUI.get(i);
            ApiClient.Term apiTerm = termsAPI.get(i);

            if (!uiTerm.getId().equals(apiTerm.getId()) ||
                    uiTerm.getStartYear() != apiTerm.getStartYear() ||
                    uiTerm.getEndYear() != apiTerm.getEndYear() ||
                    uiTerm.getStartWeek() != apiTerm.getStartWeek() ||
                    // !uiTerm.getStartDate().equals(apiTerm.getStartDate()) ||
                    uiTerm.getMaxClass() != apiTerm.getMaxClass() ||
                    uiTerm.getMaxLesson() != apiTerm.getMaxLesson() ||
                    uiTerm.isStatus() != apiTerm.isStatus()) {

                System.out.println("\n❌ Không khớp tại vị trí " + i);
                System.out.println("🔹 UI:  " + uiTerm);
                System.out.println("🔹 API: " + apiTerm);

                System.out.println("📌 Chi tiết khác biệt:");
                System.out.println("ID: " + uiTerm.getId() + " vs " + apiTerm.getId());
                System.out.println("StartYear: " + uiTerm.getStartYear() + " vs " + apiTerm.getStartYear());
                System.out.println("EndYear: " + uiTerm.getEndYear() + " vs " + apiTerm.getEndYear());
                System.out.println("StartWeek: " + uiTerm.getStartWeek() + " vs " + apiTerm.getStartWeek());
                System.out.println("MaxClass: " + uiTerm.getMaxClass() + " vs " + apiTerm.getMaxClass());
                System.out.println("MaxLesson: " + uiTerm.getMaxLesson() + " vs " + apiTerm.getMaxLesson());
                System.out.println("Status: " + uiTerm.isStatus() + " vs " + apiTerm.isStatus());
                return false;
            }
        }
        return true;
    }

    @BeforeClass
    public void setup() {
        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseUrl"));
        termPage = new TermPage(driver);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        microsoftLoginPage = new MicrosoftLoginPage(driver);
        authentication = new Authentication(driver);
        authentication.setup();

        driver2 = DriverManager.getDriver();
        driver2.manage().window().maximize();
        driver2.get(ConfigReader.getProperty("baseUrl"));
        termPage2 = new TermPage(driver2);
        homePage2 = new HomePage(driver2);
        loginPage2 = new LoginPage(driver2);
        microsoftLoginPage = new MicrosoftLoginPage(driver2);
        authentication2 = new Authentication(driver2);
        authentication2.setup();
    }

    @DataProvider(name = "majorDataProvider")
    public Object[][] majorDataProvider() {
        return new Object[][] {
                { "001222", "CNCdSATT", "CSfdDT", "Curriculum" },
                { "001223", "CNTT", "IT", "Curriculum 2" },
                { "001224", "Kinh tế", "KT", "Curriculum 3" },
                // Thêm các bộ dữ liệu không hợp lệ
                { "", "CNCdSATT", "CSfdDT", "Curriculum" }, // ID trống
                { "001225", "", "CSfdDT", "Curriculum" }, // Tên trống
                { "001226", "CNTT", "", "Curriculum" }, // Viết tắt trống
                { "001227", "Kinh tế", "KT", "" }, // Curriculum trống
        };
    }

    @DataProvider(name = "majorDataProviderExcel")
    public Object[][] majorDataProviderExcel() {
        Object[][] data = ExcelUtils.getExcelData(
                "/home/tuantai/Desktop/IT/courses/QLPCGD_TaiPhuocTuan_AutoTest/taiphuoctuan/src/resources/Test.xlsx",
                "TestData");
        System.out.println("📌 Dữ liệu từ Excel:");
        for (Object[] row : data) {
            System.out.println(java.util.Arrays.toString(row));
        }
        return data;
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
            System.out.println(username);
            microsoftLoginPage.login(username, password);

            currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("Phancong02"), "Login failed or did not redirect to dashboard.");

            homePage.saveCookies();

            System.out.println("Login with username and password test passed. Current URL: " + currentUrl);
        }
    }

    @Test(dataProvider = "majorDataProviderExcel")
    public void addMajorTest(String id, String name, String abbreviation, String curriculum) {
        authentication.loginTest();
        termPage.navigateToMajorManagement();
        termPage.clickAddMajorButton();
        termPage.enterMajorDetails(id, name, abbreviation, curriculum);
        termPage.clickSaveButton();
        // Kiểm tra xem có bất kỳ phần tử nào có class "error"
        List<WebElement> errorElements = driver.findElements(By.className("error"));
        if (!errorElements.isEmpty()) {
            System.out.println(
                    "Add major test failed for: " + id + ", " + name + ", " + abbreviation + ", " + curriculum);
            // In ra các lỗi
            for (WebElement errorElement : errorElements) {
                System.out.println("Error: " + errorElement.getText());
            }
        } else {
            // Nếu không có lỗi, tiếp tục kiểm tra checkMajorAddedSuccessfully
            if (termPage.checkMajorAddedSuccessfully()) {
                System.out.println(
                        "Add major test passed for: " + id + ", " + name + ", " + abbreviation + ", " + curriculum);
            } else {
                System.out.println(
                        "Add major test failed for: " + id + ", " + name + ", " + abbreviation + ", " + curriculum);
            }
        }
        System.out.println("========================================");
    }

    public void createMajor(String id, String name, String abbreviation, String curriculum) {
        termPage.navigateToMajorManagement();
        termPage.clickAddMajorButton();
        termPage.enterMajorDetails(id, name, abbreviation, curriculum);
        termPage.clickSaveButton();

        // Kiểm tra xem có bất kỳ phần tử nào có class "error"
        List<WebElement> errorElements = driver.findElements(By.className("error"));
        if (!errorElements.isEmpty()) {
            System.out.println(
                    "Add major test failed for: " + id + ", " + name + ", " + abbreviation + ", " + curriculum);
            // In ra các lỗi
            for (WebElement errorElement : errorElements) {
                System.out.println("Error: " + errorElement.getText());
            }
        } else {
            // Nếu không có lỗi, tiếp tục kiểm tra checkMajorAddedSuccessfully
            if (termPage.checkMajorAddedSuccessfully()) {
                System.out.println(
                        "Add major test passed for: " + id + ", " + name + ", " + abbreviation + ", " + curriculum);
            } else {
                System.out.println(
                        "Add major test failed for: " + id + ", " + name + ", " + abbreviation + ", " + curriculum);
            }
        }
        System.out.println("========================================");
    }

    @Test
    public void testConflictDataCreation() {
        Thread user1 = new Thread(() -> {
            authentication.loginTest();

            termPage.navigateToMajorManagement();
            termPage.clickAddMajorButton();
            termPage.enterMajorDetails("001229", "Conflict Major 1", "CM1", "Curriculum 1");
            termPage.clickSaveButton();

            driver.quit();

        });

        Thread user2 = new Thread(() -> {
            authentication2.loginTest();

            termPage2.navigateToMajorManagement();
            termPage2.clickAddMajorButton();
            termPage2.enterMajorDetails("001229", "Conflict Major 2", "CM2", "Curriculum 2");
            termPage2.clickSaveButton();

            driver2.quit();
        });

        user1.start();
        user2.start();

        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteMajorTest() {
        authentication.loginTest();

        termPage.navigateToMajorManagement();
        termPage.deleteMajor("001229");
        if (termPage.isMajorDeletedSuccessfully()) {
            System.out.println("delete major test passed.");
        } else
            System.out.println("delete test Failures.");
    }

    @Test
    public void getTermDataFromUI() {
        ApiClient apiClient = new ApiClient();
        authentication.loginTest();

        termPage.navigateToTermManagement();

        WaitUtils.waitForElement(driver, By.xpath("//table/tbody/tr[not(contains(., 'Đang tải'))]"), 10);
        List<TermPage.Term> termsUI = termPage.getTermsFromUI();
        System.out.println("\n📌 Danh sách học kỳ từ giao diện:");
        termsUI.forEach(System.out::println);

        try {
            List<ApiClient.Term> termsAPI = apiClient.getTermData();
            List<ApiClient.Term> first10API = termsAPI.stream().limit(10).toList();
            System.out.println("\n📌 Danh sách học kỳ từ API:");
            first10API.forEach(System.out::println);

            // So sánh danh sách UI và API
            if (compareTerms(termsUI, first10API)) {

            }
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Lỗi khi lấy danh sách học kỳ từ API: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Không thể lấy danh sách học kỳ từ API.");
        }
    }

    @AfterClass
    public void tearDown() {
        DriverManager.closeDriver();
    }
}