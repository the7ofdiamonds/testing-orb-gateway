package tech.orbfin.api.gateway.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;
import org.testng.Assert;

import java.time.Duration;

public class FrontPageTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("geo.prompt.testing", true);
        options.addPreference("geo.prompt.testing.allow", true);
        options.addArguments("-private-window");

        driver = new FirefoxDriver(options);
        driver.get("http://localhost/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Test
    public void testUI() {
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        WebElement loginButton = driver.findElement(By.id("login_btn"));
        WebElement signupButton = driver.findElement(By.id("signup_btn"));
        WebElement forgotButton = driver.findElement(By.id("login_btn"));

        Assert.assertTrue(loginButton.isDisplayed());
        Assert.assertTrue(signupButton.isDisplayed());
        Assert.assertTrue(forgotButton.isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
