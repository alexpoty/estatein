package com.alexpoty.estatein.property.dto;

import java.math.BigDecimal;

public record PropertyResponse(Long id, String title, String description, String location,
                               BigDecimal price, String area) {
}
