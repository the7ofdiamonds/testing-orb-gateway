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

import org.testng.ITestContext;
import org.testng.annotations.*;
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
        options.addArguments("-private");
        options.setProfile(profile);

        driver = new FirefoxDriver(options);
        String endpoint = "login";
        driver.get("http://localhost/" + endpoint);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Test(priority = 1, dataProvider = "Auth-Front", dataProviderClass = DataProviders.class)
    public void testUI(String email, String password, ITestContext context) {
        WebElement emailInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        Assert.assertTrue(emailInputField.isDisplayed());

        WebElement passwordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        Assert.assertTrue(passwordInputField.isDisplayed());

        WebElement submitButton = driver.findElement(By.id("login_btn"));
        Assert.assertTrue(submitButton.isDisplayed());

        context.getSuite().setAttribute("email", email);
        context.getSuite().setAttribute("password", password);
    }

    @Test(priority = 2)
    public void testLogin(ITestContext context){
        String email = (String) context.getSuite().getAttribute("email");
        String password = (String) context.getSuite().getAttribute("password");

        WebElement emailInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        emailInputField.click();
        emailInputField.sendKeys(email);

        WebElement passwordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        passwordInputField.click();
        passwordInputField.sendKeys(password);

        WebElement submitButton = driver.findElement(By.id("login_btn"));
        submitButton.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());

        wait.until(ExpectedConditions.urlContains("dashboard"));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
