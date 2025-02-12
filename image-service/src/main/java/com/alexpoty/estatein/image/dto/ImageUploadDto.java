package com.alexpoty.estatein.image.dto;

import org.springframework.web.multipart.MultipartFile;

public record ImageUploadDto(
        MultipartFile file,
        Long propertyId
) {
}
