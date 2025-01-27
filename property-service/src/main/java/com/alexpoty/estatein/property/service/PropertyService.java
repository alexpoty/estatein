package com.alexpoty.estatein.property.service;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;

import java.util.List;

public interface PropertyService {

    PropertyResponse getProperty(Long id);
    List<PropertyResponse> getAllProperties();
    PropertyResponse createProperty(PropertyRequest propertyRequest);
    PropertyResponse updateProperty(Long id, PropertyRequest propertyRequest);
    void deletePropertyById(Long id);
}
