package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Admin;
import tech.orbfin.api.gateway.endpoints.Account;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.utilities.DataProviders;

public class AccountTest {

    @Test(dataProvider = "RequestSignup", dataProviderClass = DataProviders.class)
    public void create(
            String username,
            String email,
            String password,
            String confirmPassword,
            String nicename,
            String nickname,
            String firstname,
            String lastname,
            String phone,
            String longitude,
            String latitude,
            String ip,
            String userAgent) {

        double longValue = 0.0;
        double latValue = 0.0;

        try {
            longValue = Double.parseDouble(longitude);
            latValue = Double.parseDouble(latitude);
        } catch (NumberFormatException e) {
            Assert.fail("Invalid longitude or latitude: " + e.getMessage());
        }

        Location location = new Location(longValue, latValue);

        RequestSignup requestSignup = RequestSignup.builder()
                .username(username)
                .email(email)
                .password(password)
                .confirmPassword(confirmPassword)
                .nicename(nicename)
                .nickname(nickname)
                .firstname(firstname)
                .lastname(lastname)
                .phone(phone)
                .location(location)
                .ip(ip)
                .userAgent(userAgent)
                .build();

        Response response = Account.create(requestSignup);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// link to create using userID
    @Test
    void lock(String email, String confirmationCode) {
        RequestVerify requestVerify = new RequestVerify();
        requestVerify.setEmail(email);
        requestVerify.setConfirmationCode(confirmationCode);
        Response response = Account.lock(requestVerify);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// link to create using userID
    @Test
    void unlock(String email, String confirmationCode) {
        RequestVerify requestVerify = new RequestVerify();
        requestVerify.setEmail(email);
        requestVerify.setConfirmationCode(confirmationCode);
        Response response = Account.unlock(requestVerify);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// link to create using userID
    @Test
    void remove(
            String email,
            String password,
            String confirmationCode
    ) {
        RequestRemoveAccount requestRemoveAccount = RequestRemoveAccount.builder()
                .email(email)
                .password(password)
                .confirmationCode(confirmationCode)
                .build();

        Response response = Account.remove(requestRemoveAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// link to create using userID
    @Test
    void delete(
            String email,
            String username,
            String confirmationCode
    ) {
        RequestDeleteAccount requestDeleteAccount = RequestDeleteAccount.builder()
                .email(email)
                .username(username)
                .confirmationCode(confirmationCode)
                .build();

        Response response = Admin.deleteAccount(requestDeleteAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
