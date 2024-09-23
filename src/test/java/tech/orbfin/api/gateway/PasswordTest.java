package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Password;

import tech.orbfin.api.gateway.payload.RequestChangePassword;
import tech.orbfin.api.gateway.payload.RequestForgot;
import tech.orbfin.api.gateway.payload.RequestUpdatePassword;

public class PasswordTest {
// Link to create user
    @Test
    void forgot(String email, String username) {
        RequestForgot requestForgot = new RequestForgot();
        requestForgot.setEmail(email);
        requestForgot.setUsername(username);
        Response response = Password.forgot(requestForgot);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
// Require access and refresh tokens
    @Test
    void change(String password, String confirmPassword) {
        RequestChangePassword requestChangePassword = new RequestChangePassword();
        requestChangePassword.setPassword(password);
        requestChangePassword.setConfirmPassword(confirmPassword);
        Response response = Password.change(requestChangePassword);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
    @Test
    void update(
            String email,
            String confirmationCode,
            String password,
            String confirmPassword
    ) {
        RequestUpdatePassword requestUpdatePassword = RequestUpdatePassword.builder()
                .email(email)
                .confirmationCode(confirmationCode)
                .password(password)
                .confirmPassword(confirmPassword)
                .build();
        Response response = Password.update(requestUpdatePassword);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
