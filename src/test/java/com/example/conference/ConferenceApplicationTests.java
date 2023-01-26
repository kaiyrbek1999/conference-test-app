package com.example.conference;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class ConferenceApplicationTests {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void login() {
        given()
                .urlEncodingEnabled(true)
                .param("username", "admin@gmail.com")
                .param("password", "test")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .post("/login")
                .then().statusCode(302);
    }

    @Test
    public void getAllConferencesForLoggedUser() {
        given()
                .auth()
                .basic("admin@gmail.com", "test")
                .get("/api/v1/conference")
                .then()
                .statusCode(200);
    }

    @Test
    public void getAllConferencesForLoggedUserWithWrongRole() {
        given()
                .auth()
                .basic("user@gmail.com", "1234")
                .get("/api/v1/conference")
                .then()
                .statusCode(500);
    }
    @Test
    public void getById() {
        given()
                .auth()
                .basic("admin@gmail.com", "test")
                .when()
                .get("/api/v1/conference/2")
                .then()
                .statusCode(200);
    }

    @Test
    public void addParticipant() throws JSONException {
        JSONObject request = new JSONObject();
        request.put("conferenceId", 2);
        request.put("participantEmail", "admin@gmail.com");

        given().contentType(ContentType.JSON)
                .body(request.toString())
                .auth()
                .basic("admin@gmail.com", "test")
                .when()
                .put("/api/v1/conference/add-participant")
                .then()
                .statusCode(200);
    }

    @Test
    public void removeParticipant() throws JSONException {
        JSONObject request = new JSONObject();
        request.put("conferenceId", 2);
        request.put("participantEmail", "admin@gmail.com");

        given().contentType(ContentType.JSON)
                .body(request.toString())
                .auth()
                .basic("admin@gmail.com", "test")
                .when()
                .put("/api/v1/conference/remove-participant")
                .then()
                .statusCode(200);
    }


    @Test
    public void cancel() {
        given()
                .auth()
                .basic("admin@gmail.com", "test")
                .when()
                .get("/api/v1/conference/cancel/2")
                .then()
                .statusCode(200);
    }

    @Test
    public void checkAvailability() {
        given()
                .auth()
                .basic("admin@gmail.com", "test")
                .when()
                .get("/api/v1/conference/check-availability/1")
                .then()
                .statusCode(200);
    }
}