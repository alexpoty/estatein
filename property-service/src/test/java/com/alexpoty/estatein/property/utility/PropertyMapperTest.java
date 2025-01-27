package com.alexpoty.estatein.property.utility;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.exception.PropertyNotFoundException;
import com.alexpoty.estatein.property.model.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropertyMapperTest {

    private PropertyRequest propertyRequest;
    private Property property;

    @BeforeEach
    void setUp() {
        propertyRequest = new PropertyRequest(1L, "Test", "test",
                "testLocation", new BigDecimal(20), "testArea");
        property = Property.builder()
                .id(propertyRequest.id())
                .title(propertyRequest.title())
                .description(propertyRequest.description())
                .location(propertyRequest.location())
                .price(propertyRequest.price())
                .area(propertyRequest.area())
                .build();
    }

    @Test
    @DisplayName("Should convert model to property request")
    void shouldConvertToModel_PropertyRequest() {
        // Arrange
        Property testProperty = PropertyMapper.convertToModel(propertyRequest);
        // Act and Assert
        assertThat(testProperty.getId()).isEqualTo(propertyRequest.id());
        assertThat(testProperty.getTitle()).isEqualTo(propertyRequest.title());
    }

    @Test
    @DisplayName("Should throw an exception if property is null")
    void shouldThrow_propertyRequestNull_PropertyNotFoundException() {
        // Act and Assert
        assertThrows(PropertyNotFoundException.class, () -> PropertyMapper.convertToModel(null));
    }

    @Test
    @DisplayName("Should convert entity to Response")
    void shouldConvertToResponse_Property() {
        // Arrange and Act
        PropertyResponse testResponse = PropertyMapper.convertToResponse(property);
        //Assert
        assertThat(testResponse.id()).isEqualTo(property.getId());
        assertThat(testResponse.title()).isEqualTo(property.getTitle());

    }

    @Test
    @DisplayName("Should throw an exception if entity not found")
    void shouldThrow_propertyNull_PropertyNotFoundException() {
        // Act
        assertThrows(PropertyNotFoundException.class, () -> PropertyMapper.convertToResponse(null));
    }
}