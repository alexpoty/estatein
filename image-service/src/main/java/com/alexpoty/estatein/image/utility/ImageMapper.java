package com.alexpoty.estatein.image.utility;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.model.Image;

public class ImageMapper {

    public static Image toImage(ImageRequest imageRequest) {
        return Image.builder()
                .imageUrl(imageRequest.imageUrl())
                .propertyId(imageRequest.propertyId())
                .build();
    }

    public static ImageResponse toImageResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getPropertyId()
        );
    }
}
