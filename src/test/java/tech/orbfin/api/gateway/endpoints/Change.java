package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

@Service
public class Change {

    public static Response username() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/change-username")
                .then()
                .extract().response();

        return response;
    }

    public static Response name() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/change-name")
                .then()
                .extract().response();

        return response;
    }

    public static Response phone() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/change-phone")
                .then()
                .extract().response();

        return response;
    }
}
