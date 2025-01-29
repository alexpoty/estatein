package com.alexpoty.estatein.booking.service;

import com.alexpoty.estatein.booking.dto.BookingDto;
import com.alexpoty.estatein.booking.exceptions.BookingNotFoundException;
import com.alexpoty.estatein.booking.model.Booking;
import com.alexpoty.estatein.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingServiceImpl bookingService;

    private final List<Booking> bookings = new ArrayList<>();
    private Booking booking;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        booking = Booking.builder()
                .id(1L)
                .phone("test")
                .name("test")
                .surname("test")
                .propertyId(1L)
                .build();
        bookingDto = new BookingDto(
                1L,
                null,
                "test",
                "test",
                "test",
                1L
        );
        bookings.add(booking);
    }

    @Test
    void shouldReturnListOfBookingsDTO() {
        // Arrange
        when(bookingRepository.findAll()).thenReturn(bookings);
        // Act
        List<BookingDto> actual = bookingService.getAllBookings();
        // Assert
        assertThat(actual.get(0).name()).isEqualTo(bookings.get(0).getName());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void shouldFindAndReturnBookingDtoById() {
        // Arrange
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        // Act
        BookingDto actual = bookingService.getBookingById(1L);
        // Assert
        assertThat(actual.name()).isEqualTo(booking.getName());
        assertThat(actual.phone()).isEqualTo(booking.getPhone());
        verify(bookingRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldThrowWhenFindingNotExistingBooking() {
        // Arrange
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());
        // Act and Assert
        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(1L));
        verify(bookingRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldCreateBookingReturnBookingDto() {
        // Arrange
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        // Act
        BookingDto actual = bookingService.createBooking(bookingDto);
        // Assert
        assertThat(actual.name()).isEqualTo(booking.getName());
        assertThat(actual.phone()).isEqualTo(booking.getPhone());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void shouldUpdateBookingReturnBookingDto() {
        // Arrange
        when(bookingRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        // Act
        BookingDto actual = bookingService.updateBooking(1L, bookingDto);
        // Assert
        assertThat(actual.name()).isEqualTo(booking.getName());
        assertThat(actual.phone()).isEqualTo(booking.getPhone());
        verify(bookingRepository, times(1)).existsById(anyLong());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void shouldThrowWhenUpdatingNotExistingBooking() {
        // Arrange
        when(bookingRepository.existsById(anyLong())).thenReturn(false);
        // Act and Assert
        assertThrows(BookingNotFoundException.class, () -> bookingService.updateBooking(1L, bookingDto));
        verify(bookingRepository, times(1)).existsById(anyLong());
    }

    @Test
    void shouldDeleteBooking() {
        // Arrange
        doNothing().when(bookingRepository).deleteById(anyLong());
        when(bookingRepository.existsById(anyLong())).thenReturn(true);
        // Act
        bookingService.deleteBooking(1L);
        // Assert
        verify(bookingRepository, times(1)).deleteById(anyLong());
        verify(bookingRepository, times(1)).existsById(anyLong());
    }

    @Test
    void shouldThrowWhenBookingNotFound() {
        // Arrange
        when(bookingRepository.existsById(anyLong())).thenReturn(false);
        // Act and Assert
        assertThrows(BookingNotFoundException.class, () -> bookingService.deleteBooking(1L));
        verify(bookingRepository, times(1)).existsById(anyLong());
    }
}