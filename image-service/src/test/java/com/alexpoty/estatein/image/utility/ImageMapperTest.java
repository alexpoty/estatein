package com.alexpoty.estatein.image.utility;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.model.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ImageMapperTest {

    private Image image;
    private ImageRequest imageRequest;

    @BeforeEach
    void setUp() {
        image = Image.builder()
                .id(1L)
                .imageUrl("test/url")
                .propertyId(1L)
                .build();
        imageRequest = new ImageRequest(
                "test/url",
                1L
        );
    }

    @Test
    void shouldConvertToEntity() {
        // Arrange and Act
        Image actual = ImageMapper.toImage(imageRequest);
        // Assert
        assertThat(actual.getImageUrl()).isEqualTo(imageRequest.imageUrl());
        assertThat(actual.getPropertyId()).isEqualTo(imageRequest.propertyId());
    }

    @Test
    void shouldConvertToImageResponse() {
        // Arrange and Act
        ImageResponse actual = ImageMapper.toImageResponse(image);
        // Assert
        assertThat(actual.imageUrl()).isEqualTo(image.getImageUrl());
        assertThat(actual.propertyId()).isEqualTo(image.getPropertyId());
    }
}