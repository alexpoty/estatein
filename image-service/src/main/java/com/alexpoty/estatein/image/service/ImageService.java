package com.alexpoty.estatein.image.service;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;

import java.util.List;

public interface ImageService {
    List<ImageResponse> getAllImagesByPropertyId(Long propertyId);
    ImageResponse getImageByPropertyId(Long propertyId);
    ImageResponse createImage(ImageRequest imageRequest);
    void deleteImageById(Long id);
}
