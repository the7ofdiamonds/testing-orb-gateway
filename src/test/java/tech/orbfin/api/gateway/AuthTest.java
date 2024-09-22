package tech.orbfin.api.gateway;

import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

import org.testng.Assert;

import tech.orbfin.api.gateway.endpoints.Auth;

public class AuthTest {

    @Test
    void login() {
        Response response = Auth.login();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

//    Link to login for auth header
    @Test
    void logout() {
        Response response = Auth.logout();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

//    Link to login for auth header
    @Test
    void logoutAll() {
        Response response = Auth.logoutAll();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
