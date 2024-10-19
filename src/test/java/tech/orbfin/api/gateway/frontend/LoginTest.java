package tech.orbfin.api.gateway.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tech.orbfin.api.gateway.utilities.DataProviders;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        String endpoint = "login";
        driver.get("http://localhost/" + endpoint);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Test(dataProvider = "Auth", dataProviderClass = DataProviders.class)
    public void testUI(
            String email,
            String password,
            String longitude,
            String latitude,
            String deviceToken,
            String userAgent,
            String ip
    ) {
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

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
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
