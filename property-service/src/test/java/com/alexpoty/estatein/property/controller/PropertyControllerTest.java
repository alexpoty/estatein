package com.alexpoty.estatein.property.controller;

import com.alexpoty.estatein.property.dto.PropertyRequest;
import com.alexpoty.estatein.property.dto.PropertyResponse;
import com.alexpoty.estatein.property.service.PropertyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
    void PropertyController_GetProperties_ReturnListOfProperty() throws Exception {
        when(propertyService.getAllProperties()).thenReturn(propertyResponses);

        ResultActions resultActions = mockMvc.perform(get("/api/property")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
    }

    @Test
    void PropertyController_GetPropertyById_ReturnProperty() throws Exception {
        when(propertyService.getProperty(anyLong())).thenReturn(propertyResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/property/1")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("TestProperty")));
    }

    @Test
    void PropertyController_CreateProperty_ReturnCreated() throws Exception {
        when(propertyService.createProperty(any(PropertyRequest.class))).thenReturn(propertyResponse);

        ResultActions resultActions = mockMvc.perform(post("/api/property")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(propertyRequest)));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("TestProperty")));
    }

    @Test
    void PropertyController_UpdateProperty_ReturnUpdatedProperty() throws Exception {
        when(propertyService.updateProperty(anyLong(), any(PropertyRequest.class))).thenReturn(propertyResponse);

        ResultActions resultActions = mockMvc.perform(put("/api/property/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(propertyRequest)));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("TestProperty")));
    }

    @Test
    void PropertyController_DeleteProperty_ReturnStatusNoContent() throws Exception {
        doNothing().when(propertyService).deletePropertyById(anyLong());

        ResultActions resultActions = mockMvc.perform(delete("/api/property/1")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}