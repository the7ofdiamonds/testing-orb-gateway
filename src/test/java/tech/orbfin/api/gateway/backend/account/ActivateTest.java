package tech.orbfin.api.gateway.backend.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.backend.endpoints.Account;

import tech.orbfin.api.gateway.payload.*;
import tech.orbfin.api.gateway.repositories.RepositoryUser;
import tech.orbfin.api.gateway.utilities.DataProviders;

public class ActivateTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    RepositoryUser repositoryUser;

    @BeforeClass
    public void setUp() {
        repositoryUser = new RepositoryUser(jdbcTemplate);
    }

    @Test(dataProvider = "Email", dataProviderClass = DataProviders.class)
    void activate(String email) {
        User user = repositoryUser.findUserByEmail(email);
        String userActivationKey = user.getUserActivationKey();
        RequestActivateAccount requestActivateAccount = new RequestActivateAccount(email, userActivationKey);
        Response response = Account.activate(requestActivateAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
