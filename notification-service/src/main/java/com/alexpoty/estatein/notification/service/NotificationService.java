package com.alexpoty.estatein.notification.service;

import com.alexpoty.estatein.booking.event.BookingPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = "booking-placed")
    public void listen(BookingPlacedEvent bookingPlacedEvent) {
        log.info("Got message from booking-placed {}", bookingPlacedEvent);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("estatein@gmail.com");
            messageHelper.setTo(bookingPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Your booking for property with id %s is placed successfully", bookingPlacedEvent.getPropertyId()));
            if (bookingPlacedEvent.getDate() != null) {
                messageHelper.setText(String.format("""
                         Hello %s,
                         We Glad to inform you that property that you booked on %s is accepted!
                         Our manager will contact you as soon as possible!
                         Best regards!
                         Estate in!
                        """, bookingPlacedEvent.getName(), bookingPlacedEvent.getDate()));
            } else {
                messageHelper.setText(String.format("""
                        Hello %s,
                        We Glad to inform you that property that you booked is accepted!
                        Unfortunately you did not specify a date, so our managers will contact you for more details.
                        Best regards!
                        Estate in!
                        """, bookingPlacedEvent.getName()));
            }
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Order Notification email sent!");
        } catch (MailException e) {
            log.error("Order Notification email failed", e);
            throw new RuntimeException("Exception occurred when sending mail!");
        }
    }

}
