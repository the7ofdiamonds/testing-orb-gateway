package tech.orbfin.api.gateway.frontend.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.Assert;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.time.Duration;

public class PasswordChangeTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        ProfilesIni fireFox = new ProfilesIni();
        FirefoxProfile profile = fireFox.getProfile("Test");

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("geo.prompt.testing", true);
        options.addPreference("geo.prompt.testing.allow", true);
        options.setProfile(profile);

        driver = new FirefoxDriver(options);
        String endpoint = "login";
        driver.get("http://localhost/" + endpoint);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Test(priority = 1, dataProvider = "Password", dataProviderClass = DataProviders.class)
    public void testUI(
            String email,
            String password,
            String newPassword,
            String confirmPassword,
            ITestContext context
    ) {
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        WebElement emailInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        emailInputField.click();
        emailInputField.sendKeys(email);

        WebElement passwordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        passwordInputField.click();
        passwordInputField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("login_btn"));
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("dashboard"));

        WebElement changePasswordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));

        Assert.assertTrue(changePasswordInputField.isDisplayed());

        WebElement confirmPasswordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirm-password")));

        Assert.assertTrue(confirmPasswordInputField.isDisplayed());

        WebElement passwordChangeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_password_btn")));

        Assert.assertTrue(passwordChangeButton.isDisplayed());

        context.getSuite().setAttribute("password", newPassword);
        context.getSuite().setAttribute("confirm_password", confirmPassword);
    }

    @Test(priority = 2)
    public void testPasswordChange(ITestContext context) {
        String password = (String) context.getSuite().getAttribute("password");
        String confirmPassword = (String) context.getSuite().getAttribute("confirm_password");

        WebElement changePasswordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));

        changePasswordInputField.click();
        changePasswordInputField.sendKeys(password);

        WebElement confirmPasswordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirm-password")));

        confirmPasswordInputField.click();
        confirmPasswordInputField.sendKeys(confirmPassword);

        WebElement passwordChangeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_password_btn")));

        passwordChangeButton.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
