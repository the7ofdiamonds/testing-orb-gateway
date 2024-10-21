package tech.orbfin.api.gateway.frontend.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.ITestContext;
import org.testng.Assert;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.time.Duration;

public class SettingsTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        String endpoint = "login";
        driver.get("http://localhost/" + endpoint);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Test(priority = 1, dataProvider = "Auth-Front", dataProviderClass = DataProviders.class)
    public void testUI(String email, String password) {
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

        WebElement settingsButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("settings_btn")));
        settingsButton.click();

        WebElement usernameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        WebElement nicenameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("nicename")));
        WebElement nicknameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("nickname")));
        WebElement phoneInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("phone")));
        WebElement firstnameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("firstname")));
        WebElement lastnameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("lastname")));
        WebElement pwdInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        WebElement confirmPasswordInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirm-password")));

        WebElement changeUsernameButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_username_btn")));
        WebElement changeNicenameButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_nicename_btn")));
        WebElement changeNicknameButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_nickname_btn")));
        WebElement changePhoneButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_phone_btn")));
        WebElement changeNameButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_name_btn")));
        WebElement changePasswordButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_password_btn")));

        Assert.assertTrue(usernameInputField.isDisplayed());
        Assert.assertTrue(nicenameInputField.isDisplayed());
        Assert.assertTrue(nicknameInputField.isDisplayed());
        Assert.assertTrue(phoneInputField.isDisplayed());
        Assert.assertTrue(firstnameInputField.isDisplayed());
        Assert.assertTrue(lastnameInputField.isDisplayed());
        Assert.assertTrue(pwdInputField.isDisplayed());
        Assert.assertTrue(confirmPasswordInputField.isDisplayed());

        Assert.assertTrue(changeUsernameButton.isDisplayed());
        Assert.assertTrue(changeNicenameButton.isDisplayed());
        Assert.assertTrue(changeNicknameButton.isDisplayed());
        Assert.assertTrue(changePhoneButton.isDisplayed());
        Assert.assertTrue(changeNameButton.isDisplayed());
        Assert.assertTrue(changePasswordButton.isDisplayed());
    }

    @Test(priority = 2, dataProvider = "Change-Front", dataProviderClass = DataProviders.class)
    public void testUsername(
            String username,
            String nicename,
            String nickname,
            String firstName,
            String lastName,
            String phone,
            ITestContext context
    ) {
        WebElement usernameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        usernameInputField.click();
        usernameInputField.sendKeys(username);

        WebElement changeUsernameButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_username_btn")));
        changeUsernameButton.click();

        context.getSuite().setAttribute("nicename", nicename);
        context.getSuite().setAttribute("nickname", nickname);
        context.getSuite().setAttribute("first_name", firstName);
        context.getSuite().setAttribute("last_name", lastName);
        context.getSuite().setAttribute("phone", phone);
    }

    @Test(priority = 3)
    public void testNicename(ITestContext context) {
        String nicename = (String) context.getSuite().getAttribute("nicename");

        WebElement nicenameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("nicename")));
        nicenameInputField.click();
        nicenameInputField.sendKeys(nicename);

        WebElement changeNicenameButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_nicename_btn")));
        changeNicenameButton.click();
    }

    @Test(priority = 4)
    public void testNickname(ITestContext context) {
        String nickname = (String) context.getSuite().getAttribute("nickname");

        WebElement nicknameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("nickname")));
        nicknameInputField.click();
        nicknameInputField.sendKeys(nickname);

        WebElement changeNicknameButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_nickname_btn")));
        changeNicknameButton.click();
    }

    @Test(priority = 5)
    public void testPhone(ITestContext context) {
        String phone = (String) context.getSuite().getAttribute("phone");

        WebElement phoneInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("phone")));
        phoneInputField.click();
        phoneInputField.sendKeys(phone);

        WebElement changePhoneButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_phone_btn")));
        changePhoneButton.click();
    }

    @Test(priority = 6)
    public void testName(ITestContext context) {
        String firstName = (String) context.getSuite().getAttribute("first_name");
        String lastName = (String) context.getSuite().getAttribute("last_name");

        WebElement firstnameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("firstname")));
        WebElement lastnameInputField = wait.until(ExpectedConditions.elementToBeClickable(By.name("lastname")));

        firstnameInputField.click();
        firstnameInputField.sendKeys(firstName);
        lastnameInputField.click();
        lastnameInputField.sendKeys(lastName);

        WebElement changeNameButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("change_name_btn")));
        changeNameButton.click();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
