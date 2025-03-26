package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class militaryRank {
    private WebDriver driver;
    private By parentAcademicTitleLink = By.linkText("Thù lao");
    private By learnJaw = By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li[6]/ul/li[1]");
    private By academictitleLink = By
            .xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/ul/li[2]/a");
    private By addRank = By.xpath(
            "/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[2]/button");
    private By dropdown = By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/span[1]");
    private By dropdownOptions = By.xpath("//li[contains(text(), '0012')]"); // Tùy chọn có giá trị "0012"
    private By newXPath = By.xpath("//form/div[2]/input"); // Thêm XPath mới
    

    // private By createAcademictitleButton =
    // By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[2]/button/span");
    // private By academictitleIdField =
    // By.xpath("/html/body/div[3]/div[2]/form/div[1]/input");
    // private By academictitleNameField =
    // By.xpath("/html/body/div[3]/div[2]/form/div[2]/input");
    // private By saveButton =
    // By.xpath("/html/body/div[3]/div[2]/form/div[4]/button[2]");
    // private By successPopup =
    // By.xpath("//div[@id='toast-container']//div[contains(@class,
    // 'toast-message')]");

    // private By searchAcademictitleField =
    // By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[1]/div/label/input");
    // private By updateAcademictitleButton =
    // By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/table/tbody/tr/td[5]/a[1]/i");
    // private By nameAcademictitleField =
    // By.xpath("/html/body/div[3]/div[2]/form/div[2]/input");

    // private By deleteAcademictitleButton =
    // By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/table/tbody/tr[1]/td[5]/a[2]/i");
    // private By confirmDeleteButton =
    // By.xpath("/html/body/div[3]/div/div[6]/button[1]");

    public militaryRank(WebDriver driver) {
        this.driver = driver;
    }

    public void clickParentAcademicTitle() {
        WebElement remuneration = WaitUtils.waitForElement(driver, parentAcademicTitleLink, 5);
        remuneration.click();
    }

    public void clicklearnJaw() {
        WebElement element = WaitUtils.waitForElement(driver, learnJaw, 10);
        element.click();
    }

    public void clickSecondMenuItem() {
        WebElement menuItem = WaitUtils.waitForElement(driver, academictitleLink, 10);
        menuItem.click();
    }

    public void clickaddRank() {
        WebElement button = WaitUtils.waitForElement(driver, addRank, 10);
        button.click();
    }

    public void selectRankOption(String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click vào dropdown để mở danh sách
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdownElement.click();

        // Chờ danh sách xuất hiện
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(text(), '" + optionText + "')]")));

        // Click vào giá trị mong muốn
        option.click();
    }
    public void clickOnNewXPath() {
        WebElement element = WaitUtils.waitForElement(driver, newXPath, 10);

        element.sendKeys("32211");
    }

    

    // public void clickParentAcademicTitle() {
    // WebElement remuneration = WaitUtils.waitForElement(driver,
    // parentAcademicTitleLink, 5);
    // remuneration.click();
    // }

    // public void navigateToAcademicDegreeRank() {
    // WebElement academictitleElement = WaitUtils.waitForElement(driver,
    // academictitleLink, 10);
    // academictitleElement.click();
    // }

    // public void clickCreateAcademicDegreeRank() {
    // WebElement createButton = WaitUtils.waitForElement(driver,
    // createAcademictitleButton, 10);
    // createButton.click();
    // }

    // public void enterAcademicDegreeRankDetails(String id, String name) {
    // WebElement idField = WaitUtils.waitForElement(driver, academictitleIdField,
    // 10);
    // idField.sendKeys(id);

    // WebElement nameField = WaitUtils.waitForElement(driver,
    // academictitleNameField, 10);
    // nameField.sendKeys(name);
    // }

    // public void searchAcademicTitle(String titleId) {
    // WebElement searchField = WaitUtils.waitForElement(driver,
    // searchAcademictitleField, 10);
    // searchField.sendKeys(titleId);
    // }

    // public void clickUpdateAcademicTitleButton() {
    // WebElement updateButton = WaitUtils.waitForElement(driver,
    // updateAcademictitleButton, 10);
    // updateButton.click();
    // }

    // public void updateAcademicTitleName(String newName) {
    // WebElement nameField = WaitUtils.waitForElement(driver,
    // nameAcademictitleField, 10);
    // nameField.clear();
    // nameField.sendKeys(newName);
    // }

    // public void clickSaveButton() {
    // WebElement saveBtn = WaitUtils.waitForElement(driver, saveButton, 10);
    // saveBtn.click();
    // }

    // public void navigateToAcademicTitle() {
    // WebElement academictitleElement = WaitUtils.waitForElement(driver,
    // academictitleLink, 10);
    // academictitleElement.click();
    // }

    // public void clickDeleteAcademicTitleButton() {
    // WebElement deleteButton = WaitUtils.waitForElement(driver,
    // deleteAcademictitleButton, 10);
    // deleteButton.click();
    // }

    // public void confirmDelete() {
    // WebElement confirmButton = WaitUtils.waitForElement(driver,
    // confirmDeleteButton, 10);
    // confirmButton.click();
    // }

    // public boolean isSuccessPopupDisplayed() {
    // WebElement popup = WaitUtils.waitForElement(driver, successPopup, 10);
    // return popup.isDisplayed();
    // }

    // public String getToastMessageText() {
    // try {
    // WebElement toastMessage = driver.findElement(
    // By.xpath("//div[@id='toast-container']//div[contains(@class,
    // 'toast-message')]")
    // );
    // return toastMessage.getText();
    // } catch (Exception e) {
    // return "Không tìm thấy thông báo!";
    // }
    // }
}