package com.alexpoty.estatein.booking.service;

import com.alexpoty.estatein.booking.dto.BookingDto;
import com.alexpoty.estatein.booking.event.BookingPlacedEvent;
import com.alexpoty.estatein.booking.exceptions.BookingNotFoundException;
import com.alexpoty.estatein.booking.model.Booking;
import com.alexpoty.estatein.booking.repository.BookingRepository;
import com.alexpoty.estatein.booking.util.BookingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, BookingPlacedEvent> kafkaTemplate;

    @Override
    public List<BookingDto> getAllBookings() {
        log.info("Starting to getting all bookings");
        return bookingRepository.findAll().stream().map(BookingMapper::convertToDto).toList();
    }

    @Override
    public BookingDto getBookingById(Long id) {
        log.info("Starting to get booking with id {}", id);
        return BookingMapper.convertToDto(bookingRepository.findById(id).orElseThrow(
                () -> new BookingNotFoundException("Booking with id " + id + " not found")
        ));
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
       log.info("Starting to create booking {}", bookingDto);
       // Send mail
        BookingPlacedEvent bookingPlacedEvent = new BookingPlacedEvent();
        bookingPlacedEvent.setDate(bookingDto.date());
        bookingPlacedEvent.setEmail(bookingDto.email());
        bookingPlacedEvent.setName(bookingDto.name());
        bookingPlacedEvent.setPropertyId(bookingDto.propertyId());


        log.info("Start - sending kafka topic to notification service with {}", bookingPlacedEvent);
        kafkaTemplate.send("booking-placed", bookingPlacedEvent);
        log.info("Send - kafka topic to notification service with {}", bookingPlacedEvent);
       return BookingMapper.convertToDto(bookingRepository.save(BookingMapper.convertToModel(bookingDto)));
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto bookingDto) {
        log.info("Checking if booking with id {} exists", id);
        if (!bookingRepository.existsById(id)) {
            throw new BookingNotFoundException("Booking with id " + id + " not found");
        }
        Booking booking = BookingMapper.convertToModel(bookingDto);
        booking.setId(id);
        log.info("Updating booking with id {}", id);
        return BookingMapper.convertToDto(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(Long id) {
        log.info("deleteBooking:Checking if booking with id {} exists", id);
        if (!bookingRepository.existsById(id)) {
            throw new BookingNotFoundException("Booking with id " + id + " not found");
        }
        log.info("Deleting booking with id {}", id);
        bookingRepository.deleteById(id);
    }
}
