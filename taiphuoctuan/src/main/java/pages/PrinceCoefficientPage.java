package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;
public class PrinceCoefficientPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By thuTab = By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li[6]/a/span");
    private By donGiaHeSoTab = By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li[6]/ul/li[2]/a/span");
    private By chinhButton = By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div[2]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[1]/td[3]/a");
    private By donGiaInput = By.xpath("/html/body/div[3]/div[2]/form/div[2]/input");
    private By saveButton = By.xpath("/html/body/div[3]/div[2]/form/div[3]/button[2]");

    public PrinceCoefficientPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void clickThuTab() {
        wait.until(ExpectedConditions.elementToBeClickable(thuTab)).click();
    }

    public void clickDonGiaHeSoTab() {
        wait.until(ExpectedConditions.elementToBeClickable(donGiaHeSoTab)).click();
    }

    public void clickChinhButton() {
        wait.until(ExpectedConditions.elementToBeClickable(chinhButton)).click();
    }

    public void enterDonGia(String donGia) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(donGiaInput));
        input.clear();
        input.sendKeys(donGia);
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
}
