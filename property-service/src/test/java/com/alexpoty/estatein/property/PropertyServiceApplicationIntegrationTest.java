package com.alexpoty.estatein.property;

import com.alexpoty.estatein.property.model.Property;
import com.alexpoty.estatein.property.repository.PropertyRepository;
import io.restassured.RestAssured;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.flyway.clean-disabled=false")
class PropertyServiceApplicationIntegrationTest {

    private final String BASE_ENDPOINT = "/api/property";
    private final String JSON_CONTENT = "application/json";

    @LocalServerPort
    private Integer port;

    @ServiceConnection
    static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0");

    @Autowired
    private PropertyRepository propertyRepository;

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp(@Autowired Flyway flyway) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Should return list of properties")
    void shouldReturn_listOfProperties_statusCode200() {
        // Arrange
        Property property = createTestProperty();
        propertyRepository.save(property);
        // Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .when()
                .get(BASE_ENDPOINT)
                .then()
                .statusCode(200)
                .body(".", Matchers.hasSize(1))
                .body("[0].title", Matchers.is(property.getTitle()));
    }

    @Test
    @DisplayName("Should find property By Id")
    void shouldFindByIdAndReturn_propertyResponse() {
        // Arrange
        Property property = createTestProperty();
        propertyRepository.save(property);
        //Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .when()
                .get(BASE_ENDPOINT + "/1")
                .then()
                .statusCode(200)
                .body("title", Matchers.is(property.getTitle()))
                .body("location", Matchers.is(property.getLocation()));
    }

    @Test
    @DisplayName("Should create property and then return it")
    void shouldCreatePropertyAndReturn_propertyResponse() {
        //Arrange
        String request = """
                {
                  "title": "Seaside Serenity Villa",
                  "description": "Discover your own piece of paradise with the Seaside Serenity Villa",
                  "location": "Malibu",
                  "price": 500000,
                  "area": "2,500 Square Feet"
                }
                """;
        // Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .body(request)
                .post(BASE_ENDPOINT)
                .then()
                .statusCode(201)
                .body("title", Matchers.is("Seaside Serenity Villa"));
    }

    @Test
    @DisplayName("Should update property and then return it")
    void shouldUpdatePropertyAndReturn_propertyResponse() {
        // Arrange
        propertyRepository.save(createTestProperty());
        String request = """
                {
                  "title": "Seaside Serenity Villa",
                  "description": "Discover your own piece of paradise with the Seaside Serenity Villa",
                  "location": "Malibu",
                  "price": 500000,
                  "area": "2,500 Square Feet"
                }
                """;
        //Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .body(request)
                .put(BASE_ENDPOINT + "/1")
                .then()
                .statusCode(200)
                .body("title", Matchers.is("Seaside Serenity Villa"));
    }

    @Test
    @DisplayName("Should delete Property")
    void shouldDeletePropertyAndReturn_propertyResponse() {
        // Arrange
        propertyRepository.save(createTestProperty());
        // Act
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .delete(BASE_ENDPOINT + "/1")
                .then()
                .statusCode(204);

        // Assert
        assertTrue(propertyRepository.findAll().isEmpty());
    }

    private Property createTestProperty() {
        return Property.builder()
                .title("Test")
                .location("test")
                .area("test")
                .build();
    }
}