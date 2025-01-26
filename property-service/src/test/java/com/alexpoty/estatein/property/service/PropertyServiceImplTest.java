package com.alexpoty.estatein.property.service;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.exception.PropertyAlreadyExistsException;
import com.alexpoty.estatein.property.exception.PropertyNotFoundException;
import com.alexpoty.estatein.property.model.Property;
import com.alexpoty.estatein.property.repository.PropertyRepository;
import com.alexpoty.estatein.property.utility.PropertyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PropertyServiceImplTest {

    @Autowired
    private PropertyServiceImpl propertyService;

    @MockitoBean
    PropertyRepository propertyRepository;

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
    void shouldReturn_ListOfProperties_andThenConvertThem_toDTO() {
        //Arrange
        when(propertyRepository.findAll()).thenReturn(properties);
        //Act
        List<PropertyResponse> testProperty = propertyService.getAllProperties();
        //Assert
        assertThat(testProperty.get(0)).isEqualTo(new PropertyResponse(1L, "TestProperty",
                "TestDescription", "testLocation", new BigDecimal(1111), "Test area"));
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void shouldFind_andReturn_PropertyById() {
        //Arrange
        when(propertyRepository.findById(anyLong())).thenReturn(Optional.of(property));
        //Act
        PropertyResponse testResponse = propertyService.getProperty(1L);
        //Assert
        assertThat(testResponse).isEqualTo(PropertyMapper.convertToResponse(property));
        assertThat(testResponse.description()).isEqualTo(property.getDescription());
        verify(propertyRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void shouldThrows_whenFindById_PropertyNotFoundException() {
        //Arrange
        when(propertyRepository.findById(anyLong())).thenReturn(Optional.empty());
        //Act and Assert
        assertThrows(PropertyNotFoundException.class, () -> propertyService.getProperty(1L));
    }

    @Test
    void shouldSave_andReturn_PropertyResponse() {
        //Arrange
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        //Act
        PropertyResponse testResponse = propertyService.createProperty(propertyRequest);
        //Assert
        assertThat(testResponse.title()).isEqualTo(propertyRequest.title());
        assertThat(testResponse.description()).isEqualTo(propertyRequest.description());
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void shouldThrow_whenSaveExistingProperty_PropertyAlreadyExistsException() {
        //Arrange
        when(propertyRepository.existsById(anyLong())).thenReturn(true);
        //Act and Assert
        assertThrows(PropertyAlreadyExistsException.class, () -> propertyService.createProperty(propertyRequest));
    }

    @Test
    void shouldThrow_whenUpdatingProperty() {
        //Arrange
        when(propertyRepository.existsById(anyLong())).thenReturn(false);
        //Act and Assert
        assertThrows(PropertyNotFoundException.class, () -> propertyService.updateProperty(1L, propertyRequest));
    }

    @Test
    void shouldUpdate_andSaveProperty_andThenReturn_propertyResponse() {
        //Arrange
        when(propertyRepository.existsById(anyLong())).thenReturn(true);
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        //Act
        PropertyResponse testResponse = propertyService.updateProperty(1L, propertyRequest);
        //Assert
        assertThat(testResponse.id()).isEqualTo(propertyRequest.id());
        assertThat(testResponse.title()).isEqualTo(propertyRequest.title());
        verify(propertyRepository,times(1)).existsById(anyLong());
        verify(propertyRepository,times(1)).save(any(Property.class ));
    }

    @Test
    void shouldDelete_propertyById() {
        //Arrange
        doNothing().when(propertyRepository).deleteById(anyLong());
        when(propertyRepository.existsById(anyLong())).thenReturn(true);
        //Act
        propertyService.deletePropertyById(1L);
        //Assert
        verify(propertyRepository, times(1)).deleteById(anyLong());
        verify(propertyRepository, times(1)).existsById(anyLong());
    }

    @Test
    void shouldThrow_whenPropertyNotFound() {
        //Arrange
        when(propertyRepository.existsById(anyLong())).thenReturn(false);
        //Act and Assert
        assertThrows(PropertyNotFoundException.class, () -> propertyService.deletePropertyById(1L));
        verify(propertyRepository, times(1)).existsById(anyLong());
        verify(propertyRepository, times(0)).deleteById(anyLong());
    }
}