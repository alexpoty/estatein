package com.alexpoty.estatein.booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "t_bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateTime;
    private String phone;
    private String name;
    private String surname;
    @Column(name = "property_id")
    private Long propertyId;
}
