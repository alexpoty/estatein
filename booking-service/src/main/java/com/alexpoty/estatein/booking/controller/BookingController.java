package com.alexpoty.estatein.booking.controller;

import com.alexpoty.estatein.booking.dto.BookingDto;
import com.alexpoty.estatein.booking.service.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingServiceImpl bookingService;

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        log.info("BookingController:getAllBookings");
        return new ResponseEntity<>(bookingService.getAllBookings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable("id") Long id) {
        log.info("BookingController:getBookingById");
        return new ResponseEntity<>(bookingService.getBookingById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto) {
        log.info("BookingController:createBooking");
        return new ResponseEntity<>(bookingService.createBooking(bookingDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable("id") Long id, @RequestBody BookingDto bookingDto) {
        log.info("BookingController:updateBooking");
        return new ResponseEntity<>(bookingService.updateBooking(id, bookingDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long id) {
        log.info("BookingController:deleteBooking");
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
