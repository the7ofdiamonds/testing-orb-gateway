package tech.orbfin.api.gateway.backend;

import lombok.extern.slf4j.Slf4j;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestContext;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.backend.endpoints.Admin;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.utilities.DataProviders;

@Slf4j
public class AdminTest {

    @Test(priority = 1, dataProvider = "Admin", dataProviderClass = DataProviders.class)
    void lock(String email, ITestContext context) {
        RequestAdmin requestAdmin = new RequestAdmin(email);
        Response response = Admin.lock(requestAdmin);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        context.getSuite().setAttribute("email", email);
    }

    @Test(priority = 2)
    void expireAccount(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        RequestAdmin requestAdmin = new RequestAdmin(email);
        Response response = Admin.expireAccount(requestAdmin);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void expireCredentials(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        RequestAdmin requestAdmin = new RequestAdmin(email);
        Response response = Admin.expireCredentials(requestAdmin);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    void disable(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        RequestAdmin requestAdmin = new RequestAdmin(email);
        Response response = Admin.disable(requestAdmin);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    void delete(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        RequestAdmin requestAdmin = new RequestAdmin(email);
        Response response = Admin.delete(requestAdmin);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
