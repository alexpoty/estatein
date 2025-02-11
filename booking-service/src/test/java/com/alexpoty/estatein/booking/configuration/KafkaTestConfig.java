package com.alexpoty.estatein.booking.configuration;

import com.alexpoty.estatein.booking.event.BookingPlacedEvent;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@TestConfiguration
public class KafkaTestConfig {

    @Bean
    @SuppressWarnings("unchecked")
    public KafkaTemplate<String, BookingPlacedEvent> kafkaTemplate() {
        return Mockito.mock(KafkaTemplate.class);
    }
}
