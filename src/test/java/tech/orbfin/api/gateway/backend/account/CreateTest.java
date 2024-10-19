package tech.orbfin.api.gateway.backend.account;

import lombok.extern.slf4j.Slf4j;

import org.testng.annotations.Test;
import org.testng.Assert;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.backend.endpoints.Account;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.utilities.DataProviders;

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

        String refreshToken = response.getBody().jsonPath().get("refreshToken");
        String accessToken = response.getBody().jsonPath().get("accessToken");

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(refreshToken);
    }
}
