package com.alexpoty.estatein.booking.dto;

import java.time.LocalDateTime;

public record BookingDto(
        Long id,
        LocalDateTime date,
        String phone,
        String name,
        String surname,
        Long propertyId
) {
}
