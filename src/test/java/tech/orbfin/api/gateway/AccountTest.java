package tech.orbfin.api.gateway;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Admin;
import tech.orbfin.api.gateway.endpoints.Account;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.utilities.DataProviders;

@Slf4j
public class AccountTest {

    @Test(priority = 1, dataProvider = "RequestSignup", dataProviderClass = DataProviders.class)
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
            String userAgent,
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
        Integer id = response.getBody().jsonPath().get("id");
        String confirmationCode = response.getBody().jsonPath().get("confirmationCode");
        String refreshToken = response.getBody().jsonPath().get("refreshToken");
        String accessToken = response.getBody().jsonPath().get("accessToken");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 201);

        context.getSuite().setAttribute("refresh_token", refreshToken);
        context.getSuite().setAttribute("access_token", accessToken);
        context.getSuite().setAttribute("user_id", id);
        context.getSuite().setAttribute("email", email);
        context.getSuite().setAttribute("username", username);
        context.getSuite().setAttribute("confirmation_code", confirmationCode);
        context.getSuite().setAttribute("password", password);
    }

    @Test(priority = 2)
    void lock(ITestContext context) {
        Object email = context.getSuite().getAttribute("email");
        Object confirmationCode = context.getSuite().getAttribute("confirmation_code");
        RequestVerify requestVerify = new RequestVerify();
        requestVerify.setEmail((String) email);
        requestVerify.setConfirmationCode((String) confirmationCode);
        Response response = Account.lock(requestVerify);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void unlock(ITestContext context) {
        Object email = context.getSuite().getAttribute("email");
        Object confirmationCode = context.getSuite().getAttribute("confirmation_code");
        RequestVerify requestVerify = new RequestVerify();
        requestVerify.setEmail((String) email);
        requestVerify.setConfirmationCode((String) confirmationCode);
        Response response = Account.unlock(requestVerify);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    void remove(ITestContext context) {
        Object email = context.getSuite().getAttribute("email");
        Object password = context.getSuite().getAttribute("password");
        Object confirmationCode = context.getSuite().getAttribute("confirmation_code");
        RequestRemoveAccount requestRemoveAccount = RequestRemoveAccount.builder()
                .email((String) email)
                .password((String) password)
                .confirmationCode((String) confirmationCode)
                .build();

        Response response = Account.remove(requestRemoveAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    void delete(ITestContext context) {
        Object email = context.getSuite().getAttribute("email");
        Object username = context.getSuite().getAttribute("username");
        Object confirmationCode = context.getSuite().getAttribute("confirmation_code");
        RequestDeleteAccount requestDeleteAccount = RequestDeleteAccount.builder()
                .email((String) email)
                .username((String) username)
                .confirmationCode((String) confirmationCode)
                .build();

        Response response = Admin.deleteAccount(requestDeleteAccount);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
