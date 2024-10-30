package tech.orbfin.api.gateway.backend;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestContext;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.GatewayApplication;
import tech.orbfin.api.gateway.backend.endpoints.Auth;
import tech.orbfin.api.gateway.backend.endpoints.Password;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.repositories.RepositoryUser;
import tech.orbfin.api.gateway.utilities.DataProviders;

import java.util.HashMap;
import java.util.Map;

@TestPropertySource(locations = "classpath:application.yaml")
@SpringBootTest(classes = GatewayApplication.class)
@ActiveProfiles("test")
public class PasswordTest extends AbstractTestNGSpringContextTests {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    RepositoryUser repositoryUser;

    @BeforeClass
    public void setUp() {
        repositoryUser = new RepositoryUser(jdbcTemplate);
    }

    @Test(priority = 1, dataProvider = "Password", dataProviderClass = DataProviders.class)
    void forgot(String email,
                String username,
                String oldPassword,
                String confirmationCode,
                String password,
                String confirmPassword,
                ITestContext context) {
        RequestForgot requestForgot = new RequestForgot();
        requestForgot.setEmail(email);
        Response response = Password.forgot(requestForgot);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        RequestForgot request = new RequestForgot();
        request.setUsername(username);
        Response res = Password.forgot(requestForgot);
        res.then().log().all();

        context.getSuite().setAttribute("email", requestForgot.getEmail());
        context.getSuite().setAttribute("username", request.getUsername());
        context.getSuite().setAttribute("old_password", oldPassword);
        context.getSuite().setAttribute("confirmation_code", confirmationCode);
        context.getSuite().setAttribute("password", password);
        context.getSuite().setAttribute("confirm_password", confirmPassword);

        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test(priority = 2)
    void change(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        String newPassword = (String) context.getSuite().getAttribute("old_password");
        String password = (String) context.getSuite().getAttribute("password");
        String confirmPassword = (String) context.getSuite().getAttribute("confirm_password");

        String deviceToken = "123ABC456def";
        String ip = "123.456.7890";
        String userAgent = "Agent";
        double longValue = 0.0;
        double latValue = 0.0;
        Location location = new Location(longValue, latValue);

        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Token", deviceToken);
        headers.put("X-Real-IP", ip);
        headers.put("User-Agent", userAgent);
        headers.put("X-Longitude", String.valueOf(location.getLongitude()));
        headers.put("X-Latitude", String.valueOf(location.getLatitude()));

        RequestLogin requestLogin = RequestLogin.builder()
                .email(email)
                .password(password)
                .build();

        Response responseLogin = Auth.login(headers,requestLogin);
        String refreshToken = responseLogin.getBody().jsonPath().get("refresh_token");
        String accessToken = responseLogin.getBody().jsonPath().get("access_token");

        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Refresh-Token", refreshToken);

        RequestChangePassword requestChangePassword = RequestChangePassword.builder()
                .email(email)
                .password(password)
                .newPassword(newPassword)
                .confirmPassword(confirmPassword)
                .build();

        Response response = Password.change(headers, requestChangePassword);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void update(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        User user = repositoryUser.findUserByEmail(email);
        String confirmationCode = user.getConfirmationCode();
        String password = (String) context.getSuite().getAttribute("password");
        String confirmPassword = (String) context.getSuite().getAttribute("confirm_password");
        RequestUpdatePassword requestUpdatePassword = RequestUpdatePassword.builder()
                .email(email)
                .confirmationCode(confirmationCode)
                .password(password)
                .confirmPassword(confirmPassword)
                .build();
        Response response = Password.update(requestUpdatePassword);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
