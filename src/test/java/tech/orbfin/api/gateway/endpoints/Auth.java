package tech.orbfin.api.gateway.endpoints;

import io.restassured.response.Response;
import org.springframework.stereotype.Service;
import tech.orbfin.api.gateway.configurations.ConfigAPI;

import static io.restassured.RestAssured.given;

@Service
public class Auth {

    public static Response login() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/")
                .then()
                .extract().response();

        return response;
    }

    public static Response logout() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/logout")
                .then()
                .extract().response();

        return response;
    }

    public static Response logoutAll() {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/logout-all")
                .then()
                .extract().response();

        return response;
    }
}
