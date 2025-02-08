package com.alexpoty.estatein.booking.util;

import com.alexpoty.estatein.booking.dto.BookingDto;
import com.alexpoty.estatein.booking.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookingMapperTest {

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
    }

    @Test
    @DisplayName("Should convert Booking Entity to BookingDto")
    void shouldConvertBookingToBookingDto() {
        // Arrange and Act
        BookingDto actual = BookingMapper.convertToDto(booking);
        // Assert
        assertThat(actual.id()).isEqualTo(booking.getId());
        assertThat(actual.phone()).isEqualTo(booking.getPhone());
        assertThat(actual.name()).isEqualTo(booking.getName());
    }

    @Test
    @DisplayName("Should convert BookingDto to Entity")
    void shouldConvertBookingDtoToBooking() {
        // Arrange and Act
        Booking actual = BookingMapper.convertToModel(bookingDto);
        // Assert
        assertThat(actual.getId()).isEqualTo(bookingDto.id());
        assertThat(actual.getPhone()).isEqualTo(bookingDto.phone());
        assertThat(actual.getName()).isEqualTo(bookingDto.name());
    }
}