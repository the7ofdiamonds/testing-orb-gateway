package tech.orbfin.api.gateway.backend.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

import tech.orbfin.api.gateway.payload.RequestLogin;
import tech.orbfin.api.gateway.payload.RequestLogoutAll;

import java.util.Map;

@Service
public class Auth {

    public static Response login(Map<String, String> headers, RequestLogin requestLogin) {

        Response response = given()
                .headers(headers)
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestLogin)
                .when()
                .post("/auth/login")
                .then()
                .extract().response();

        return response;
    }

    public static Response logout(Map<String, String> headers) {

        Response response = given()
                .contentType("application/json")
                .headers(headers)
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/auth/logout")
                .then()
                .extract().response();

        return response;
    }

    public static Response logoutAll(Map<String, String> headers) {

        Response response = given()
                .contentType("application/json")
                .headers(headers)
                .baseUri(ConfigAPI.BASE_URI)
                .when()
                .post("/auth/logout-all")
                .then()
                .extract().response();

        return response;
    }
}
