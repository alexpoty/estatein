package com.alexpoty.estatein.booking.controller;

import com.alexpoty.estatein.booking.dto.BookingDto;
import com.alexpoty.estatein.booking.service.BookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Booking Api", description = "Endpoints for managing bookings")
public class BookingController {

    private final BookingServiceImpl bookingService;

    @Operation(summary = "Get a list of bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found bookings",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = BookingDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        log.info("BookingController:getAllBookings");
        return new ResponseEntity<>(bookingService.getAllBookings(), HttpStatus.OK);
    }

    @Operation(summary = "Find and return booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found booking",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = BookingDto.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@Parameter(description = "Id of a booking")
                                                         @PathVariable("id") Long id) {
        log.info("BookingController:getBookingById");
        return new ResponseEntity<>(bookingService.getBookingById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking Created",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = BookingDto.class))})
    })
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@io.swagger.v3.oas.annotations.parameters.RequestBody
                                                                (description = "BookingDto", required = true,
                                                                content = @Content(schema =
                                                                @Schema(implementation = BookingDto.class)))
                                                        @RequestBody BookingDto bookingDto) {
        log.info("BookingController:createBooking");
        return new ResponseEntity<>(bookingService.createBooking(bookingDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Booking",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = BookingDto.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable("id") Long id, @RequestBody BookingDto bookingDto) {
        log.info("BookingController:updateBooking");
        return new ResponseEntity<>(bookingService.updateBooking(id, bookingDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Booking")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@Parameter(description = "Id of deleted booking")
                                                  @PathVariable("id") Long id) {
        log.info("BookingController:deleteBooking");
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
