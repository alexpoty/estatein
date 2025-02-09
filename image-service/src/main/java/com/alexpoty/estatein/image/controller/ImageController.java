package com.alexpoty.estatein.image.controller;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.service.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageService;

    @GetMapping
    public ResponseEntity<ImageResponse> getImageByPropertyId(@RequestParam Long propertyId) {
        return new ResponseEntity<>(imageService.getImageByPropertyId(propertyId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ImageResponse>> getAllImagesByPropertyId(@PathVariable Long id) {
        return new ResponseEntity<>(imageService.getAllImagesByPropertyId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ImageResponse> createImage(@RequestBody ImageRequest imageRequest) {
        return new ResponseEntity<>(imageService.createImage(imageRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
