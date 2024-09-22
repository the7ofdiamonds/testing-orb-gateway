package tech.orbfin.api.gateway;

import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

import org.testng.Assert;

import tech.orbfin.api.gateway.endpoints.Password;

public class PasswordTest {
// Link to create user
    @Test
    void forgot() {
        Response response = Password.forgot();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
    @Test
    void change() {
        Response response = Password.change();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
    @Test
    void update() {
        Response response = Password.update();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
