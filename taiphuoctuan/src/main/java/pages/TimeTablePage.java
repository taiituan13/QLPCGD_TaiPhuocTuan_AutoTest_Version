package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import utils.WaitUtils;

public class TimeTablePage {
        private WebDriver driver;

    public TimeTablePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void theoryAssign(){
        WebElement theorySession  =  WaitUtils.waitForElement(driver, By.id("480682"), 2);
        theorySession.click();
        theorySession.click();
        theorySession.click();
    }
}
