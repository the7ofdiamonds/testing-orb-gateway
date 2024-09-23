package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Change;
import tech.orbfin.api.gateway.payload.RequestChangeName;
import tech.orbfin.api.gateway.payload.RequestChangePhone;
import tech.orbfin.api.gateway.payload.RequestChangeUsername;

public class ChangeTest {
// Link to create user
    @Test
    void username(
            String email,
            String password,
            String username
    ) {
        RequestChangeUsername requestChangeUsername = RequestChangeUsername.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();
        Response response = Change.username(requestChangeUsername);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
    @Test
    void name(
            String email,
            String firstName,
            String lastName) {
        RequestChangeName requestChangeName = RequestChangeName.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Response response = Change.name(requestChangeName);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
    @Test
    void phone(String email, String phone) {
        RequestChangePhone requestChangePhone = new RequestChangePhone();
        requestChangePhone.setEmail(email);
        requestChangePhone.setPhone(phone);
        Response response = Change.phone(requestChangePhone);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
