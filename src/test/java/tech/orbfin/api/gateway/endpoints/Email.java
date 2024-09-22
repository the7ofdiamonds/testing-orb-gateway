package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

@Service
public class Email {

    public static Response verify() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/verify-email")
                .then()
                .extract().response();

        return response;
    }

    public static Response add() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/add-email")
                .then()
                .extract().response();

        return response;
    }

    public static Response remove() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/remove-email")
                .then()
                .extract().response();

        return response;
    }
}
