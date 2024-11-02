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

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

import tech.orbfin.api.gateway.GatewayApplication;
import tech.orbfin.api.gateway.payload.User;
import tech.orbfin.api.gateway.repositories.RepositoryUser;
import tech.orbfin.api.gateway.utilities.DataProviders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;

@TestPropertySource(locations = "classpath:application.yaml")
@SpringBootTest(classes = GatewayApplication.class)
@ActiveProfiles("test")
public class PasswordTest extends AbstractTestNGSpringContextTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    RepositoryUser repositoryUser;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        repositoryUser = new RepositoryUser(jdbcTemplate);
    }

    @Test(dataProvider = "Password-Recovery", dataProviderClass = DataProviders.class)
    public void testUI(
            String email,
            String password,
            String confirmPassword
    ) throws UnsupportedEncodingException {
        String encodedEmail = URLEncoder.encode(email, "UTF-8");
        User user = repositoryUser.findUserByEmail(email);
        String confirmationCode = user.getConfirmationCode();
        driver.get("http://localhost/password/recovery/" + encodedEmail + "/" + confirmationCode);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        WebElement passwordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));

        Assert.assertTrue(passwordInputField.isDisplayed());

        passwordInputField.click();
        passwordInputField.sendKeys(password);

        WebElement confirmPasswordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirm-password")));

        Assert.assertTrue(confirmPasswordInputField.isDisplayed());

        confirmPasswordInputField.click();
        confirmPasswordInputField.sendKeys(confirmPassword);

        WebElement confirmButton = driver.findElement(By.id("confirm_password_btn"));

        Assert.assertTrue(confirmButton.isDisplayed());

        confirmButton.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
