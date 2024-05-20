package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.service.YachtImageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Order(110)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtImageControllerTest {

    @MockBean
    private YachtImageService yachtImageService;

    @Autowired
    private MockMvc mockMvc;

    private YachtImageResponseDto yachtImageResponseDto;
    private YachtImageRequestDto yachtImageRequestDto;

    private static final String BASE_URL = "/yachtImages";
    private static final Long IMAGE_ID = 1L;
    private static final Long YACHT_ID = 1L;
    private static final String YACHT_IMAGE_KEY = "image-key-123";

    @BeforeEach
    void setUp() {
        yachtImageResponseDto = new YachtImageResponseDto();
        yachtImageResponseDto.setId(IMAGE_ID);
        yachtImageResponseDto.setYachtId(YACHT_ID);
        yachtImageResponseDto.setImageKey(YACHT_IMAGE_KEY);

        yachtImageRequestDto = new YachtImageRequestDto();
        yachtImageRequestDto.setYachtId(YACHT_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtImageService);
    }

    @Test
    @Order(10)
    @DisplayName("uploadMultipleImages - Successfully uploads multiple images")
    void testUploadMultipleImages_Success() throws Exception {
        // Arrange
        MockMultipartFile imageFile1 = new MockMultipartFile("images",
                "image1.jpg", MediaType.IMAGE_JPEG_VALUE, "image1".getBytes());
        MockMultipartFile imageFile2 = new MockMultipartFile("images",
                "image2.jpg", MediaType.IMAGE_JPEG_VALUE, "image2".getBytes());
        MockMultipartFile yachtData = new MockMultipartFile("yachtData",
                "", MediaType.APPLICATION_JSON_VALUE, asJsonString(yachtImageRequestDto).getBytes());

        List<YachtImageResponseDto> responseDtoList = Collections.singletonList(yachtImageResponseDto);

        when(yachtImageService.saveMultipleImages(any(YachtImageRequestDto.class),
                any(List.class))).thenReturn(responseDtoList);

        // Act & Assert
        mockMvc.perform(multipart(BASE_URL)
                        .file(imageFile1)
                        .file(imageFile2)
                        .file(yachtData)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].yacht_image_id").value(IMAGE_ID))
                .andExpect(jsonPath("$[0].yacht_image_key").value(YACHT_IMAGE_KEY))
                .andReturn();

        verify(yachtImageService, times(1)).saveMultipleImages(any(YachtImageRequestDto.class), any(List.class));
    }

    @Test
    @Order(20)
    @DisplayName("getById - Successfully retrieves an image by ID")
    void testGetById_Success() throws Exception {
        // Arrange
        when(yachtImageService.findById(IMAGE_ID)).thenReturn(yachtImageResponseDto);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + IMAGE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yacht_image_id").value(IMAGE_ID))
                .andExpect(jsonPath("$.yacht_image_key").value(YACHT_IMAGE_KEY))
                .andReturn();

        verify(yachtImageService, times(1)).findById(IMAGE_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAll - Successfully retrieves all images")
    void testGetAll_Success() throws Exception {
        // Arrange
        YachtImageResponseDto anotherImage = new YachtImageResponseDto();
        anotherImage.setId(2L);

        List<YachtImageResponseDto> images = Arrays.asList(yachtImageResponseDto, anotherImage);
        when(yachtImageService.findAll()).thenReturn(images);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].yacht_image_id").value(IMAGE_ID))
                .andExpect(jsonPath("$[1].yacht_image_id").value(2L))
                .andReturn();

        verify(yachtImageService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAll - Returns NO_CONTENT when no images are found")
    void testGetAll_NoContent() throws Exception {
        // Arrange
        when(yachtImageService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(yachtImageService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("update - Successfully updates an image")
    void testUpdate_Success() throws Exception {
        // Arrange
        MockMultipartFile imageFile = new MockMultipartFile("imageFile",
                "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image".getBytes());
        MockMultipartFile yachtData = new MockMultipartFile("yachtData",
                "", MediaType.APPLICATION_JSON_VALUE, asJsonString(yachtImageRequestDto).getBytes());

        when(yachtImageService.update(eq(IMAGE_ID), any(YachtImageRequestDto.class), any(MultipartFile.class))).thenReturn(yachtImageResponseDto);

        // Act & Assert
        mockMvc.perform(multipart(BASE_URL + "/" + IMAGE_ID)
                        .file(imageFile)
                        .file(yachtData)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yacht_image_id").value(IMAGE_ID))
                .andExpect(jsonPath("$.yacht_image_key").value(YACHT_IMAGE_KEY))
                .andReturn();

        verify(yachtImageService, times(1)).update(eq(IMAGE_ID),
                any(YachtImageRequestDto.class), any(MultipartFile.class));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes an image by ID")
    void testDeleteById_Success() throws Exception {
        // Arrange
        doNothing().when(yachtImageService).delete(IMAGE_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + IMAGE_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(yachtImageService, times(1)).delete(IMAGE_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
