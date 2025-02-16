package com.alexpoty.estatein.image.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private Long propertyId;
}
