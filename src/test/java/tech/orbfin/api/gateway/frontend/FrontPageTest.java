package tech.orbfin.api.gateway.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.Assert;


import java.time.Duration;

public class FrontPageTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        ProfilesIni fireFox = new ProfilesIni();
        FirefoxProfile profile = fireFox.getProfile("Test");

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("geo.prompt.testing", true);
        options.addPreference("geo.prompt.testing.allow", true);
        options.addArguments("-private-window");
        options.setProfile(profile);

        driver = new FirefoxDriver(options);
        driver.get("http://localhost/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testUI() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        WebElement loginButton = driver.findElement(By.id("login_page_btn"));
        WebElement signupButton = driver.findElement(By.id("signup_page_btn"));
        WebElement forgotButton = driver.findElement(By.id("login_page_btn"));

        Assert.assertTrue(loginButton.isDisplayed());
        Assert.assertTrue(signupButton.isDisplayed());
        Assert.assertTrue(forgotButton.isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }
}
