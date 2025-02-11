package com.alexpoty.estatein.booking.util;

import com.alexpoty.estatein.booking.dto.BookingDto;
import com.alexpoty.estatein.booking.model.Booking;

public class BookingMapper {

    public static Booking convertToModel(BookingDto bookingDto) {
        return Booking.builder()
                .id(bookingDto.id())
                .date(bookingDto.date())
                .email(bookingDto.email())
                .phone(bookingDto.phone())
                .name(bookingDto.name())
                .surname(bookingDto.surname())
                .propertyId(bookingDto.propertyId())
                .build();
    }

    public static BookingDto convertToDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getDate(),
                booking.getEmail(),
                booking.getPhone(),
                booking.getName(),
                booking.getSurname(),
                booking.getPropertyId()
        );
    }
}
