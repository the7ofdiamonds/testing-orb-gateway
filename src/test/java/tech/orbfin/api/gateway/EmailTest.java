package tech.orbfin.api.gateway;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Email;
import tech.orbfin.api.gateway.payload.RequestAddEmail;
import tech.orbfin.api.gateway.payload.RequestRemoveEmail;
import tech.orbfin.api.gateway.payload.RequestVerify;

public class EmailTest {
// Link to create user
//    @Test
//    void verify(String email, String confirmationCode) {
//        RequestVerify requestVerify = new RequestVerify();
//        requestVerify.setEmail(email);
//        requestVerify.setConfirmationCode(confirmationCode);
//        Response response = Email.verify(requestVerify);
//        response.then().log().all();
//
//        Assert.assertEquals(response.getStatusCode(), 200);
//    }
// Link to create user
    @Test
    void add(
            String email,
            String password,
            String newEmail) {
        RequestAddEmail requestAddEmail = RequestAddEmail.builder()
                .email(email)
                .password(password)
                .newEmail(newEmail)
                .build();
        Response response = Email.add(requestAddEmail);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
    @Test
    void remove(
            String email,
            String password,
            String removeEmail) {
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
