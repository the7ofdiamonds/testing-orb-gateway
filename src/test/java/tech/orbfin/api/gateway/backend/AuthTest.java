package tech.orbfin.api.gateway.backend;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.backend.endpoints.Auth;

import tech.orbfin.api.gateway.payload.Location;
import tech.orbfin.api.gateway.payload.RequestLogin;

import tech.orbfin.api.gateway.payload.RequestLogoutAll;
import tech.orbfin.api.gateway.utilities.DataProviders;

import java.util.HashMap;
import java.util.Map;

public class AuthTest {

    @Test(priority = 1, dataProvider = "Auth", dataProviderClass = DataProviders.class)
    void login(
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
        Response response = Auth.login(requestLogin);
        String usrname = response.getBody().jsonPath().get("username");
        String refreshToken = response.getBody().jsonPath().get("refresh_token");
        String accessToken = response.getBody().jsonPath().get("access_token");
        response.then().log().all();

        context.getSuite().setAttribute("username", usrname);
        context.getSuite().setAttribute("refresh_token", refreshToken);
        context.getSuite().setAttribute("access_token", accessToken);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    void logout(ITestContext context) {
        String refreshToken = (String) context.getSuite().getAttribute("refresh_token");
        String accessToken = (String) context.getSuite().getAttribute("access_token");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Refresh-Token", refreshToken);

        Response response = Auth.logout(headers);
        response.then().log().all();

        context.getSuite().setAttribute("headers", headers);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void logoutAll(ITestContext context) {
        String username = (String) context.getSuite().getAttribute("username");
        Map<String, String> headers = (Map<String, String>) context.getSuite().getAttribute("headers");
        RequestLogoutAll requestLogoutAll = new RequestLogoutAll(username);

        Response response = Auth.logoutAll(headers, requestLogoutAll);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
