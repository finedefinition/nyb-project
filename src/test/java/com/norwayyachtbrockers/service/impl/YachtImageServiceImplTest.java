package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtImageMapper;
import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.repository.YachtImageRepository;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.util.S3ImageService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(690)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtImageServiceImplTest {

    @MockBean
    private YachtImageRepository yachtImageRepository;
    @MockBean
    private YachtRepository yachtRepository;
    @MockBean
    private YachtImageMapper yachtImageMapper;
    @MockBean
    private S3ImageService s3ImageService;

    @Autowired
    private YachtImageServiceImpl yachtImageService;

    private Yacht yacht;
    private YachtImage yachtImage;
    private YachtImageRequestDto yachtImageRequestDto;
    private static final Long YACHT_ID = 1L;
    private static final Long YACHT_IMAGE_ID = 2L;
    private static final String IMAGE_KEY = "imageKey";
    private static final String IMAGE_KEY_UPDATED = "imageKeyUpdated";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        yacht = new Yacht();
        yacht.setId(YACHT_ID);
        yacht.setYachtImages(new HashSet<>());

        yachtImage = new YachtImage();
        yachtImage.setId(YACHT_IMAGE_ID);
        yachtImage.setImageKey(IMAGE_KEY);
        yachtImage.setYacht(yacht);

        yachtImageRequestDto = new YachtImageRequestDto();
        yachtImageRequestDto.setYachtId(YACHT_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtImageRepository, yachtImageMapper, yachtRepository, s3ImageService);
    }

    @Test
    @Order(10)
    @DisplayName("saveMultipleImages - Successfully saves multiple images")
    @Transactional
    void testSaveMultipleImages_Success() {
        // Arrange
        MultipartFile file1 = new MockMultipartFile("image1", "image1.jpg",
                "image/jpeg", "image1 content".getBytes());
        MultipartFile file2 = new MockMultipartFile("image2", "image2.jpg",
                "image/jpeg", "image2 content".getBytes());
        List<MultipartFile> imageFiles = Arrays.asList(file1, file2);

        when(yachtRepository.findById(YACHT_ID)).thenReturn(Optional.of(yacht));
        when(s3ImageService.uploadImageToS3(file1)).thenReturn(IMAGE_KEY + "1");
        when(s3ImageService.uploadImageToS3(file2)).thenReturn(IMAGE_KEY + "2");
        when(yachtImageRepository.save(any(YachtImage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(yachtImageMapper.convertToResponseDto(any(YachtImage.class)))
                .thenReturn(new YachtImageResponseDto());

        // Act
        List<YachtImageResponseDto> responseDtos = yachtImageService.saveMultipleImages(yachtImageRequestDto, imageFiles);

        // Assert
        assertNotNull(responseDtos, "Response DTO list should not be null");
        assertEquals(2, responseDtos.size(), "Response DTO list size should be 2");

        verify(s3ImageService, times(1)).uploadImageToS3(file1);
        verify(s3ImageService, times(1)).uploadImageToS3(file2);
        verify(yachtImageRepository, times(2)).save(any(YachtImage.class));
    }

    @Test
    @Order(20)
    @DisplayName("findById - Successfully finds a yacht image by ID")
    void testFindById_Success() {
        // Arrange
        when(yachtImageRepository.findById(YACHT_IMAGE_ID)).thenReturn(Optional.of(yachtImage));
        when(yachtImageMapper.convertToResponseDto(yachtImage)).thenReturn(new YachtImageResponseDto());

        // Act
        YachtImageResponseDto responseDto = yachtImageService.findById(YACHT_IMAGE_ID);

        // Assert
        assertNotNull(responseDto, "Found yacht image should not be null");
        verify(yachtImageRepository, times(1)).findById(YACHT_IMAGE_ID);
    }

    @Test
    @Order(30)
    @DisplayName("findAll - Successfully retrieves all yacht images sorted by ID")
    void testFindAll_Success() {
        // Arrange
        YachtImage anotherYachtImage = new YachtImage();
        anotherYachtImage.setId(3L);
        List<YachtImage> yachtImages = Arrays.asList(yachtImage, anotherYachtImage);

        when(yachtImageRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(yachtImages);
        when(yachtImageMapper.convertToResponseDto(any(YachtImage.class))).thenReturn(new YachtImageResponseDto());

        // Act
        List<YachtImageResponseDto> responseDtos = yachtImageService.findAll();

        // Assert
        assertNotNull(responseDtos, "Found yacht images list should not be null");
        assertEquals(2, responseDtos.size(), "Found yacht images list size should be 2");

        verify(yachtImageRepository, times(1))
                .findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    @Order(40)
    @DisplayName("update - Successfully updates a yacht image")
    @Transactional
    void testUpdate_Success() {
        // Arrange
        MultipartFile newFile = new MockMultipartFile("image", "image.jpg",
                "image/jpeg", "new image content".getBytes());

        when(yachtImageRepository.findById(YACHT_IMAGE_ID)).thenReturn(Optional.of(yachtImage));
        when(yachtRepository.findById(YACHT_ID)).thenReturn(Optional.of(yacht));
        when(s3ImageService.uploadImageToS3(newFile)).thenReturn(IMAGE_KEY_UPDATED);
        when(yachtImageRepository.save(any(YachtImage.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(yachtImageMapper.convertToResponseDto(any(YachtImage.class))).thenReturn(new YachtImageResponseDto());

        // Act
        YachtImageResponseDto responseDto = yachtImageService.update(YACHT_IMAGE_ID, yachtImageRequestDto, newFile);

        // Assert
        assertNotNull(responseDto, "Updated yacht image should not be null");
        assertEquals(IMAGE_KEY_UPDATED, yachtImage.getImageKey(), "Image key should be updated");

        verify(s3ImageService, times(1)).uploadImageToS3(newFile);
        verify(yachtImageRepository, times(1)).save(yachtImage);
    }

    @Test
    @Order(50)
    @DisplayName("delete - Successfully deletes a yacht image by ID")
    @Transactional
    void testDelete_Success() {
        // Arrange
        when(yachtImageRepository.findById(YACHT_IMAGE_ID)).thenReturn(Optional.of(yachtImage));
        doNothing().when(yachtImageRepository).delete(yachtImage);

        // Act
        yachtImageService.delete(YACHT_IMAGE_ID);

        // Assert
        verify(yachtImageRepository, times(1)).delete(yachtImage);
    }
}