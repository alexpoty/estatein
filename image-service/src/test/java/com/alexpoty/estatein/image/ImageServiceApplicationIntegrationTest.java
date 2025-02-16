package com.alexpoty.estatein.image;

import com.alexpoty.estatein.image.model.Image;
import com.alexpoty.estatein.image.respository.ImageRepository;
import com.alexpoty.estatein.image.service.CloudinaryServiceImpl;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.flyway.clean-disabled=false"
)
class ImageServiceApplicationIntegrationTest {

    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0");
    private final String BASE_ENDPOINT = "/api/image";
    private final String JSON_CONTENT = "application/json";
    @LocalServerPort
    private Integer port;
    @MockitoBean
    private CloudinaryServiceImpl cloudinaryService;
    @Autowired
    private ImageRepository imageRepository;

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
    void shouldGetImageByPropertyId() {
        // Arrange
        imageRepository.save(createImage());
        // Act And Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .when()
                .get("/api/image?propertyId=1")
                .then()
                .statusCode(200)
                .body("propertyId", Matchers.is(1))
                .body("imageUrl", Matchers.is("test/url"));
    }

    @Test
    void shouldGetAllImagesByPropertyId() {
        // Arrange
        imageRepository.save(createImage());
        // Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .when()
                .get(BASE_ENDPOINT + "/1")
                .then()
                .statusCode(200)
                .body(".", Matchers.hasSize(1))
                .body("[0].propertyId", Matchers.is(1));
    }

    @Test
    void shouldCreateImage() {
        // Arrange
        String request = """
                {
                    "imageUrl": "test/url",
                    "propertyId": 1
                }
                """;
        // Act and Assert
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .body(request)
                .post(BASE_ENDPOINT)
                .then()
                .statusCode(201)
                .body("propertyId", Matchers.is(1));
    }

    @Test
    void shouldDeleteImage() {
        // Arrange
        imageRepository.save(createImage());
        // Act
        RestAssured.given()
                .contentType(JSON_CONTENT)
                .delete(BASE_ENDPOINT + "/1")
                .then()
                .statusCode(204);
        // Assert
        assertTrue(imageRepository.findAll().isEmpty());
    }

    @Test
    void shouldUploadImage() {
        // Arrange
        byte[] fileContent = "dummy content".getBytes(); // The content of the file
        String fileName = "test.jpg";
        when(cloudinaryService.uploadFile(any(MultipartFile.class))).thenReturn("http://cloudinary.com/image_url");

        RestAssured.given()
                .multiPart("file", fileName, fileContent, "image/jpeg")
                .param("propertyId", 1)
                .when()
                .post(BASE_ENDPOINT + "/upload")
                .then()
                .statusCode(201)
                .body("url", Matchers.containsString("http://cloudinary.com/image_url"));
        verify(cloudinaryService, times(1)).uploadFile(any(MultipartFile.class));
    }

    private Image createImage() {
        return Image.builder()
                .imageUrl("test/url")
                .propertyId(1L)
                .build();
    }
}