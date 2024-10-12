package tech.orbfin.api.gateway;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import tech.orbfin.api.gateway.endpoints.Admin;

import tech.orbfin.api.gateway.payload.*;

import tech.orbfin.api.gateway.utilities.DataProviders;

@Slf4j
public class AdminTest {

    @Test(priority = 1, dataProvider = "Admin", dataProviderClass = DataProviders.class)
    void lock(String email, ITestContext context) {
        RequestAdmin requestAdmin = new RequestAdmin(email);
        Response response = Admin.lock(requestAdmin);
        response.then().log().all();

        context.getSuite().setAttribute("email", requestAdmin.getEmail());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    void remove(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        RequestAdmin requestAdmin = new RequestAdmin(email);
        Response response = Admin.remove(requestAdmin);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    void delete(ITestContext context) {
        String email = (String) context.getSuite().getAttribute("email");
        RequestAdmin requestAdmin = new RequestAdmin((String) email);
        Response response = Admin.delete(requestAdmin);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
