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

public class ChangeTest {

    @Test(priority = 1, dataProvider = "Change", dataProviderClass = DataProviders.class)
    void username(
            String email,
            String password,
            String username,
            String firstName,
            String lastName,
            String phone,
            ITestContext context
    ) {
        RequestChangeUsername requestChangeUsername = RequestChangeUsername.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();
        Response response = Change.username(requestChangeUsername);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        context.getSuite().setAttribute("email", requestChangeUsername.getEmail());
        context.getSuite().setAttribute("first_name", firstName);
        context.getSuite().setAttribute("last_name", lastName);
        context.getSuite().setAttribute("phone", phone);
    }

    @Test(priority = 2)
    void name(ITestContext context) {
        Object email = context.getSuite().getAttribute("email");
        Object firstName = context.getSuite().getAttribute("first_name");
        Object lastName = context.getSuite().getAttribute("last_name");
        RequestChangeName requestChangeName = RequestChangeName.builder()
                .email((String) email)
                .firstName((String) firstName)
                .lastName((String) lastName)
                .build();
        Response response = Change.name(requestChangeName);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void phone(ITestContext context) {
        Object email = context.getSuite().getAttribute("email");
        Object phone = context.getSuite().getAttribute("phone");
        RequestChangePhone requestChangePhone = new RequestChangePhone();
        requestChangePhone.setEmail((String) email);
        requestChangePhone.setPhone((String) phone);
        Response response = Change.phone(requestChangePhone);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
