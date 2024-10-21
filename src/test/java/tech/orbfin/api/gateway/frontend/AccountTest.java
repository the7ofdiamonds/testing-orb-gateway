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

public class AccountTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Test(priority = 1, dataProvider = "Activate", dataProviderClass = DataProviders.class)
    public void testActivation(String email, String userActivationCode) throws UnsupportedEncodingException {
        driver = new ChromeDriver();
        String encodedEmail = URLEncoder.encode(email, "UTF-8");
        driver.get("http://localhost/account/activation/" + encodedEmail + "/" + userActivationCode);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());
    }

    @Test(priority = 2, dataProvider = "Recover", dataProviderClass = DataProviders.class)
    public void testRecovery(String email, String userActivationCode) throws UnsupportedEncodingException {
        driver = new ChromeDriver();
        String encodedEmail = URLEncoder.encode(email, "UTF-8");
        driver.get("http://localhost/account/recovery/" + encodedEmail + "/" + userActivationCode);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
