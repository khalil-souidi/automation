package com.khalil.qa.api;

import com.khalil.qa.utils.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/** CRUD complet sur restful-booker : les tests s'enchaînent (création -> lecture -> MAJ -> suppression). */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("api")
class BookingApiTest {

    private static String token;
    private static int bookingId;

    private static Map<String, Object> booking(String firstname, int price) {
        return Map.of(
                "firstname", firstname,
                "lastname", "QA",
                "totalprice", price,
                "depositpaid", true,
                "bookingdates", Map.of("checkin", "2026-11-01", "checkout", "2026-11-05"),
                "additionalneeds", "Breakfast");
    }

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = Config.get("api.baseUrl");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        token = given().contentType(ContentType.JSON)
                .body(Map.of("username", Config.get("api.username"), "password", Config.get("api.password")))
                .when().post("/auth")
                .then().statusCode(200).body("token", notNullValue())
                .extract().path("token");
    }

    @Test @Order(1)
    void healthCheck() {
        given().when().get("/ping").then().statusCode(201);
    }

    @Test @Order(2)
    void createBooking() {
        bookingId = given().contentType(ContentType.JSON).header("Accept", "application/json")
                .body(booking("Khalil", 100))
                .when().post("/booking")
                .then().statusCode(200)
                .body("bookingid", greaterThan(0))
                .body("booking.firstname", equalTo("Khalil"))
                .time(lessThan(5000L))
                .extract().path("bookingid");
    }

    @BeforeEach
    void requireBooking(TestInfo info) {
        // Si la création a échoué, on saute les tests dépendants au lieu de les faire échouer en cascade
        if (!info.getDisplayName().startsWith("healthCheck") && !info.getDisplayName().startsWith("createBooking")) {
            Assumptions.assumeTrue(bookingId > 0, "Booking non créé - test ignoré");
        }
    }

    @Test @Order(3)
    void getBooking() {
        given().header("Accept", "application/json")
                .when().get("/booking/{id}", bookingId)
                .then().statusCode(200)
                .body("firstname", equalTo("Khalil"))
                .body("totalprice", equalTo(100));
    }

    @Test @Order(4)
    void updateBooking() {
        given().contentType(ContentType.JSON).header("Accept", "application/json")
                .cookie("token", token)
                .body(booking("Khalil", 250))
                .when().put("/booking/{id}", bookingId)
                .then().statusCode(200)
                .body("totalprice", equalTo(250));
    }

    @Test @Order(5)
    void updateWithoutTokenIsForbidden() {
        given().contentType(ContentType.JSON).header("Accept", "application/json")
                .body(booking("Hacker", 1))
                .when().put("/booking/{id}", bookingId)
                .then().statusCode(403);
    }

    @Test @Order(6)
    void deleteBooking() {
        given().cookie("token", token)
                .when().delete("/booking/{id}", bookingId)
                .then().statusCode(201);

        given().when().get("/booking/{id}", bookingId).then().statusCode(404);
    }
}