package com.alexpoty.estatein.property.controller;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Property Api", description = "Endpoints for managing properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Operation(summary = "Get a list of properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found a property",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropertyResponse.class))})
    })
    @GetMapping
    public ResponseEntity<List<PropertyResponse>> getAllProperties() {
        log.info("PropertyController::getAllProperties");
        return new ResponseEntity<>(propertyService.getAllProperties(), HttpStatus.OK);
    }

    @Operation(summary = "Get a page of properties", description = "Retrieves a page of properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    @GetMapping("/page")
    public ResponseEntity<Page<PropertyResponse>> getPageOfProperties(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "9") int size) {
        return new ResponseEntity<>(propertyService.getPageOfProperties(page, size), HttpStatus.OK);
    }

    @Operation(summary = "Find a property by its Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found a property",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropertyResponse.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> getProperty(@Parameter(description = "id of property to be searched")
                                                        @PathVariable("id") Long id) {
        log.info("PropertyController::getProperty");
        return new ResponseEntity<>(propertyService.getProperty(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Property Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropertyResponse.class))})
    })
    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Property To Create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PropertyRequest.class))
    ) @RequestBody PropertyRequest propertyRequest) {
        log.info("PropertyController::createProperty");
        return new ResponseEntity<>(propertyService.createProperty(propertyRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Property Updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropertyResponse.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponse> updateProperty(
            @Parameter(description = "Id of updated property")
            @PathVariable("id") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Property to Update", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropertyRequest.class))
            )
            @RequestBody PropertyRequest propertyRequest) {
        log.info("PropertyController::updateProperty");
        return new ResponseEntity<>(propertyService.updateProperty(id, propertyRequest), HttpStatus.OK);
    }

    @Operation(summary = "Delete Property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Property Deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropertyResponse.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropertyById(@Parameter(description = "Id of property to delete")
                                                   @PathVariable("id") Long id) {
        log.info("PropertyController::deleteProperty");
        propertyService.deletePropertyById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
