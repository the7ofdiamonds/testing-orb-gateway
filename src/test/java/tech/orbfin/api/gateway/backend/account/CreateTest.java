package tech.orbfin.api.gateway.backend.account;

import lombok.extern.slf4j.Slf4j;

import org.testng.annotations.Test;
import org.testng.Assert;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.backend.endpoints.Account;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CreateTest {

    @Test(dataProvider = "Create", dataProviderClass = DataProviders.class)
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
            String userAgent
    ) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Real-IP", ip);
        headers.put("User-Agent", userAgent);
        headers.put("X-Longitude", longitude);
        headers.put("X-Latitude", latitude);

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
                .build();

        Response response = Account.create(headers, requestSignup);
        response.then().log().all();

        String refreshToken = response.getBody().jsonPath().get("refreshToken");
        String accessToken = response.getBody().jsonPath().get("accessToken");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(refreshToken);
    }
}
