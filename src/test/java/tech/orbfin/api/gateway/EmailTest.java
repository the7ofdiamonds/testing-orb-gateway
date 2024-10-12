package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Email;

import tech.orbfin.api.gateway.payload.RequestAddEmail;
import tech.orbfin.api.gateway.payload.RequestRemoveEmail;

import tech.orbfin.api.gateway.utilities.DataProviders;

public class EmailTest {

    @Test(priority = 1, dataProvider = "Email", dataProviderClass = DataProviders.class)
    void add(
            String email,
            String password,
            String newEmail,
            String removeEmail,
            ITestContext context) {
        RequestAddEmail requestAddEmail = RequestAddEmail.builder()
                .email(email)
                .password(password)
                .newEmail(newEmail)
                .build();
        Response response = Email.add(requestAddEmail);
        response.then().log().all();

        context.getSuite().setAttribute("email", email);
        context.getSuite().setAttribute("password", password);
        context.getSuite().setAttribute("remove_email", removeEmail);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    void remove(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        String password = (String) context.getSuite().getAttribute("password");
        String removeEmail = (String) context.getSuite().getAttribute("remove_email");
        RequestRemoveEmail requestRemoveEmail = RequestRemoveEmail.builder()
                .email(email)
                .password(password)
                .removeEmail(removeEmail)
                .build();
        Response response = Email.remove(requestRemoveEmail);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
