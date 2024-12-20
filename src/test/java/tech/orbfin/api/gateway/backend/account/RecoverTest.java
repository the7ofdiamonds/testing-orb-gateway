package tech.orbfin.api.gateway.backend.account;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import io.restassured.response.Response;

import org.testng.Assert;

import tech.orbfin.api.gateway.GatewayApplication;
import tech.orbfin.api.gateway.backend.endpoints.Account;

import tech.orbfin.api.gateway.payload.RequestActivateAccount;

import tech.orbfin.api.gateway.payload.User;
import tech.orbfin.api.gateway.repositories.RepositoryUser;
import tech.orbfin.api.gateway.utilities.DataProviders;

import java.util.HashMap;
import java.util.Map;

@TestPropertySource(locations = "classpath:application.yaml")
@SpringBootTest(classes = GatewayApplication.class)
@ActiveProfiles("test")
public class RecoverTest extends AbstractTestNGSpringContextTests {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    RepositoryUser repositoryUser;

    @BeforeClass
    public void setUp() {
        repositoryUser = new RepositoryUser(jdbcTemplate);
    }

    @Test(dataProvider = "Email", dataProviderClass = DataProviders.class)
    void recover(String email) {
        String ip = "123.456.7890";
        String userAgent = "userAgent";
        String longitude = "123.4567";
        String latitude = "123.4567";

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Real-IP", ip);
        headers.put("User-Agent", userAgent);
        headers.put("X-Longitude", longitude);
        headers.put("X-Latitude", latitude);

        User user = repositoryUser.findUserByEmail(email);
        String userActivationKey = user.getUserActivationKey();
        RequestActivateAccount requestActivateAccount = RequestActivateAccount.builder()
                .email(email)
                .userActivationKey(userActivationKey)
                .build();

        Response response = Account.recover(headers, requestActivateAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
