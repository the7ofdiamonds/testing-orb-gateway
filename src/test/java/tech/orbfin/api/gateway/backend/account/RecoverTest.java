package tech.orbfin.api.gateway.backend.account;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import org.testng.Assert;

import tech.orbfin.api.gateway.endpoints.Account;

import tech.orbfin.api.gateway.payload.RequestActivateAccount;

import tech.orbfin.api.gateway.utilities.DataProviders;

public class RecoverTest {
// Front end test ???
    @Test(dataProvider = "Recover", dataProviderClass = DataProviders.class)
    void recover(String email, String userActivationKey) {
        RequestActivateAccount requestActivateAccount = RequestActivateAccount.builder()
                .email(email)
                .userActivationKey(userActivationKey)
                .build();

        Response response = Account.recover(requestActivateAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
