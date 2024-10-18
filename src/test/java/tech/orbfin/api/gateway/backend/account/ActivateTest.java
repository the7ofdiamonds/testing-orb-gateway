package tech.orbfin.api.gateway.backend.account;

import org.testng.annotations.Test;
import org.testng.Assert;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Account;

import tech.orbfin.api.gateway.payload.*;
import tech.orbfin.api.gateway.utilities.DataProviders;

public class ActivateTest {

    @Test(dataProvider = "Activate", dataProviderClass = DataProviders.class)
    void activate(String email, String userActivationCode) {
        RequestActivateAccount requestActivateAccount = new RequestActivateAccount(email, userActivationCode);
        Response response = Account.activate(requestActivateAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
