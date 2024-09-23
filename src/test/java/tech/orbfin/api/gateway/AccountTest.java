package tech.orbfin.api.gateway;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import tech.orbfin.api.gateway.endpoints.Admin;
import tech.orbfin.api.gateway.endpoints.Account;
import tech.orbfin.api.gateway.payload.Location;
import tech.orbfin.api.gateway.payload.RequestSignup;
import tech.orbfin.api.gateway.utilities.DataProviders;

public class AccountTest {

    @Test(dataProvider="RequestSignup", dataProviderClass= DataProviders.class)
    public void create(
            String username,
            String email,
            String password,
            String confirmPassword,
            String nicename,
            String nickname,
            String firstname,
            String lastname,
            String phone,
            Location location,
            String ip,
            String userAgent) {
        RequestSignup requestSignup = RequestSignup.builder()
            .username(username)
            .email(email)
            .password(password)
            .confirmPassword(confirmPassword)
            .nicename(nicename)
            .nickname(nickname)
            .firstname(firstname)
            .lastname(lastname)
            .phone(phone)
            .location(location)
            .ip(ip)
            .userAgent(userAgent)
            .build();
        Response response = Account.create(requestSignup);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
//// link to create using userID
//    @Test
//    void lock() {
//        Response response = Account.lock();
//        response.then().log().all();
//
//        Assert.assertEquals(response.getStatusCode(), 200);
//    }
//// link to create using userID
//    @Test
//    void unlock() {
//        Response response = Account.unlock();
//        response.then().log().all();
//
//        Assert.assertEquals(response.getStatusCode(), 200);
//    }
//// link to create using userID
//    @Test
//    void remove() {
//        Response response = Account.remove();
//        response.then().log().all();
//
//        Assert.assertEquals(response.getStatusCode(), 200);
//    }
//// link to create using userID
//    @Test
//    void delete() {
//        Response response = Admin.deleteAccount();
//        response.then().log().all();
//
//        Assert.assertEquals(response.getStatusCode(), 200);
//    }
}
