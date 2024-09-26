package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

import tech.orbfin.api.gateway.payload.RequestLogin;
import tech.orbfin.api.gateway.payload.RequestLogout;
import tech.orbfin.api.gateway.payload.RequestLogoutAll;

import java.util.HashMap;
import java.util.Map;

@Service
public class Auth {

    public static Response login(RequestLogin requestLogin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestLogin)
                .when()
                .post("/")
                .then()
                .extract().response();

        return response;
    }

    public static Response logout(RequestLogout requestLogout) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + requestLogout.getAccessToken());
        headers.put("Refresh-Token", requestLogout.getRefreshToken());

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .headers(headers)
                .body(requestLogout)
                .when()
                .post("/logout")
                .then()
                .extract().response();

        return response;
    }

    public static Response logoutAll(RequestLogoutAll requestLogoutAll) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestLogoutAll)
                .when()
                .post("/logout-all")
                .then()
                .extract().response();

        return response;
    }
}
