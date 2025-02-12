package com.alexpoty.estatein.image.controller;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.dto.ImageUploadDto;
import com.alexpoty.estatein.image.service.ImageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Tag(name = "Image Service Api", description = "Endpoints for images")
public class ImageController {

    private final ImageServiceImpl imageService;

    @Operation(summary = "Get First Image of property By Id")
    @GetMapping
    public ResponseEntity<ImageResponse> getImageByPropertyId(@RequestParam Long propertyId) {
        return new ResponseEntity<>(imageService.getImageByPropertyId(propertyId), HttpStatus.OK);
    }

    @Operation(summary = "Get List of images of property")
    @GetMapping("/{id}")
    public ResponseEntity<List<ImageResponse>> getAllImagesByPropertyId(@PathVariable Long id) {
        return new ResponseEntity<>(imageService.getAllImagesByPropertyId(id), HttpStatus.OK);
    }

    @Operation(summary = "Creating a image")
    @PostMapping
    public ResponseEntity<ImageResponse> createImage(@RequestBody ImageRequest imageRequest) {
        return new ResponseEntity<>(imageService.createImage(imageRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Deleting an Image")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<?, ?>> uploadImage(@ModelAttribute ImageUploadDto imageRequest) {
        return new ResponseEntity<>(imageService.uploadImage(imageRequest), HttpStatus.OK);
    }
}
