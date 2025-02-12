package com.alexpoty.estatein.property.service;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.exception.PropertyAlreadyExistsException;
import com.alexpoty.estatein.property.exception.PropertyNotFoundException;
import com.alexpoty.estatein.property.model.Property;
import com.alexpoty.estatein.property.repository.PropertyRepository;
import com.alexpoty.estatein.property.utility.PropertyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock
    PropertyRepository propertyRepository;
    @InjectMocks
    private PropertyServiceImpl propertyService;

    private final List<Property> properties = new ArrayList<>();
    private Property property;
    private PropertyRequest propertyRequest;

    @BeforeEach
    void setUp() {
        property = Property.builder()
                .id(1L)
                .title("TestProperty")
                .description("TestDescription")
                .location("testLocation")
                .price(new BigDecimal(1111))
                .area("Test area")
                .build();
        properties.add(property);
        propertyRequest = new PropertyRequest(1L,
                "TestProperty",
                "TestDescription",
                "testLocation",
                new BigDecimal(11111),
                "testArea");
    }

    @Test
    @DisplayName("Should return list of property responses")
    void shouldReturn_ListOfProperties_andThenConvertThem_toDTO() {
        // Arrange
        when(propertyRepository.findAll()).thenReturn(properties);
        // Act
        List<PropertyResponse> testProperty = propertyService.getAllProperties();
        // Assert
        assertThat(testProperty.get(0)).isEqualTo(new PropertyResponse(1L, "TestProperty",
                "TestDescription", "testLocation", new BigDecimal(1111), "Test area"));
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnPageOfProperties() {
        // Arrange
        Page<Property> page = new PageImpl<>(properties);
        when(propertyRepository.findAll(any(Pageable.class))).thenReturn(page);
        // Act
        Page<PropertyResponse> actual = propertyService.getPageOfProperties(0,1);
        // Assert
        assertThat(actual.getTotalElements()).isEqualTo(1);
        assertThat(actual.getTotalPages()).isEqualTo(1);
        verify(propertyRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should find property by id and then return it")
    void shouldFind_andReturn_PropertyById() {
        // Arrange
        when(propertyRepository.findById(anyLong())).thenReturn(Optional.of(property));
        // Act
        PropertyResponse testResponse = propertyService.getProperty(1L);
        // Assert
        assertThat(testResponse).isEqualTo(PropertyMapper.convertToResponse(property));
        assertThat(testResponse.description()).isEqualTo(property.getDescription());
        verify(propertyRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("Should throw an exception if property not found by id")
    void shouldThrows_whenFindById_PropertyNotFoundException() {
        // Arrange
        when(propertyRepository.findById(anyLong())).thenReturn(Optional.empty());
        // Act and Assert
        assertThrows(PropertyNotFoundException.class, () -> propertyService.getProperty(1L));
    }

    @Test
    @DisplayName("Should save property and then return it")
    void shouldSave_andReturn_PropertyResponse() {
        // Arrange
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        // Act
        PropertyResponse testResponse = propertyService.createProperty(propertyRequest);
        // Assert
        assertThat(testResponse.title()).isEqualTo(propertyRequest.title());
        assertThat(testResponse.description()).isEqualTo(propertyRequest.description());
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    @DisplayName("Should throw an exception if property already exists")
    void shouldThrow_whenSaveExistingProperty_PropertyAlreadyExistsException() {
        // Arrange
        when(propertyRepository.existsById(anyLong())).thenReturn(true);
        // Act and Assert
        assertThrows(PropertyAlreadyExistsException.class, () -> propertyService.createProperty(propertyRequest));
    }

    @Test
    @DisplayName("Should throw and exception if property does not exist")
    void shouldThrow_whenUpdatingProperty() {
        // Arrange
        when(propertyRepository.existsById(anyLong())).thenReturn(false);
        // Act and Assert
        assertThrows(PropertyNotFoundException.class, () -> propertyService.updateProperty(1L, propertyRequest));
    }

    @Test
    @DisplayName("Should update property and then return property response")
    void shouldUpdate_andSaveProperty_andThenReturn_propertyResponse() {
        // Arrange
        when(propertyRepository.existsById(anyLong())).thenReturn(true);
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        // Act
        PropertyResponse testResponse = propertyService.updateProperty(1L, propertyRequest);
        // Assert
        assertThat(testResponse.id()).isEqualTo(propertyRequest.id());
        assertThat(testResponse.title()).isEqualTo(propertyRequest.title());
        verify(propertyRepository,times(1)).existsById(anyLong());
        verify(propertyRepository,times(1)).save(any(Property.class ));
    }

    @Test
    @DisplayName("Should delete property by id")
    void shouldDelete_propertyById() {
        // Arrange
        doNothing().when(propertyRepository).deleteById(anyLong());
        when(propertyRepository.existsById(anyLong())).thenReturn(true);
        // Act
        propertyService.deletePropertyById(1L);
        // Assert
        verify(propertyRepository, times(1)).deleteById(anyLong());
        verify(propertyRepository, times(1)).existsById(anyLong());
    }

    @Test
    @DisplayName("Should throw an exception if property not found")
    void shouldThrow_whenPropertyNotFound() {
        // Arrange
        when(propertyRepository.existsById(anyLong())).thenReturn(false);
        // Act and Assert
        assertThrows(PropertyNotFoundException.class, () -> propertyService.deletePropertyById(1L));
        verify(propertyRepository, times(1)).existsById(anyLong());
        verify(propertyRepository, times(0)).deleteById(anyLong());
    }
}