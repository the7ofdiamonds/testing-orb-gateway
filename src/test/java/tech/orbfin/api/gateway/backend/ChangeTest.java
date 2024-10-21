package tech.orbfin.api.gateway.backend;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestContext;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.backend.endpoints.Auth;
import tech.orbfin.api.gateway.backend.endpoints.Change;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.util.HashMap;
import java.util.Map;

public class ChangeTest {

    @Test(priority = 1, dataProvider = "Change", dataProviderClass = DataProviders.class)
    void username(
            String email,
            String password,
            String username,
            String nicename,
            String nickname,
            String firstName,
            String lastName,
            String phone,
            ITestContext context
    ) {
        RequestLogin requestLogin = RequestLogin.builder()
                .email(email)
                .password(password)
                .build();
        Response responseLogin = Auth.login(requestLogin);
        String refreshToken = responseLogin.getBody().jsonPath().get("refresh_token");
        String accessToken = responseLogin.getBody().jsonPath().get("access_token");
        RequestChangeUsername requestChangeUsername = RequestChangeUsername.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Refresh-Token", refreshToken);

        Response response = Change.username(headers, requestChangeUsername);
        response.then().log().all();

        context.getSuite().setAttribute("email", requestChangeUsername.getEmail());
        context.getSuite().setAttribute("password", requestChangeUsername.getPassword());
        context.getSuite().setAttribute("nicename", nicename);
        context.getSuite().setAttribute("nickname", nickname);
        context.getSuite().setAttribute("first_name", firstName);
        context.getSuite().setAttribute("last_name", lastName);
        context.getSuite().setAttribute("phone", phone);
        context.getSuite().setAttribute("headers", headers);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    void nicename(ITestContext context) {
        Map<String, String> headers = (Map<String, String>) context.getSuite().getAttribute("headers");
        String email = (String) context.getSuite().getAttribute("email");
        String password = (String) context.getSuite().getAttribute("password");
        String nicename = (String) context.getSuite().getAttribute("nicename");
        RequestChangeNicename requestChangeNicename = RequestChangeNicename.builder()
                .email(email)
                .password(password)
                .nicename(nicename)
                .build();

        Response response = Change.nicename(headers, requestChangeNicename);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void nickname(ITestContext context) {
        Map<String, String> headers = (Map<String, String>) context.getSuite().getAttribute("headers");
        String email = (String) context.getSuite().getAttribute("email");
        String password = (String) context.getSuite().getAttribute("password");
        String nickname = (String) context.getSuite().getAttribute("nickname");
        RequestChangeNickname requestChangeNickname = RequestChangeNickname.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        Response response = Change.nickname(headers, requestChangeNickname);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    void name(ITestContext context) {
        Map<String, String> headers = (Map<String, String>) context.getSuite().getAttribute("headers");
        String email = (String) context.getSuite().getAttribute("email");
        String password = (String) context.getSuite().getAttribute("password");
        String firstName = (String) context.getSuite().getAttribute("first_name");
        String lastName = (String) context.getSuite().getAttribute("last_name");
        RequestChangeName requestChangeName = RequestChangeName.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Response response = Change.name(headers, requestChangeName);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    void phone(ITestContext context) {
        Map<String, String> headers = (Map<String, String>) context.getSuite().getAttribute("headers");
        String email = (String) context.getSuite().getAttribute("email");
        String password = (String) context.getSuite().getAttribute("password");
        String phone = (String) context.getSuite().getAttribute("phone");
        RequestChangePhone requestChangePhone = RequestChangePhone.builder()
                .email(email)
                .password(password)
                .phone(phone)
                .build();

        Response response = Change.phone(headers, requestChangePhone);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
