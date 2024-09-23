package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Auth;

import tech.orbfin.api.gateway.payload.Location;
import tech.orbfin.api.gateway.payload.RequestLogin;
import tech.orbfin.api.gateway.payload.RequestLogout;
import tech.orbfin.api.gateway.payload.RequestLogoutAll;

public class AuthTest {

    @Test
    void login(String username, String password, String longitude, String latitude, String deviceToken, String userAgent, String ip) {
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
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

//    Link to login for auth header
    @Test
    void logout(String accessToken, String refreshToken) {
        RequestLogout requestLogout = new RequestLogout();
        requestLogout.setAccessToken(accessToken);
        requestLogout.setRefreshToken(refreshToken);
        Response response = Auth.logout(requestLogout);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

//    Link to login for auth header
    @Test
    void logoutAll(String username) {
        RequestLogoutAll requestLogoutAll = new RequestLogoutAll(username);
        Response response = Auth.logoutAll(requestLogoutAll);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
