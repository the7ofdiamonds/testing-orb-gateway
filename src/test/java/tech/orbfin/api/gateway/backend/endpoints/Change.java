package tech.orbfin.api.gateway.backend.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import tech.orbfin.api.gateway.configurations.ConfigAPI;
import tech.orbfin.api.gateway.payload.RequestChangeNicename;
import tech.orbfin.api.gateway.payload.RequestChangeNickname;
import tech.orbfin.api.gateway.payload.RequestChangeName;
import tech.orbfin.api.gateway.payload.RequestChangePhone;
import tech.orbfin.api.gateway.payload.RequestChangeUsername;

import java.util.Map;

@Service
public class Change {

    public static Response username(Map<String, String> headers,
            RequestChangeUsername requestChangeUsername) {

        Response response = given()
                .contentType("application/json")
                .headers(headers)
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestChangeUsername)
                .when()
                .post("/change/username")
                .then()
                .extract().response();

        return response;
    }

    public static Response nicename(Map<String, String> headers,
                                RequestChangeNicename requestChangeNicename) {

        Response response = given()
                .contentType("application/json")
                .headers(headers)
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestChangeNicename)
                .when()
                .post("/change/nicename")
                .then()
                .extract().response();

        return response;
    }

    public static Response nickname(Map<String, String> headers,
                                RequestChangeNickname requestChangeNickname) {

        Response response = given()
                .contentType("application/json")
                .headers(headers)
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestChangeNickname)
                .when()
                .post("/change/nickname")
                .then()
                .extract().response();

        return response;
    }

    public static Response name(Map<String, String> headers,
                                RequestChangeName requestChangeName) {

        Response response = given()
                .contentType("application/json")
                .headers(headers)
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestChangeName)
                .when()
                .post("/change/name")
                .then()
                .extract().response();

        return response;
    }

    public static Response phone(Map<String, String> headers,
                                 RequestChangePhone requestChangePhone) {

        Response response = given()
                .contentType("application/json")
                .headers(headers)
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestChangePhone)
                .when()
                .post("/change/phone")
                .then()
                .extract().response();

        return response;
    }
}
