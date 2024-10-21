package tech.orbfin.api.gateway.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;

public class PasswordTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Test(dataProvider = "Password-Recovery", dataProviderClass = DataProviders.class)
    public void testUI(
            String email,
            String confirmationCode,
            String password,
            String confirmPassword
    ) throws UnsupportedEncodingException {
        driver = new ChromeDriver();
        String encodedEmail = URLEncoder.encode(email, "UTF-8");
        driver.get("http://localhost/password/recovery/" + encodedEmail + "/" + confirmationCode);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        WebElement passwordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        passwordInputField.click();
        passwordInputField.sendKeys(password);

        WebElement confirmPasswordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirm-password")));
        confirmPasswordInputField.click();
        confirmPasswordInputField.sendKeys(confirmPassword);

        WebElement confirmButton = driver.findElement(By.id("confirm_password_btn"));
        confirmButton.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
