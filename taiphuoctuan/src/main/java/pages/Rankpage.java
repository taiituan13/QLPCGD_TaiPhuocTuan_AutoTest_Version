package pages;

import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.lang.Thread;

import utils.WaitUtils;

public class Rankpage {

    private WebDriver driver;

    public Rankpage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void remunerationClick() {
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // WebElement RemunerationMenu =
        // wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Thù lao")));
        // RemunerationMenu.click();

        WebElement remuneration = WaitUtils.waitForElement(driver,
                By.linkText("Thù lao"), 5);
        remuneration.click();

        WebElement academicDegree = WaitUtils.waitForElement(driver, By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li[6]/ul/li[1]/a"), 5);
        academicDegree.click();
        
        
        // remuneration.click();`
    }

}
