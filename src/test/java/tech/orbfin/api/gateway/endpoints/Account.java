package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

import tech.orbfin.api.gateway.payload.RequestActivateAccount;
import tech.orbfin.api.gateway.payload.RequestSignup;
import tech.orbfin.api.gateway.payload.RequestVerify;

@Service
public class Account {

    public static Response create(RequestSignup requestSignup) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestSignup)
                .when()
                .post("/signup")
                .then()
                .extract().response();

        return response;
    }

    public static Response activate(RequestActivateAccount requestActivateAccount) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestActivateAccount)
                .when()
                .post("/account/activate")
                .then()
                .extract().response();

        return response;
    }

    public static Response lock(RequestVerify requestVerify) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestVerify)
                .when()
                .post("/account/lock")
                .then()
                .extract().response();

        return response;
    }

    public static Response unlock(RequestVerify requestVerify) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestVerify)
                .when()
                .post("/account/unlock")
                .then()
                .extract().response();

        return response;
    }

    public static Response remove(RequestVerify requestVerify) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestVerify)
                .when()
                .post("/account/remove")
                .then()
                .extract().response();

        return response;
    }

    public static Response enable(RequestVerify requestVerify) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestVerify)
                .when()
                .post("/account/enable")
                .then()
                .extract().response();

        return response;
    }
}
