package com.alexpoty.estatein.booking.controller;

import com.alexpoty.estatein.booking.dto.BookingDto;
import com.alexpoty.estatein.booking.service.BookingServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingServiceImpl bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<BookingDto> bookingDtos = new ArrayList<>();
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        bookingDto = new BookingDto(1L,
                null,
                "test",
                "test",
                "test",
                1L);
        bookingDtos.add(bookingDto);
    }

    @Test
    void BookingController_GetBooking_ReturnListOfBookingDtos() throws Exception {
        // Arrange
        when(bookingService.getAllBookings()).thenReturn(bookingDtos);
        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/booking")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
    }

    @Test
    void BookingController_GetBooking_ReturnBookingDto() throws Exception {
        // Arrange
        when(bookingService.getBookingById(anyLong())).thenReturn(bookingDto);
        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/booking/1")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.is(bookingDto.phone())));
    }

    @Test
    void BookingController_CreateBooking_ReturnBookingDto() throws Exception {
        // Arrange
        when(bookingService.createBooking(any(BookingDto.class))).thenReturn(bookingDto);
        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingDto)));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.is(bookingDto.phone())));
    }

    @Test
    void BookingController_UpdateBooking_ReturnBookingDto() throws Exception {
        // Arrange
        when(bookingService.updateBooking(anyLong(), any(BookingDto.class))).thenReturn(bookingDto);
        // Act
        ResultActions resultActions = mockMvc.perform(put("/api/booking/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingDto)));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.is(bookingDto.phone())));
    }

    @Test
    void BookingController_DeleteBooking() throws Exception {
        // Arrange
        doNothing().when(bookingService).deleteBooking(anyLong());
        // Act
        ResultActions result = mockMvc.perform(delete("/api/booking/1")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}