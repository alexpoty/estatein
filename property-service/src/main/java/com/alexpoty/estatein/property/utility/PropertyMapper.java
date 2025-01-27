package com.alexpoty.estatein.property.utility;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.exception.PropertyNotFoundException;
import com.alexpoty.estatein.property.model.Property;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyMapper {

    public static Property convertToModel(PropertyRequest propertyRequest) {
        if (propertyRequest == null) throw new PropertyNotFoundException("Property Not Found");
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
        if (property == null) throw new PropertyNotFoundException("Property not found");
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
