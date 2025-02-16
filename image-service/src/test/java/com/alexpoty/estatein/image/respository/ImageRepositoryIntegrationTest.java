package com.alexpoty.estatein.image.respository;

import com.alexpoty.estatein.image.model.Image;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.flyway.clean-disabled=false")
@RequiredArgsConstructor
class ImageRepositoryIntegrationTest {

    @ServiceConnection
    static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0");

    @Autowired
    private ImageRepository imageRepository;

    private Image image;

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
        image = Image.builder()
                .imageUrl("test/url")
                .propertyId(1L)
                .build();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldFindAllImagesByPropertyId() {
        // Arrange
        imageRepository.save(image);
        // Act
        List<Image> actual = imageRepository.findAllByPropertyId(1L);
        // Assert
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getImageUrl()).isEqualTo(image.getImageUrl());
    }

    @Test
    void shouldFindFirstByPropertyId() {
        // Arrange
        imageRepository.save(image);
        // Act
        Image actual = imageRepository.findFirstByPropertyId(1L);
        // Assert
        assertThat(actual.getImageUrl()).isEqualTo(image.getImageUrl());
        assertThat(actual.getPropertyId()).isEqualTo(1L);
    }
}