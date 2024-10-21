package tech.orbfin.api.gateway.backend.account;

import org.springframework.beans.factory.annotation.Autowired;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import io.restassured.response.Response;

import org.testng.Assert;

import tech.orbfin.api.gateway.backend.endpoints.Account;

import tech.orbfin.api.gateway.payload.RequestActivateAccount;

import tech.orbfin.api.gateway.payload.User;
import tech.orbfin.api.gateway.repositories.RepositoryUser;
import tech.orbfin.api.gateway.utilities.DataProviders;

public class RecoverTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    RepositoryUser repositoryUser;

    @BeforeClass
    public void setUp() {
        repositoryUser = new RepositoryUser(jdbcTemplate);
    }

    @Test(dataProvider = "Email", dataProviderClass = DataProviders.class)
    void recover(String email) {
        User user = repositoryUser.findUserByEmail(email);
        String userActivationKey = user.getUserActivationKey();
        RequestActivateAccount requestActivateAccount = RequestActivateAccount.builder()
                .email(email)
                .userActivationKey(userActivationKey)
                .build();

        Response response = Account.recover(requestActivateAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
