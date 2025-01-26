package com.alexpoty.estatein.property.dto;

import java.math.BigDecimal;

public record PropertyRequest(Long id, String title, String description, String location,
                              BigDecimal price, String area) {
}
