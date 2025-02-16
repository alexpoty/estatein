package com.alexpoty.estatein.property.controller;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.service.PropertyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PropertyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PropertyServiceImpl propertyService;

    @Autowired
    private ObjectMapper mapper;

    private final List<PropertyResponse> propertyResponses = new ArrayList<>();
    private PropertyResponse propertyResponse;
    private PropertyRequest propertyRequest;

    @BeforeEach
    void setUp() {
        propertyResponse = new PropertyResponse(1L, "TestProperty",
                "TestDescription", "testLocation", new BigDecimal(1111), "Test area");
        propertyResponses.add(propertyResponse);
        propertyRequest = new PropertyRequest(1L, "TestProperty",
                "TestDescription", "testLocation", new BigDecimal(1111), "Test area");
    }

    @Test
    @DisplayName("Should return list of properties")
    void PropertyController_GetProperties_ReturnListOfProperty() throws Exception {
        // Arrange
        when(propertyService.getAllProperties()).thenReturn(propertyResponses);
        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/property")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
    }

    @Test
    void PropertyController_GetPageOfProperties() throws Exception {
        // Arrange
        Page<PropertyResponse> page = new PageImpl<>(propertyResponses);
        when(propertyService.getPageOfProperties(anyInt(), anyInt())).thenReturn(page);
        // Act
        mockMvc.perform(get("/api/property/page")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should find property by id and return it")
    void PropertyController_GetPropertyById_ReturnProperty() throws Exception {
        // Arrange
        when(propertyService.getProperty(anyLong())).thenReturn(propertyResponse);
        //Act
        ResultActions resultActions = mockMvc.perform(get("/api/property/1")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("TestProperty")));
    }

    @Test
    @DisplayName("Should create property and then return it")
    void PropertyController_CreateProperty_ReturnCreated() throws Exception {
        // Arrange
        when(propertyService.createProperty(any(PropertyRequest.class))).thenReturn(propertyResponse);
        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/property")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(propertyRequest)));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("TestProperty")));
    }

    @Test
    @DisplayName("Should update property and then return it")
    void PropertyController_UpdateProperty_ReturnUpdatedProperty() throws Exception {
        // Arrange
        when(propertyService.updateProperty(anyLong(), any(PropertyRequest.class))).thenReturn(propertyResponse);
        // Act
        ResultActions resultActions = mockMvc.perform(put("/api/property/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(propertyRequest)));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("TestProperty")));
    }

    @Test
    @DisplayName("Should delete property")
    void PropertyController_DeleteProperty_ReturnStatusNoContent() throws Exception {
        // Arrange
        doNothing().when(propertyService).deletePropertyById(anyLong());
        //Act
        ResultActions resultActions = mockMvc.perform(delete("/api/property/1")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}