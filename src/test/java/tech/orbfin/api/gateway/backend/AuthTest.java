package tech.orbfin.api.gateway.backend;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.backend.endpoints.Auth;

import tech.orbfin.api.gateway.payload.RequestLogin;

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
        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Token", deviceToken);
        headers.put("X-Real-IP", ip);
        headers.put("User-Agent", userAgent);
        headers.put("X-Longitude", longitude);
        headers.put("X-Latitude", latitude);

        RequestLogin requestLogin = RequestLogin.builder()
                .email(email)
                .password(password)
                .build();

        Response response = Auth.login(headers, requestLogin);
        response.then().log().all();

        String usrname = response.getBody().jsonPath().get("username");
        String refreshToken = response.getBody().jsonPath().get("refresh_token");
        String accessToken = response.getBody().jsonPath().get("access_token");

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

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3, dataProvider = "Auth", dataProviderClass = DataProviders.class)
    void logoutAll(
            String email,
            String password,
            String longitude,
            String latitude,
            String deviceToken,
            String userAgent,
            String ip) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Token", deviceToken);
        headers.put("X-Real-IP", ip);
        headers.put("User-Agent", userAgent);
        headers.put("X-Longitude", longitude);
        headers.put("X-Latitude", latitude);

        RequestLogin requestLogin = RequestLogin.builder()
                .email(email)
                .password(password)
                .build();

        Response responseLogin = Auth.login(headers, requestLogin);

        String refreshToken = responseLogin.getBody().jsonPath().get("refresh_token");
        String accessToken = responseLogin.getBody().jsonPath().get("access_token");

        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Refresh-Token", refreshToken);

        Response response = Auth.logoutAll(headers);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
