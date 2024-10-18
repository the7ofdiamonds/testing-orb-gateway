package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

import tech.orbfin.api.gateway.payload.RequestActivateAccount;
import tech.orbfin.api.gateway.payload.RequestSignup;

import java.util.Map;

@Service
public class Account {

    public static Response create(RequestSignup requestSignup) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestSignup)
            .when()
                .post("/account/create")
            .then()
                .log().all()
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

    public static Response lock(Map<String, String> headers) {

        Response response = given()
                .headers(headers)
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/account/lock")
                .then()
                .extract().response();

        return response;
    }

    public static Response unlock(RequestActivateAccount requestActivateAccount) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestActivateAccount)
                .when()
                .post("/account/unlock")
                .then()
                .extract().response();

        return response;
    }

    public static Response recover(RequestActivateAccount requestActivateAccount) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestActivateAccount)
                .when()
                .post("/account/recovery")
                .then()
                .extract().response();

        return response;
    }
}
