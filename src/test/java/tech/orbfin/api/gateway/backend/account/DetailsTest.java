package tech.orbfin.api.gateway.backend.account;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestContext;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.backend.endpoints.Account;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.util.HashMap;
import java.util.Map;

public class DetailsTest {
// Front end test ???
    @Test(priority = 1, dataProvider = "Details", dataProviderClass = DataProviders.class)
    void lock(
            String email,
            String userActivationKey,
            String accessToken,
            String refreshToken,
            ITestContext context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Refresh-Token", refreshToken);

        Response response = Account.lock(headers);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        context.getSuite().setAttribute("email", email);
        context.getSuite().setAttribute("user_activation_key", userActivationKey);
    }

    @Test(priority = 2)
    void unlock(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        String userActivationKey = (String) context.getSuite().getAttribute("user_activation_key");
        RequestActivateAccount requestActivateAccount = RequestActivateAccount.builder()
                .email(email)
                .userActivationKey(userActivationKey)
                .build();
        Response response = Account.unlock(requestActivateAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
