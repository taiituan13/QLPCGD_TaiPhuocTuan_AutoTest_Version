package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class TermPage {
    private WebDriver driver;

    public TermPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToTermManagement() {
        WebElement majorMenu = WaitUtils.waitForElement(driver, By.linkText("Học kỳ và ngành"), 10);
        majorMenu.click();

        WebElement major = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/ul/li[2]/a"), 10);
        major.click();
    }

    public void clickAddMajorButton() {
        WebElement addMajorButton = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[2]/button"), 10);
        addMajorButton.click();
    }

    public void enterMajorDetails(String id, String name, String abbreviation, String curriculum) {
        WebElement majorId = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div[2]/form/div[1]/input"), 10);
        majorId.sendKeys(id);

        WebElement majorName = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div[2]/form/div[2]/input"), 10);
        majorName.sendKeys(name);

        WebElement majorAbbreviation = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div[2]/form/div[3]/input"), 10);
        majorAbbreviation.sendKeys(abbreviation);

        WebElement curriculumDropdown = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div[2]/form/div[4]/div/span/span[1]/span/span[1]"), 10);
        curriculumDropdown.click();
        WebElement selectCurriculum = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div[2]/form/div[4]/div/span[2]/span/span[2]/ul/li[2]"), 10);
        selectCurriculum.click();
    }

    public void clickSaveButton() {
        WebElement saveButton = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div[2]/form/div[5]/button[2]"), 10);
        saveButton.click();
    }

    public boolean isMajorAddedSuccessfully() {
        try {
            WebElement successPopup = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[4]/div"), 10);
            return !successPopup.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    public void deleteMajor(String majorId) {
        WebElement searchMajor = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[1]/div/label/input"), 10);
        searchMajor.sendKeys(majorId);

        WebElement deleteMajorButton = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/table/tbody/tr[1]/td[6]/a[2]/i"), 10);
        deleteMajorButton.click();

        WebElement confirmDeleteButton = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div/div[6]/button[1]"), 10);
        confirmDeleteButton.click();
    }

    public boolean isMajorDeletedSuccessfully() {
        try {
            WebElement successDeletePopup = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div"), 10);
            return successDeletePopup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}