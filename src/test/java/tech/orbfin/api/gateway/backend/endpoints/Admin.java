package tech.orbfin.api.gateway.backend.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

import tech.orbfin.api.gateway.payload.RequestAdmin;

@Service
public class Admin {

    public static Response lock(RequestAdmin requestAdmin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAdmin)
                .when()
                .post("/admin/details/lock-account")
                .then()
                .extract().response();

        return response;
    }

    public static Response expireAccount(RequestAdmin requestAdmin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAdmin)
                .when()
                .post("/admin/details/expire-account")
                .then()
                .extract().response();

        return response;
    }

    public static Response expireCredentials(RequestAdmin requestAdmin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAdmin)
                .when()
                .post("/admin/details/expire-credentials")
                .then()
                .extract().response();

        return response;
    }

    public static Response disable(RequestAdmin requestAdmin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAdmin)
                .when()
                .post("/admin/details/disable-account")
                .then()
                .extract().response();

        return response;
    }

    public static Response delete(RequestAdmin requestAdmin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAdmin)
                .when()
                .post("/admin/account/delete")
                .then()
                .extract().response();

        return response;
    }
}
