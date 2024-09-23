package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import tech.orbfin.api.gateway.configurations.ConfigAPI;
import tech.orbfin.api.gateway.payload.RequestAddEmail;
import tech.orbfin.api.gateway.payload.RequestRemoveEmail;
import tech.orbfin.api.gateway.payload.RequestVerify;

@Service
public class Email {

    public static Response verify(RequestVerify requestVerify) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestVerify)
                .when()
                .post("/verify-email")
                .then()
                .extract().response();

        return response;
    }

    public static Response add(RequestAddEmail requestAddEmail) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAddEmail)
                .when()
                .post("/add-email")
                .then()
                .extract().response();

        return response;
    }

    public static Response remove(RequestRemoveEmail requestRemoveEmail) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestRemoveEmail)
                .when()
                .post("/remove-email")
                .then()
                .extract().response();

        return response;
    }
}
