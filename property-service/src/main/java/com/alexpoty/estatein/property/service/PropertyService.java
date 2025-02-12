package com.alexpoty.estatein.property.service;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PropertyService {

    PropertyResponse getProperty(Long id);
    Page<PropertyResponse> getPageOfProperties(int page, int size);
    List<PropertyResponse> getAllProperties();
    PropertyResponse createProperty(PropertyRequest propertyRequest);
    PropertyResponse updateProperty(Long id, PropertyRequest propertyRequest);
    void deletePropertyById(Long id);
}
