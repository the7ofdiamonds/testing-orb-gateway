package tech.orbfin.api.gateway.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.time.Duration;

public class SignUpTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        String endpoint = "signup";
        driver.get("http://localhost/" + endpoint);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Test(dataProvider = "Create", dataProviderClass = DataProviders.class)
    public void testUI(
            String username,
            String email,
            String password,
            String confirmPassword,
            String nicename,
            String nickname,
            String firstname,
            String lastname,
            String phone,
            String longitude,
            String latitude,
            String ip,
            String userAgent) {
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));
        WebElement usernameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        usernameInputField.click();
        usernameInputField.sendKeys(username);

        WebElement emailInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        emailInputField.click();
        emailInputField.sendKeys(email);

        WebElement passwordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        passwordInputField.click();
        passwordInputField.sendKeys(password);

        WebElement confirmPasswordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirm-password")));
        confirmPasswordInputField.click();
        confirmPasswordInputField.sendKeys(confirmPassword);

        WebElement nicenameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("nicename")));
        nicenameInputField.click();
        nicenameInputField.sendKeys(nicename);

        WebElement nicknameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("nickname")));
        nicknameInputField.click();
        nicknameInputField.sendKeys(nickname);

        WebElement firstnameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("firstname")));
        firstnameInputField.click();
        firstnameInputField.sendKeys(firstname);

        WebElement lastnameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("lastname")));
        lastnameInputField.click();
        lastnameInputField.sendKeys(lastname);

        WebElement phoneInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("phone")));
        phoneInputField.click();
        phoneInputField.sendKeys(phone);

        WebElement signupButton = driver.findElement(By.id("signup_btn"));
        signupButton.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
