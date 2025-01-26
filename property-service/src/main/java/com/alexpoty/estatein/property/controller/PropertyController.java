package com.alexpoty.estatein.property.controller;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<PropertyResponse>> getAllProperties() {
        log.info("PropertyController::getAllProperties");
        return new ResponseEntity<>(propertyService.getAllProperties(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> getProperty(@PathVariable("id") Long id) {
        log.info("PropertyController::getProperty");
        return new ResponseEntity<>(propertyService.getProperty(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody PropertyRequest propertyRequest) {
        log.info("PropertyController::createProperty");
        return new ResponseEntity<>(propertyService.createProperty(propertyRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponse> updateProperty(@PathVariable("id") Long id,
                                                           @RequestBody PropertyRequest propertyRequest) {
        log.info("PropertyController::updateProperty");
        return new ResponseEntity<>(propertyService.updateProperty(id, propertyRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropertyById(@PathVariable("id") Long id) {
        log.info("PropertyController::deleteProperty");
        propertyService.deletePropertyById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
