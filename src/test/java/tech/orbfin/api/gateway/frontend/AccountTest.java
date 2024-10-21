package tech.orbfin.api.gateway.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

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
public class AccountTest extends AbstractTestNGSpringContextTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    RepositoryUser repositoryUser;

    @BeforeClass
    public void setUp() {
        repositoryUser = new RepositoryUser(jdbcTemplate);
    }

    @Test(priority = 1, dataProvider = "Activate", dataProviderClass = DataProviders.class)
    public void testActivation(String email) throws UnsupportedEncodingException {
        driver = new ChromeDriver();
        String encodedEmail = URLEncoder.encode(email, "UTF-8");
        User user = repositoryUser.findUserByEmail(email);
        String userActivationKey = user.getUserActivationKey();
        driver.get("http://localhost/account/activation/" + encodedEmail + "/" + userActivationKey);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());
    }

    @Test(priority = 2, dataProvider = "Recover", dataProviderClass = DataProviders.class)
    public void testRecovery(String email) throws UnsupportedEncodingException {
        driver = new ChromeDriver();
        String encodedEmail = URLEncoder.encode(email, "UTF-8");
        User user = repositoryUser.findUserByEmail(email);
        String userActivationKey = user.getUserActivationKey();
        driver.get("http://localhost/account/recovery/" + encodedEmail + "/" + userActivationKey);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assert.assertTrue(message.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
