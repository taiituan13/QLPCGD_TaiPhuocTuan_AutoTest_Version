package pages;

import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.lang.Thread;

import utils.WaitUtils;

public class TimeTablePage {
    private WebDriver driver;

    public TimeTablePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void TimeTableTabClick() {
        WebElement timeTableTab = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li[4]/a"), 2);
        timeTableTab.click();
        WebElement assign = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li[4]/ul/li[2]"), 2);
        assign.click();

    }

    public void theoryAssign() throws InterruptedException {
        WebElement theorySession = WaitUtils.waitForElement(driver, By.id("480682"), 15);
        theorySession.click();
        Thread.sleep(500);
        theorySession.click();
        Thread.sleep(500);
        theorySession.click();

    }
    public void assignLecturer() {
        WebElement lecturerselect = WaitUtils.waitForElement(driver,
                By.xpath("/html/body/div[3]/div[2]/div/div/span[1]/span[1]"), 15);
                lecturerselect.click();
       
    }

}
