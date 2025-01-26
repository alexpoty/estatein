package com.alexpoty.estatein.property.utility;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.exception.PropertyNotFoundException;
import com.alexpoty.estatein.property.model.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    void shouldConvertToModel_PropertyRequest() {
        Property testProperty = PropertyMapper.convertToModel(propertyRequest);
        assertThat(testProperty.getId()).isEqualTo(propertyRequest.id());
        assertThat(testProperty.getTitle()).isEqualTo(propertyRequest.title());
    }

    @Test
    void shouldThrow_propertyRequestNull_PropertyNotFoundException() {
        assertThrows(PropertyNotFoundException.class, () -> PropertyMapper.convertToModel(null));
    }

    @Test
    void shouldConvertToResponse_Property() {
        PropertyResponse testResponse = PropertyMapper.convertToResponse(property);
        assertThat(testResponse.id()).isEqualTo(property.getId());
        assertThat(testResponse.title()).isEqualTo(property.getTitle());

    }

    @Test
    void shouldThrow_propertyNull_PropertyNotFoundException() {
        assertThrows(PropertyNotFoundException.class, () -> PropertyMapper.convertToResponse(null));
    }


}