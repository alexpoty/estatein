package com.alexpoty.estatein.image.service;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.dto.ImageUploadDto;

import java.util.List;
import java.util.Map;

public interface ImageService {
    List<ImageResponse> getAllImagesByPropertyId(Long propertyId);
    ImageResponse getImageByPropertyId(Long propertyId);
    ImageResponse createImage(ImageRequest imageRequest);
    void deleteImageById(Long id);
    Map<?, ?> uploadImage(ImageUploadDto imageUploadDto);
}
