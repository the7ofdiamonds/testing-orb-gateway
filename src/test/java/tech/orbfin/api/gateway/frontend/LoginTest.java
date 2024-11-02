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
import org.testng.annotations.AfterTest;
import org.testng.Assert;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.time.Duration;

public class LoginTest {
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

    @Test(priority = 1, dataProvider = "Auth-Front", dataProviderClass = DataProviders.class)
    public void test(String email, String password){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login_btn")));
        Assert.assertTrue(loginButton.isDisplayed());

        loginButton.click();

        WebElement emailInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        Assert.assertTrue(emailInputField.isDisplayed());

        emailInputField.click();
        emailInputField.sendKeys(email);

        WebElement passwordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        Assert.assertTrue(passwordInputField.isDisplayed());

        passwordInputField.click();
        passwordInputField.sendKeys(password);

        WebElement submitButton = driver.findElement(By.id("login_btn"));
        Assert.assertTrue(submitButton.isDisplayed());

        submitButton.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());

        boolean correctURL = wait.until(ExpectedConditions.urlContains("dashboard"));
        Assert.assertTrue(correctURL);

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_btn")));

        Assert.assertTrue(logoutButton.isDisplayed());

        logoutButton.click();

        boolean frontPage = wait.until(ExpectedConditions.urlContains(""));
        Assert.assertTrue(frontPage);
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }
}
