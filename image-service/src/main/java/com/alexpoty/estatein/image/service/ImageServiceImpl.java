package com.alexpoty.estatein.image.service;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.dto.ImageUploadDto;
import com.alexpoty.estatein.image.exception.ImageNotFoundException;
import com.alexpoty.estatein.image.model.Image;
import com.alexpoty.estatein.image.respository.ImageRepository;
import com.alexpoty.estatein.image.utility.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CloudinaryServiceImpl cloudinaryService;

    public List<ImageResponse> getAllImagesByPropertyId(Long propertyId) {
        log.info("Starting to findingAllImages By PropertyID {}", propertyId);
        return imageRepository.findAllByPropertyId(propertyId).stream().map(ImageMapper::toImageResponse).toList();
    }

    public ImageResponse getImageByPropertyId(Long propertyId) {
        log.info("Starting to find First Image By PropertyId {}", propertyId);
        return ImageMapper.toImageResponse(imageRepository.findFirstByPropertyId(propertyId));
    }

    public ImageResponse createImage(ImageRequest imageRequest) {
        log.info("Starting to create image {}", imageRequest);
        return ImageMapper.toImageResponse(imageRepository.save(ImageMapper.toImage(imageRequest)));
    }

    public void deleteImageById(Long id) {
        log.info("Checking if image exists with id {}", id);
        if (!imageRepository.existsById(id)) {
            throw new ImageNotFoundException("Image with id " + id + " not found");
        }
        log.info("Deleting image {}", id);
        imageRepository.deleteById(id);
    }

    public Map<?, ?> uploadImage(ImageUploadDto imageUploadDto) {
        try {
            if (imageUploadDto.file() == null) {
                throw new ImageNotFoundException("File is empty");
            }
            Image image = new Image();
            image.setPropertyId(imageUploadDto.propertyId());
            image.setImageUrl(cloudinaryService.uploadFile(imageUploadDto.file()));
            imageRepository.save(image);
            return Map.of("url", image.getImageUrl());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
