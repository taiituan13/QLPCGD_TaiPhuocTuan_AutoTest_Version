package pages;

import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import java.lang.Thread;

import utils.WaitUtils;

public class AcademicDegreePage {

    private WebDriver driver;

    public AcademicDegreePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void remunerationClick() {
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement remuneration = WaitUtils.waitForElement(driver,
                By.linkText("Thù lao"), 5);
        remuneration.click();

        WebElement academicDegree = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li[6]/ul/li[1]/a"), 5);
        academicDegree.click();
    }

    public void addAcaddemicTitles(String maHocHam, String tenHocHam) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[2]/button")));
        button.click();

        WebElement inputMa = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@placeholder='Nhập mã học hàm, học vị']")));
        inputMa.sendKeys(maHocHam);
        WebElement inputTen = driver.findElement(By.xpath("//input[@placeholder='Nhập tên học hàm, học vị']"));
        inputTen.sendKeys(tenHocHam);
        WebElement buttonLuu = driver.findElement(By.xpath("//button[text()='Lưu']"));
        buttonLuu.click();



    }

    public boolean isToastMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='toast-container']//div[contains(@class, 'toast-message')]")
            ));
            return toastMessage.isDisplayed();
        } catch (Exception e) {
            return false;  // Trả về false nếu phần tử không tồn tại hoặc không hiển thị
        }
    }

    public String getToastMessageText() {
        try {
            WebElement toastMessage = driver.findElement(
                By.xpath("//div[@id='toast-container']//div[contains(@class, 'toast-message')]")
            );
            return toastMessage.getText();
        } catch (Exception e) {
            return "Không tìm thấy thông báo!";
        }
    }
    public void search(String searchText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    
        // Chờ cho thanh tìm kiếm hiển thị
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[1]/div/label/input")));
    
        // Nhập nội dung tìm kiếm
        searchInput.sendKeys(searchText);
        
    }



    
}

// WebElement adAcaddemicTitles = WaitUtils.waitForElement(driver,
// By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[2]/button"),
// 5);
// addAcademicTitles.click();

// remuneration.click();`