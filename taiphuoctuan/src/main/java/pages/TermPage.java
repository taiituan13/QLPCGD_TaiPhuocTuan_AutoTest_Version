package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import config.ApiClient;
import utils.WaitUtils;

public class TermPage {
    private WebDriver driver;

    public TermPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static class Term {
        private String id;
        private int startYear;
        private int endYear;
        private int maxClass;
        private int maxLesson;
        private String startDate;
        private int startWeek;
        private boolean status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStartYear() {
            return startYear;
        }

        public void setStartYear(int startYear) {
            this.startYear = startYear;
        }

        public int getEndYear() {
            return endYear;
        }

        public void setEndYear(int endYear) {
            this.endYear = endYear;
        }

        public int getMaxClass() {
            return maxClass;
        }

        public void setMaxClass(int maxClass) {
            this.maxClass = maxClass;
        }

        public int getMaxLesson() {
            return maxLesson;
        }

        public void setMaxLesson(int maxLesson) {
            this.maxLesson = maxLesson;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public int getStartWeek() {
            return startWeek;
        }

        public void setStartWeek(int startWeek) {
            this.startWeek = startWeek;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "{id: " + id + ", start_year: " + startYear + ", end_year: " + endYear +
                    ", start_week: " + startWeek + ", start_date: '" + startDate + "', " +
                    "max_class: " + maxClass + ", max_lesson: " + maxLesson + ", status: " + status + "}";
        }
    }

    public void navigateToTermManagement() {
        WebElement majorMenu = WaitUtils.waitForElement(driver, By.linkText("Học kỳ và ngành"), 10);
        majorMenu.click();
    }

    public void navigateToMajorManagement() {
        WebElement majorMenu = WaitUtils.waitForElement(driver, By.linkText("Học kỳ và ngành"), 10);
        majorMenu.click();

        WebElement major = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/ul/li[2]/a"), 10);
        major.click();
    }

    public void clickAddMajorButton() {
        WebElement addMajorButton = WaitUtils.waitForElement(driver, By.xpath(
                "/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[2]/button"),
                10);
        addMajorButton.click();
    }

    public void enterMajorDetails(String id, String name, String abbreviation, String curriculum) {
        WebElement majorId = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div[2]/form/div[1]/input"),
                10);
        majorId.sendKeys(id);

        WebElement majorName = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[3]/div[2]/form/div[2]/input"),
                10);
        majorName.sendKeys(name);

        WebElement majorAbbreviation = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[3]/div[2]/form/div[3]/input"), 10);
        majorAbbreviation.sendKeys(abbreviation);

        WebElement curriculumDropdown = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[3]/div[2]/form/div[4]/div/span/span[1]/span/span[1]"), 10);
        curriculumDropdown.click();
        WebElement selectCurriculum = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[3]/div[2]/form/div[4]/div/span[2]/span/span[2]/ul/li[2]"), 10);
        selectCurriculum.click();
    }

    public void clickSaveButton() {
        WebElement saveButton = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[3]/div[2]/form/div[5]/button[2]"), 10);
        saveButton.click();
    }

    public boolean checkMajorAddedSuccessfully() {
        try {
            WebElement successPopup = WaitUtils.waitForElement(driver,
                    By.xpath("//div[@id='toast-container']//div[contains(@class, 'toast-message')]"), 10);
            return successPopup.isDisplayed();
        } catch (Exception e) {
            // Tìm element thông báo và lấy text của nó
            WebElement failedPopup = WaitUtils.waitForElement(driver,
                    By.xpath("//div[contains(@class,'swal2-html-container')]"), 0);
            String alertText = failedPopup.getText();
            System.out.println("Alert Text: " + alertText);
            return false;
        }
    }

    public WebElement searchMajor(String majorId) {
        WebElement searchMajor = WaitUtils.waitForElement(driver, By.xpath(
                "/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div/div/div[1]/div[2]/div/div[1]/div/label/input"),
                10);
        searchMajor.sendKeys(majorId);

        // Chờ bảng cập nhật và lấy hàng đầu tiên
        WebElement firstRow = WaitUtils.waitForElement(driver, By.xpath("//table/tbody/tr[1]"), 10);
        if (firstRow != null && firstRow.isDisplayed() && !firstRow.getText().trim().equals("Không tìm thấy kết quả")) {
            return firstRow;
        } else {
            // Lấy text thông báo của bảng nếu không tìm thấy hàng nào
            WebElement noResultMessage = driver.findElement(By.xpath("//table/tbody/tr/td"));
            System.out.println("No result message: " + noResultMessage.getText());
            return null;
        }
    }

    public void deleteMajor(String majorId) {
        WebElement firstRow = searchMajor(majorId);
        if (firstRow != null) {
            WebElement deleteMajorButton = firstRow.findElement(By.xpath(".//td[6]/a[2]/i"));
            deleteMajorButton.click();

            WebElement confirmDeleteButton = WaitUtils.waitForElement(driver,
                    By.xpath("/html/body/div[3]/div/div[6]/button[1]"), 10);
            confirmDeleteButton.click();
        } else {
            System.out.println("Major not found, cannot delete.");
        }
    }

    public boolean isMajorDeletedSuccessfully() {
        try {
            WebElement successDeletePopup = WaitUtils.waitForElement(driver,
                    By.xpath("//div[@id='toast-container']//div[contains(@class, 'toast-message')]"), 5);
            return successDeletePopup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<Term> getTermsFromUI() {
        List<Term> terms = new ArrayList<>();
        WaitUtils.waitForElement(driver, By.xpath("//table/tbody/tr"), 10);

        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) { // Đảm bảo có đủ cột dữ liệu
                Term term = new Term();
                term.setId(cells.get(0).getText().trim());
                term.setStartYear(Integer.parseInt(cells.get(1).getText().trim()));
                term.setEndYear(Integer.parseInt(cells.get(2).getText().trim()));
                term.setStartWeek(Integer.parseInt(cells.get(3).getText().trim()));
                term.setStartDate(cells.get(4).getText().trim());
                term.setMaxClass(Integer.parseInt(cells.get(6).getText().trim()));
                term.setMaxLesson(Integer.parseInt(cells.get(5).getText().trim()));
                // Kiểm tra trạng thái của checkbox
                WebElement statusCheckbox = cells.get(7).findElement(By.tagName("input"));
                term.setStatus(statusCheckbox.isSelected());

                terms.add(term);
            }
        }
        return terms;
    }

}