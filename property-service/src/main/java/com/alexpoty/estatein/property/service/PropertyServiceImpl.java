package com.alexpoty.estatein.property.service;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.exception.PropertyAlreadyExistsException;
import com.alexpoty.estatein.property.exception.PropertyNotFoundException;
import com.alexpoty.estatein.property.model.Property;
import com.alexpoty.estatein.property.repository.PropertyRepository;
import com.alexpoty.estatein.property.utility.PropertyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    public List<PropertyResponse> getAllProperties() {
        log.info("Starting to retrieve all properties from db");
        return propertyRepository.findAll().stream().map(PropertyMapper::convertToResponse).toList();
    }

    public PropertyResponse getProperty(Long id) {
        log.info("Starting to retrieve property by id");
        return propertyRepository.findById(id).map(PropertyMapper::convertToResponse).orElseThrow(
                () -> new PropertyNotFoundException("Property not found " + id)
        );
    }

    public PropertyResponse createProperty(PropertyRequest propertyRequest) {
        log.info("Checking if property exists or not");
        if (propertyRepository.existsById(propertyRequest.id()))
            throw new PropertyAlreadyExistsException("Property with id " + propertyRequest.id() + " already exists");
        log.info("Starting to create property");
        return PropertyMapper.convertToResponse(propertyRepository
                .save(PropertyMapper.convertToModel(propertyRequest)));
    }

    public PropertyResponse updateProperty(Long id, PropertyRequest propertyRequest) {
        log.info("Validating if property exists in db");
        if (!propertyRepository.existsById(id)) throw new PropertyNotFoundException("Property not found " + id);
        log.info("Assigning property id");
        Property property = PropertyMapper.convertToModel(propertyRequest);
        property.setId(id);
        log.info("Updating property");
        return PropertyMapper.convertToResponse(propertyRepository.save(property));
    }

    public void deletePropertyById(Long id) {
        log.info("Validating if property exists by id");
        if (!propertyRepository.existsById(id)) throw new PropertyNotFoundException("Property not found " + id);
        log.info("Deleting property");
        propertyRepository.deleteById(id);
    }

}
