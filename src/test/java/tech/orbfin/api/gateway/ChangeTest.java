package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Change;

import tech.orbfin.api.gateway.payload.RequestChangeName;
import tech.orbfin.api.gateway.payload.RequestChangePhone;
import tech.orbfin.api.gateway.payload.RequestChangeUsername;

import tech.orbfin.api.gateway.utilities.DataProviders;

import java.util.HashMap;
import java.util.Map;

public class ChangeTest {

    @Test(priority = 1, dataProvider = "Change", dataProviderClass = DataProviders.class)
    void username(
            String email,
            String password,
            String username,
            String firstName,
            String lastName,
            String phone,
            String accessToken,
            String refreshToken,
            ITestContext context
    ) {
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

        Assert.assertEquals(response.getStatusCode(), 200);

        context.getSuite().setAttribute("email", requestChangeUsername.getEmail());
        context.getSuite().setAttribute("password", requestChangeUsername.getPassword());
        context.getSuite().setAttribute("first_name", firstName);
        context.getSuite().setAttribute("last_name", lastName);
        context.getSuite().setAttribute("phone", phone);
        context.getSuite().setAttribute("headers", headers);
    }

    @Test(priority = 2)
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

    @Test(priority = 3)
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
