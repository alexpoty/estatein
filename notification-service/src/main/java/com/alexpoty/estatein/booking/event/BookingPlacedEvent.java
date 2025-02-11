package com.alexpoty.estatein.booking.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPlacedEvent {

  private LocalDate date;
  private String email;
  private String name;
  private Long propertyId;
}