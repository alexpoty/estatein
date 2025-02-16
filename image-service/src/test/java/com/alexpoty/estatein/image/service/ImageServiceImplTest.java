package com.alexpoty.estatein.image.service;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.dto.ImageUploadDto;
import com.alexpoty.estatein.image.exception.ImageNotFoundException;
import com.alexpoty.estatein.image.model.Image;
import com.alexpoty.estatein.image.respository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private CloudinaryServiceImpl cloudinaryService;
    @InjectMocks
    private ImageServiceImpl imageService;

    private ImageRequest imageRequest;
    private Image image;

    @BeforeEach
    void setUp() {
        imageRequest = new ImageRequest(
                "test/url",
                1L
        );
        image = Image.builder()
                .id(1L)
                .imageUrl("test/url")
                .propertyId(1L)
                .build();
    }

    @Test
    void shouldReturnListOfImagesByPropertyId() {
        // Arrange
        when(imageRepository.findAllByPropertyId(anyLong())).thenReturn(List.of(image));
        // Act
        List<ImageResponse> actual = imageService.getAllImagesByPropertyId(1L);
        // Assert
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).imageUrl()).isEqualTo(image.getImageUrl());
        verify(imageRepository, times(1)).findAllByPropertyId(anyLong());
    }

    @Test
    void shouldFindImageByPropertyId() {
        // Arrange
        when(imageRepository.findFirstByPropertyId(anyLong())).thenReturn(image);
        // Act
        ImageResponse actual = imageService.getImageByPropertyId(1L);
        // Assert
        assertThat(actual.imageUrl()).isEqualTo(image.getImageUrl());
        assertThat(actual.propertyId()).isEqualTo(image.getPropertyId());
        verify(imageRepository, times(1)).findFirstByPropertyId(anyLong());
    }

    @Test
    void shouldSaveImage() {
        // Arrange
        when(imageRepository.save(any(Image.class))).thenReturn(image);
        // Act
        ImageResponse actual = imageService.createImage(imageRequest);
        // Assert
        assertThat(actual.imageUrl()).isEqualTo(imageRequest.imageUrl());
        assertThat(actual.propertyId()).isEqualTo(imageRequest.propertyId());
        verify(imageRepository, times(1)).save(any(Image.class));
    }

    @Test
    void shouldDeleteImage() {
        // Arrange
        doNothing().when(imageRepository).deleteById(anyLong());
        when(imageRepository.existsById(anyLong())).thenReturn(true);
        // Act
        imageService.deleteImageById(1L);
        // Assert
        verify(imageRepository, times(1)).deleteById(anyLong());
        verify(imageRepository, times(1)).existsById(anyLong());
    }

    @Test
    void shouldThrowWhenDeleting() {
        // Arrange
        when(imageRepository.existsById(anyLong())).thenReturn(false);
        // Act and assert
        assertThrows(ImageNotFoundException.class, () -> imageService.deleteImageById(1L));
    }

    @Test
    void shouldUploadImage() {
        // Arrange
        ImageUploadDto imageUploadDto = new ImageUploadDto(
                new MockMultipartFile("file",
                        "test.jpg","image/jpeg", "some-content".getBytes()),
                1L
        );
        when(cloudinaryService.uploadFile(any(MultipartFile.class))).thenReturn("http://cloudinary.com/image_url");
        when(imageRepository.save(any(Image.class))).thenReturn(image);
        // Act
        Map<?, ?> result = imageService.uploadImage(imageUploadDto);
        // Assert
        assertNotNull(result);
        assertThat(result.get("url")).isEqualTo("http://cloudinary.com/image_url");
    }
}