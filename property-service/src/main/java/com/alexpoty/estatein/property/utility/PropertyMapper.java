package com.alexpoty.estatein.property.utility;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.model.Property;

public class PropertyMapper {

    public static Property convertToModel(PropertyRequest propertyRequest) {
        return Property.builder()
                .id(propertyRequest.id())
                .title(propertyRequest.title())
                .description(propertyRequest.description())
                .location(propertyRequest.location())
                .price(propertyRequest.price())
                .area(propertyRequest.area())
                .build();
    }

    public static PropertyResponse convertToResponse(Property property) {
        return new PropertyResponse(
                property.getId(),
                property.getTitle(),
                property.getDescription(),
                property.getLocation(),
                property.getPrice(),
                property.getArea()
        );
    }
}
