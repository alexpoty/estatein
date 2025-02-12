package com.alexpoty.estatein.image.controller;

import com.alexpoty.estatein.image.dto.ImageRequest;
import com.alexpoty.estatein.image.dto.ImageResponse;
import com.alexpoty.estatein.image.dto.ImageUploadDto;
import com.alexpoty.estatein.image.service.CloudinaryServiceImpl;
import com.alexpoty.estatein.image.service.ImageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ImageServiceImpl imageService;

    @MockitoBean
    private CloudinaryServiceImpl cloudinaryService;

    @Autowired
    private ObjectMapper objectMapper;

    private ImageResponse imageResponse;
    private ImageRequest imageRequest;

    @BeforeEach
    void setUp() {
        imageResponse = new ImageResponse(
                1L ,
                "test/url",
                1L
        );
        imageRequest = new ImageRequest(
                "test/url",
                1L
        );
    }

    @Test
    void ImageController_GetImageByPropertyId() throws Exception {
        // Arrange
        when(imageService.getImageByPropertyId(anyLong())).thenReturn(imageResponse);
        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/image?propertyId=1")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.propertyId", CoreMatchers.is(1)));
    }

    @Test
    void ImageController_GetAllImagesByPropertyId() throws Exception {
        // Arrange
        when(imageService.getAllImagesByPropertyId(anyLong())).thenReturn(List.of(imageResponse));
        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/image/1")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
    }

    @Test
    void ImageController_CreateImage() throws Exception {
        // Arrange
        when(imageService.createImage(any(ImageRequest.class))).thenReturn(imageResponse);
        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/image")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imageRequest)));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.propertyId", CoreMatchers.is(1)));
    }

    @Test
    void ImageController_DeleteImage() throws Exception {
        // Arrange
        doNothing().when(imageService).deleteImageById(anyLong());
        // Act
        ResultActions resultActions = mockMvc.perform(delete("/api/image/1")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void ImageController_UploadImage() throws Exception {
        // Arrange
        when(cloudinaryService.uploadFile(any(MultipartFile.class))).thenReturn("http://cloudinary.com/image_url");
        ImageUploadDto imageUploadDto = new ImageUploadDto(
                new MockMultipartFile("file",
                        "test.jpg","image/jpeg", "some-content".getBytes()),
                1L
        );

        // Act
        mockMvc.perform(multipart("/api/image/upload")
                .file((MockMultipartFile) imageUploadDto.file())
                .param("propertyId", "1"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}