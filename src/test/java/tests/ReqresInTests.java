package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void successfulRegistrationTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .contentType(JSON)
                .body(body)
                .log().uri()
                .log().body()
                .when()
                .post("/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successfulUpdateTest() {
        String body = "{ \"name\": \"neo\", \"job\": \"hacker\" }";

        given()
                .contentType(JSON)
                .body(body)
                .log().uri()
                .log().body()
                .when()
                .patch("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("neo"), "job", is("hacker"));
    }

    @Test
    void successfulListTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("/api/unknown")
                .then()
                .log().status()
                .log().body()
                .body("data[0].id", is(1));
    }

    @Test
    void negativeResourceTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("/api/unknown/23")
                .then()
                .log().status()
                .log().body()
                .body("isEmpty()", is(true));
    }

    @Test
    void negativeRegistrationTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\" }";
        given()
                .contentType(JSON)
                .body(body)
                .log().uri()
                .log().body()
                .when()
                .post("/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
