package tech.orbfin.api.gateway.backend.account;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestContext;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.GatewayApplication;
import tech.orbfin.api.gateway.backend.endpoints.Account;

import tech.orbfin.api.gateway.backend.endpoints.Auth;
import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.repositories.RepositoryUser;
import tech.orbfin.api.gateway.utilities.DataProviders;

import java.util.HashMap;
import java.util.Map;

@TestPropertySource(locations = "classpath:application.yaml")
@SpringBootTest(classes = GatewayApplication.class)
@ActiveProfiles("test")
public class DetailsTest extends AbstractTestNGSpringContextTests {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    RepositoryUser repositoryUser;

    @BeforeClass
    public void setUp() {
        repositoryUser = new RepositoryUser(jdbcTemplate);
    }

    @Test(priority = 1, dataProvider = "Auth", dataProviderClass = DataProviders.class)
    void lock(
            String email,
            String password,
            String longitude,
            String latitude,
            String deviceToken,
            String userAgent,
            String ip,
            ITestContext context) {
        double longValue = 0.0;
        double latValue = 0.0;

        try {
            longValue = Double.parseDouble(longitude);
            latValue = Double.parseDouble(latitude);
        } catch (NumberFormatException e) {
            Assert.fail("Invalid longitude or latitude: " + e.getMessage());
        }

        Location location = new Location(longValue, latValue);

        RequestLogin requestLogin = RequestLogin.builder()
                .email(email)
                .password(password)
                .location(location)
                .deviceToken(deviceToken)
                .userAgent(userAgent)
                .ip(ip)
                .build();
        Response responseLogin = Auth.login(requestLogin);
        String refreshToken = responseLogin.getBody().jsonPath().get("refresh_token");
        String accessToken = responseLogin.getBody().jsonPath().get("access_token");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Refresh-Token", refreshToken);

        Response response = Account.lock(headers);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        context.getSuite().setAttribute("email", email);
    }

    @Test(priority = 2)
    void unlock(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        User user = repositoryUser.findUserByEmail(email);
        String userActivationKey = user.getUserActivationKey();
        RequestActivateAccount requestActivateAccount = RequestActivateAccount.builder()
                .email(email)
                .userActivationKey(userActivationKey)
                .build();
        Response response = Account.unlock(requestActivateAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
