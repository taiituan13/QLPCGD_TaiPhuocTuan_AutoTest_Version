package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class AcademicDegreePage {
    private WebDriver driver;

    private By academictitleLink = By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li[6]/ul/li[1]/a");
    private By createAcademictitleButton = By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[2]/button/span");
    private By academictitleIdField = By.xpath("/html/body/div[3]/div[2]/form/div[1]/input");
    private By academictitleNameField = By.xpath("/html/body/div[3]/div[2]/form/div[2]/input");
    private By saveButton = By.xpath("/html/body/div[3]/div[2]/form/div[4]/button[2]");
    private By successPopup = By.xpath("//div[@id='toast-container']//div[contains(@class, 'toast-message')]");

    private By searchAcademictitleField = By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[1]/div/label/input");
    private By updateAcademictitleButton = By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/table/tbody/tr/td[5]/a[1]/i");
    private By nameAcademictitleField = By.xpath("/html/body/div[3]/div[2]/form/div[2]/input");

    private By deleteAcademictitleButton = By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/table/tbody/tr[1]/td[5]/a[2]/i");
    private By confirmDeleteButton = By.xpath("/html/body/div[3]/div/div[6]/button[1]");


    public AcademicDegreePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToAcademicDegreeRank() {
        WebElement academictitleElement = WaitUtils.waitForElement(driver, academictitleLink, 10);
        academictitleElement.click();
    }

    public void clickCreateAcademicDegreeRank() {
        WebElement createButton = WaitUtils.waitForElement(driver, createAcademictitleButton, 10);
        createButton.click();
    }

    public void enterAcademicDegreeRankDetails(String id, String name) {
        WebElement idField = WaitUtils.waitForElement(driver, academictitleIdField, 10);
        idField.sendKeys(id);

        WebElement nameField = WaitUtils.waitForElement(driver, academictitleNameField, 10);
        nameField.sendKeys(name);
    }

    public void searchAcademicTitle(String titleId) {
        WebElement searchField = WaitUtils.waitForElement(driver, searchAcademictitleField, 10);
        searchField.sendKeys(titleId);
    }

    public void clickUpdateAcademicTitleButton() {
        WebElement updateButton = WaitUtils.waitForElement(driver, updateAcademictitleButton, 10);
        updateButton.click();
    }

    public void updateAcademicTitleName(String newName) {
        WebElement nameField = WaitUtils.waitForElement(driver, nameAcademictitleField, 10);
        nameField.clear();
        nameField.sendKeys(newName);
    }


    public void clickSaveButton() {
        WebElement saveBtn = WaitUtils.waitForElement(driver, saveButton, 10);
        saveBtn.click();
    }

    public void navigateToAcademicTitle() {
        WebElement academictitleElement = WaitUtils.waitForElement(driver, academictitleLink, 10);
        academictitleElement.click();
    }


    public void clickDeleteAcademicTitleButton() {
        WebElement deleteButton = WaitUtils.waitForElement(driver, deleteAcademictitleButton, 10);
        deleteButton.click();
    }

    public void confirmDelete() {
        WebElement confirmButton = WaitUtils.waitForElement(driver, confirmDeleteButton, 10);
        confirmButton.click();
    }

    public boolean isSuccessPopupDisplayed() {
        WebElement popup = WaitUtils.waitForElement(driver, successPopup, 10);
        return popup.isDisplayed();
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
}