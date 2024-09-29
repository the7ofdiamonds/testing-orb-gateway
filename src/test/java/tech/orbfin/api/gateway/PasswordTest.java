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

import java.util.HashMap;
import java.util.Map;

public class PasswordTest {

    @Test(priority = 1, dataProvider = "Password", dataProviderClass = DataProviders.class)
    void forgot(String email,
                String username,
                String oldPassword,
                String password,
                String confirmPassword,
                String accessToken,
                String refreshToken,
                ITestContext context) {
        RequestForgot requestForgot = new RequestForgot();
        requestForgot.setEmail(email);
        Response response = Password.forgot(requestForgot);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        RequestForgot request = new RequestForgot();
        request.setUsername(username);
        Response res = Password.forgot(requestForgot);
        String confirmationCode = response.getBody().jsonPath().get("confirmationCode");
        res.then().log().all();

        Assert.assertEquals(res.getStatusCode(), 200);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Refresh-Token", refreshToken);

        context.getSuite().setAttribute("email", requestForgot.getEmail());
        context.getSuite().setAttribute("username", request.getUsername());
        context.getSuite().setAttribute("old_password", oldPassword);
        context.getSuite().setAttribute("password", password);
        context.getSuite().setAttribute("confirm_password", confirmPassword);
        context.getSuite().setAttribute("confirmation_code", confirmationCode);
        context.getSuite().setAttribute("headers", headers);
    }

    @Test(priority = 2)
    void change(ITestContext context) {
        Map<String, String> headers = (Map<String, String>) context.getSuite().getAttribute("headers");
        String email = (String) context.getSuite().getAttribute("email");
        String oldPassword = (String) context.getSuite().getAttribute("old_password");
        String password = (String) context.getSuite().getAttribute("password");
        String confirmPassword = (String) context.getSuite().getAttribute("confirm_password");
        RequestChangePassword requestChangePassword = RequestChangePassword.builder()
                .email(email)
                .oldPassword(oldPassword)
                .password(password)
                .confirmPassword(confirmPassword)
                .build();

        Response response = Password.change(headers, requestChangePassword);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void update(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        String confirmationCode = (String) context.getSuite().getAttribute("confirmation_code");
        String password = (String) context.getSuite().getAttribute("password");
        String confirmPassword = (String) context.getSuite().getAttribute("confirm_password");
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
