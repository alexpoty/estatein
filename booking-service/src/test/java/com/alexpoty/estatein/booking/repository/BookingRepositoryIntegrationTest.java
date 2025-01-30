package com.alexpoty.estatein.booking.repository;

import com.alexpoty.estatein.booking.model.Booking;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(properties = "spring.flyway.clean-disabled=false")
@RequiredArgsConstructor
class BookingRepositoryIntegrationTest {

    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0");

    @Autowired
    private BookingRepository bookingRepository;

    private Booking booking;

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @BeforeEach
    void setUp(@Autowired Flyway flyway) {
        booking = Booking.builder()
                .phone("test")
                .name("test")
                .surname("test")
                .propertyId(1L)
                .build();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void createBooking() {
        // Arrange and Act
        bookingRepository.save(booking);
        // Assert
        assertThat(bookingRepository.findById(1L).orElseThrow().getName()).isEqualTo("test");
        assertThat(bookingRepository.findById(1L).orElseThrow().getSurname()).isEqualTo("test");
    }

    @Test
    void findBookingById() {
        // Arrange
        bookingRepository.save(booking);
        // Act
        var actual = bookingRepository.findById(1L).orElseThrow();
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getPhone()).isEqualTo(booking.getPhone());
        assertThat(actual.getName()).isEqualTo(booking.getName());
    }

    @Test
    void findAllBookings() {
        // Arrange
        bookingRepository.save(booking);
        // Act
        List<Booking> actual = bookingRepository.findAll();
        // Assert
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getName()).isEqualTo(booking.getName());
    }

    @Test
    void updateBooking() {
        // Arrange
        bookingRepository.save(booking);
        // Act
        var toUpdate = bookingRepository.findById(1L).orElseThrow();
        toUpdate.setPhone("updated");
        toUpdate.setSurname("updated");
        bookingRepository.save(toUpdate);
        var actual = bookingRepository.findById(1L).orElseThrow();
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getPhone()).isEqualTo("updated");
    }

    @Test
    void deleteBooking() {
        // Arrange
        bookingRepository.save(booking);
        // Act
        bookingRepository.deleteById(1L);
        //Assert
        assertFalse(bookingRepository.existsById(1L));
    }
}