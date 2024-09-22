package tech.orbfin.api.gateway;

import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

import org.testng.Assert;

import tech.orbfin.api.gateway.endpoints.Email;

public class EmailTest {
// Link to create user
    @Test
    void verify() {
        Response response = Email.verify();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
    @Test
    void add() {
        Response response = Email.add();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// Link to create user
    @Test
    void remove() {
        Response response = Email.remove();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
