package com.alexpoty.estatein.image.controller;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.dto.ImageUploadDto;
import com.alexpoty.estatein.image.service.ImageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get First Image of property By Id", description = "Get First Image of Property Id")
    @GetMapping
    public ResponseEntity<ImageResponse> getImageByPropertyId(@RequestParam Long propertyId) {
        return new ResponseEntity<>(imageService.getImageByPropertyId(propertyId), HttpStatus.OK);
    }

    @Operation(summary = "Get List of images of property", description = "Get an array of images for property")
    @GetMapping("/{id}")
    public ResponseEntity<List<ImageResponse>> getAllImagesByPropertyId(@PathVariable Long id) {
        return new ResponseEntity<>(imageService.getAllImagesByPropertyId(id), HttpStatus.OK);
    }

    @Operation(summary = "Creating a image", description = "Passing image urls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created image")
    })
    @PostMapping
    public ResponseEntity<ImageResponse> createImage(@RequestBody ImageRequest imageRequest) {
        return new ResponseEntity<>(imageService.createImage(imageRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Deleting an Image", description = "Deleting an image by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted image"),
            @ApiResponse(responseCode = "500", description = "Image already exists")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Upload image", description = "Upload an image as Multipart File")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created"),
            @ApiResponse(responseCode = "200", description = "Empty body means that could not connect to Cloudinary Service")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<?, ?>> uploadImage(@ModelAttribute ImageUploadDto imageRequest) {
        return new ResponseEntity<>(imageService.uploadImage(imageRequest), HttpStatus.CREATED);
    }
}
