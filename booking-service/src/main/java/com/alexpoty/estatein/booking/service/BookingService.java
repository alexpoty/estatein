package com.alexpoty.estatein.booking.service;

import com.alexpoty.estatein.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    List<BookingDto> getAllBookings();
    BookingDto getBookingById(Long id);
    BookingDto createBooking(BookingDto bookingDto);
    BookingDto updateBooking(Long id, BookingDto bookingDto);
    void deleteBooking(Long id);
}
