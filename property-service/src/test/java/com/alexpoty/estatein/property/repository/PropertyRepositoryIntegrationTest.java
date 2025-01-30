package com.alexpoty.estatein.property.repository;

import com.alexpoty.estatein.property.model.Property;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.flyway.clean-disabled=false")
@RequiredArgsConstructor
class PropertyRepositoryIntegrationTest {

    @ServiceConnection
    static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0");

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property;

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
        property = Property.builder()
                .title("Test Property")
                .location("Test Address")
                .description("Test Description")
                .price(new BigDecimal("100.00"))
                .area("Test Area")
                .build();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Should save property and then return it")
    void shouldSave_property_returnProperty() {
        //Arrange and Act
        var dbProperty = propertyRepository.save(property);
        var actualProperty = propertyRepository.findById(dbProperty.getId()).orElseThrow();

        //Assert
        assertThat(property.getTitle()).isEqualTo(actualProperty.getTitle());
        assertThat(property.getLocation()).isEqualTo(actualProperty.getLocation());
    }

    @Test
    @DisplayName("Should return list of all properties")
    void shouldReturn_ListOfProperties() {
        //Arrange
        assertDoesNotThrow(() -> {
            propertyRepository.save(property);
        });

        //Act
        List<Property> properties = propertyRepository.findAll();

        //Assert
        assertThat(properties).isNotNull();
        assertThat(properties.size()).isEqualTo(1);
        assertThat(properties.get(0)).isNotNull();
        assertThat(properties.get(0).getTitle()).isEqualTo(property.getTitle());
    }

    @Test
    @DisplayName("Should find entity by id and then return it")
    void shouldFindPropertyById_ReturnProperty() {
        //Arrange
        propertyRepository.save(property);

        //Act
        Property retrievedProperty = propertyRepository.findById(1L).orElseThrow();

        //Assert
        assertThat(retrievedProperty.getTitle()).isEqualTo(property.getTitle());
        assertThat(retrievedProperty.getDescription()).isEqualTo(property.getDescription());
    }

    @Test
    @DisplayName("Should update entity and then return it")
    void shouldUpdateProperty_ReturnUpdatedProperty() {
        // Arrange
        propertyRepository.save(property);
        Property updatedProperty = Property.builder()
                .id(property.getId())
                .title("Updated Title")
                .description("Updated Description")
                .location("Updated Location")
                .price(new BigDecimal("10000"))
                .area("Updated Area")
                .build();

        // Act
        Property resultProperty = propertyRepository.save(updatedProperty);

        // Assert
        assertThat(resultProperty.getTitle()).isEqualTo(updatedProperty.getTitle());
    }

    @Test
    @DisplayName("Should delete entity by id")
    void shouldDeletePropertyById() {
        //Arrange
        var expectedProperty = propertyRepository.save(property);
        //Act
        propertyRepository.deleteById(expectedProperty.getId());
        //Assert
        assertFalse(propertyRepository.existsById(expectedProperty.getId()));
    }
}