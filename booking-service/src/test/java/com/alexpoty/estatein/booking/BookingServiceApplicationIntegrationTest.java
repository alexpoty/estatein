package com.alexpoty.estatein.booking;

import com.alexpoty.estatein.booking.model.Booking;
import com.alexpoty.estatein.booking.repository.BookingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.flyway.clean-disabled=false"
)
class BookingServiceApplicationIntegrationTest {

    private final String BASE_ENDPOINT = "/api/booking";
    private final String JSON_CONTENT = "application/json";

    @LocalServerPort
    private Integer port;
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0");

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @BeforeEach
    void setUp(@Autowired Flyway flyway) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldFindAllBookings() {
        // Arrange
        Booking booking = createBooking();
        bookingRepository.save(booking);
        // Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .when()
                .get(BASE_ENDPOINT)
                .then()
                .statusCode(200)
                .body(".", Matchers.hasSize(1))
                .body("[0].phone", Matchers.is(booking.getPhone()));
    }

    @Test
    void shouldFindBookingById() {
        // Arrange
        Booking booking = createBooking();
        bookingRepository.save(booking);
        // Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .when()
                .get(BASE_ENDPOINT + "/" + 1)
                .then()
                .statusCode(200)
                .body("phone", Matchers.is(booking.getPhone()));
    }

    @Test
    void shouldCreateBooking() throws JsonProcessingException {
        // Arrange
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .body(objectMapper.writeValueAsString(createBooking()))
                .when()
                .post(BASE_ENDPOINT)
                .then()
                .statusCode(201)
                .body("phone", Matchers.is("test"));
    }

    @Test
    void shouldUpdateBooking() throws JsonProcessingException {
        // Arrange
        Booking updatedBooking = bookingRepository.save(createBooking());
        updatedBooking.setPhone("updated");
        // Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .body(objectMapper.writeValueAsString(updatedBooking))
                .when()
                .put(BASE_ENDPOINT + "/" + updatedBooking.getId())
                .then()
                .statusCode(200)
                .body("phone", Matchers.is(updatedBooking.getPhone()));
    }

    @Test
    void shouldDeleteBooking() {
        // Arrange
        bookingRepository.save(createBooking());
        // Act and assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .when()
                .delete(BASE_ENDPOINT + "/" + 1)
                .then()
                .statusCode(204);
    }

    private Booking createBooking() {
        return Booking.builder()
                .phone("test")
                .name("test")
                .surname("test")
                .propertyId(1L)
                .build();
    }
}