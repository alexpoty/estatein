package com.alexpoty.estatein.booking.dto;

import java.time.LocalDate;

public record BookingDto(
        Long id,
        LocalDate date,
        String phone,
        String name,
        String surname,
        Long propertyId
) {
}
