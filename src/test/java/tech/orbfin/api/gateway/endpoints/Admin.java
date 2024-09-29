package tech.orbfin.api.gateway.endpoints;

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
                .post("/admin/account/lock")
                .then()
                .extract().response();

        return response;
    }

    public static Response unlock(RequestAdmin requestAdmin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAdmin)
                .when()
                .post("/admin/account/unlock")
                .then()
                .extract().response();

        return response;
    }

    public static Response remove(RequestAdmin requestAdmin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAdmin)
                .when()
                .post("/admin/account/remove")
                .then()
                .extract().response();

        return response;
    }

    public static Response enable(RequestAdmin requestAdmin) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestAdmin)
                .when()
                .post("/admin/account/enable")
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
