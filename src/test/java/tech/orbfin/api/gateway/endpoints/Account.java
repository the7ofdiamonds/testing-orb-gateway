package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

import tech.orbfin.api.gateway.payload.RequestRemoveAccount;
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

    public static Response lock() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/lock-account")
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
                .post("/unlock-account")
                .then()
                .extract().response();

        return response;
    }

    public static Response remove(RequestRemoveAccount requestRemoveAccount) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestRemoveAccount)
                .when()
                .post("/remove-account")
                .then()
                .extract().response();

        return response;
    }
}
