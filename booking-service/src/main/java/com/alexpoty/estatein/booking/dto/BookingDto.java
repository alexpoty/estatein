package com.alexpoty.estatein.booking.dto;

import java.time.LocalDate;

public record BookingDto(
        Long id,
        LocalDate date,
        String email,
        String phone,
        String name,
        String surname,
        Long propertyId
) {
}
