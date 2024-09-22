package tech.orbfin.api.gateway;

import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import org.testng.Assert;

import tech.orbfin.api.gateway.endpoints.Admin;
import tech.orbfin.api.gateway.endpoints.Account;

public class AccountTest {

    @Test
    void create() {
        Response response = Account.create();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// link to create using userID
    @Test
    void lock() {
        Response response = Account.lock();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// link to create using userID
    @Test
    void unlock() {
        Response response = Account.unlock();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// link to create using userID
    @Test
    void remove() {
        Response response = Account.remove();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
// link to create using userID
    @Test
    void delete() {
        Response response = Admin.deleteAccount();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
