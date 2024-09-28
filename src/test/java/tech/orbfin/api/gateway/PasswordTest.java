package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Password;

import tech.orbfin.api.gateway.payload.RequestChangePassword;
import tech.orbfin.api.gateway.payload.RequestForgot;
import tech.orbfin.api.gateway.payload.RequestUpdatePassword;

import tech.orbfin.api.gateway.utilities.DataProviders;

public class PasswordTest {

    @Test(priority = 1, dataProvider = "Password", dataProviderClass = DataProviders.class)
    void forgot(String email,
                String username,
                String password,
                String confirmPassword,
                String confirmationCode,
                ITestContext context) {
        RequestForgot requestForgot = new RequestForgot();
        requestForgot.setEmail(email);
        Response response = Password.forgot(requestForgot);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        RequestForgot request = new RequestForgot();
        request.setUsername(username);
        Response res = Password.forgot(requestForgot);
        res.then().log().all();

        Assert.assertEquals(res.getStatusCode(), 200);

        context.getSuite().setAttribute("email", requestForgot.getEmail());
        context.getSuite().setAttribute("username", request.getUsername());
        context.getSuite().setAttribute("password", password);
        context.getSuite().setAttribute("confirm_password", confirmPassword);
        context.getSuite().setAttribute("confirmation_code", confirmationCode);
    }
// Require access and refresh tokens
    @Test(priority = 2)
    void change(ITestContext context) {
        Object password = context.getSuite().getAttribute("password");
        Object confirmPassword = context.getSuite().getAttribute("confirm_password");
        RequestChangePassword requestChangePassword = new RequestChangePassword();
        requestChangePassword.setPassword((String) password);
        requestChangePassword.setConfirmPassword((String) confirmPassword);
        Response response = Password.change(requestChangePassword);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void update(ITestContext context) {
        Object email = context.getSuite().getAttribute("email");
        Object confirmationCode = context.getSuite().getAttribute("confirmation_code");
        Object password = context.getSuite().getAttribute("password");
        Object confirmPassword = context.getSuite().getAttribute("confirm_password");
        RequestUpdatePassword requestUpdatePassword = RequestUpdatePassword.builder()
                .email((String) email)
                .confirmationCode((String) confirmationCode)
                .password((String) password)
                .confirmPassword((String) confirmPassword)
                .build();
        Response response = Password.update(requestUpdatePassword);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
