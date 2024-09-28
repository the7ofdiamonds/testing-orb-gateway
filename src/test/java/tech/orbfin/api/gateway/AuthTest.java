package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Auth;

import tech.orbfin.api.gateway.payload.Location;
import tech.orbfin.api.gateway.payload.RequestLogin;
import tech.orbfin.api.gateway.payload.RequestLogout;
import tech.orbfin.api.gateway.payload.RequestLogoutAll;
import tech.orbfin.api.gateway.utilities.DataProviders;

public class AuthTest {

    @Test(priority = 1, dataProvider = "Auth", dataProviderClass = DataProviders.class)
    void login(
            String username,
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
                .username(username)
                .password(password)
                .location(location)
                .deviceToken(deviceToken)
                .userAgent(userAgent)
                .ip(ip)
                .build();
        Response response = Auth.login(requestLogin);
        String usrname = response.getBody().jsonPath().get("username");
        String refreshToken = response.getBody().jsonPath().get("refreshToken");
        String accessToken = response.getBody().jsonPath().get("accessToken");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        context.getSuite().setAttribute("username", usrname);
        context.getSuite().setAttribute("refresh_token", refreshToken);
        context.getSuite().setAttribute("access_token", accessToken);
    }

    @Test(priority = 2)
    void logout(ITestContext context) {
        Object refreshToken = context.getSuite().getAttribute("refresh_token");
        Object accessToken = context.getSuite().getAttribute("access_token");
        RequestLogout requestLogout = new RequestLogout();
        requestLogout.setAccessToken((String) accessToken);
        requestLogout.setRefreshToken((String) refreshToken);
        Response response = Auth.logout(requestLogout);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void logoutAll(ITestContext context) {
        Object username = context.getSuite().getAttribute("username");
        Object refreshToken = context.getSuite().getAttribute("refresh_token");
        Object accessToken = context.getSuite().getAttribute("access_token");
        RequestLogoutAll requestLogoutAll = new RequestLogoutAll((String) username);
        Response response = Auth.logoutAll(requestLogoutAll);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
