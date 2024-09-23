package tech.orbfin.api.gateway.endpoints;

import org.springframework.stereotype.Service;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import tech.orbfin.api.gateway.configurations.ConfigAPI;

import tech.orbfin.api.gateway.payload.RequestDeleteAccount;

@Service
public class Admin {

    public static Response deleteAccount(RequestDeleteAccount requestDeleteAccount) {

        Response response = given()
                .contentType("application/json")
                .baseUri(ConfigAPI.BASE_URI)
                .body(requestDeleteAccount)
                .when()
                .get("/delete-account")
                .then()
                .extract().response();

        return response;
    }
}
