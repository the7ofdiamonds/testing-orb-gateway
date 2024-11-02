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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.time.Duration;

public class LogoutTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ProfilesIni fireFox = new ProfilesIni();
        FirefoxProfile profile = fireFox.getProfile("Test");

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("geo.prompt.testing", true);
        options.addPreference("geo.prompt.testing.allow", true);
        options.addArguments("-private-window");
        options.setProfile(profile);

        driver = new FirefoxDriver(options);
        String endpoint = "login";
        driver.get("http://localhost/" + endpoint);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "Auth-Front", dataProviderClass = DataProviders.class)
    public void testUI(String email, String password) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        WebElement emailInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        emailInputField.click();
        emailInputField.sendKeys(email);

        WebElement passwordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        passwordInputField.click();
        passwordInputField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("login_btn"));
        loginButton.click();

        WebElement loginSuccessMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(loginSuccessMessage.isDisplayed());

        wait.until(ExpectedConditions.urlContains("dashboard"));

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_btn")));
        logoutButton.click();

        WebElement overlay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-overlay")));

        if (overlay.isDisplayed()) {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error")));

            if (error.isDisplayed()) {
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error")));

                Assert.fail(errorMessage.getText());
            }
        }

        WebElement logoutSuccessMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(logoutSuccessMessage.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
