package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

@Service
public class Password {

    public static Response forgot() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/forgot-password")
                .then()
                .extract().response();

        return response;
    }

    public static Response change() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/change-password")
                .then()
                .extract().response();

        return response;
    }

    public static Response update() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/update-password")
                .then()
                .extract().response();

        return response;
    }
}
