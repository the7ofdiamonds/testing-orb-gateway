package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

import tech.orbfin.api.gateway.payload.RequestChangePassword;
import tech.orbfin.api.gateway.payload.RequestForgot;
import tech.orbfin.api.gateway.payload.RequestUpdatePassword;

import java.util.Map;

@Service
public class Password {

    public static Response forgot(RequestForgot requestForgot) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestForgot)
                .when()
                .post("/password/forgot")
                .then()
                .extract().response();

        return response;
    }

    public static Response change(Map<String, String> headers, RequestChangePassword requestChangePassword) {

        Response response = given()
                .contentType("application/json")
                .headers(headers)
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestChangePassword)
                .when()
                .post("/password/change")
                .then()
                .extract().response();

        return response;
    }

    public static Response update(RequestUpdatePassword requestUpdatePassword) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestUpdatePassword)
                .when()
                .post("/password/update")
                .then()
                .extract().response();

        return response;
    }
}
