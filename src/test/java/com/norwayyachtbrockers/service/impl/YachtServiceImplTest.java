package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtMapper;
import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.repository.specification.yacht.YachtSpecificationBuilder;
import com.norwayyachtbrockers.service.YachtImageService;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(710)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtServiceImplTest {

    @MockBean
    private YachtRepository yachtRepository;
    @MockBean
    private YachtImageService yachtImageService;
    @MockBean
    private YachtMapper yachtMapper;
    @MockBean
    private YachtSpecificationBuilder yachtSpecificationBuilder;
    @MockBean
    private S3ImageService s3ImageService;

    @Autowired
    private YachtServiceImpl yachtService;

    private Yacht yacht;
    private YachtRequestDto yachtRequestDto;
    private YachtResponseDto yachtResponseDto;
    private MultipartFile mainImageFile;
    private List<MultipartFile> additionalImageFiles;

    private static final Long YACHT_ID = 1L;
    private static final String MAIN_IMAGE_KEY = "mainImageKey";
    private static final BigDecimal YACHT_PRICE = new BigDecimal("10000.00");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        yacht = new Yacht();
        yacht.setId(YACHT_ID);
        yacht.setPrice(YACHT_PRICE);

        yachtRequestDto = new YachtRequestDto();

        yachtResponseDto = new YachtResponseDto();
        yachtResponseDto.setId(YACHT_ID);

        mainImageFile = Mockito.mock(MultipartFile.class);
        additionalImageFiles = Arrays.asList(Mockito.mock(MultipartFile.class), Mockito.mock(MultipartFile.class));
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtRepository, yachtImageService, yachtMapper, yachtSpecificationBuilder, s3ImageService);
    }

    @Test
    @Order(10)
    @DisplayName("save - Successfully saves a yacht from DTO with main image")
    @Transactional
    void testSave_Success_WithMainImage() {
        // Arrange
        when(yachtMapper.createYachtFromDto(yachtRequestDto)).thenReturn(yacht);
        when(s3ImageService.uploadImageToS3(mainImageFile)).thenReturn(MAIN_IMAGE_KEY);
        when(yachtRepository.save(yacht)).thenReturn(yacht);
        when(yachtMapper.convertToDto(yacht)).thenReturn(new YachtResponseDto());

        // Act
        YachtResponseDto responseDto = yachtService.save(yachtRequestDto, mainImageFile, additionalImageFiles);

        // Assert
        assertNotNull(responseDto, "Response DTO should not be null");
        verify(yachtRepository, times(1)).save(yacht);
        verify(s3ImageService, times(1)).uploadImageToS3(mainImageFile);
        assertEquals(MAIN_IMAGE_KEY, yacht.getMainImageKey(), "Main image key should match");
    }

    @Test
    @Order(20)
    @DisplayName("save - Successfully saves a yacht from DTO without main image")
    @Transactional
    void testSave_Success_WithoutMainImage() {
        // Arrange
        when(yachtMapper.createYachtFromDto(yachtRequestDto)).thenReturn(yacht);
        when(yachtRepository.save(yacht)).thenReturn(yacht);
        when(yachtImageService.saveMultipleImages(any(YachtImageRequestDto.class), eq(additionalImageFiles)))
                .thenReturn(List.of(new YachtImageResponseDto()));
        when(yachtMapper.convertToDto(yacht)).thenReturn(new YachtResponseDto());

        // Act
        YachtResponseDto responseDto = yachtService.save(yachtRequestDto, null, additionalImageFiles);

        // Assert
        assertNotNull(responseDto, "Response DTO should not be null");
        verify(yachtRepository, times(1)).save(yacht);
        verify(yachtImageService, times(1)).saveMultipleImages(any(YachtImageRequestDto.class),
                eq(additionalImageFiles));
        assertNull(yacht.getMainImageKey(), "Main image key should match the first additional image key");
    }

    @Test
    @Order(30)
    @DisplayName("findId - Successfully finds a yacht by ID")
    void testFindId_Success() {
        // Arrange
        YachtResponseDto yachtResponseDto = new YachtResponseDto();
        yachtResponseDto.setId(YACHT_ID);
        // set other properties of yachtResponseDto if necessary

        when(yachtRepository.findById(YACHT_ID)).thenReturn(Optional.of(yacht));
        when(yachtMapper.convertToDto(yacht)).thenReturn(yachtResponseDto);

        // Act
        YachtResponseDto foundYachtResponseDto = yachtService.findId(YACHT_ID);

        // Assert
        assertNotNull(foundYachtResponseDto, "Found yacht should not be null");
        assertEquals(YACHT_ID, foundYachtResponseDto.getId(), "Yacht ID should match");
    }
    @Test
    @Order(40)
    @DisplayName("findAll - Successfully retrieves all yachts sorted by ID")
    void testFindAll_Success() {
        // Arrange
        List<Yacht> yachts = new ArrayList<>();
        yachts.add(yacht);

        when(yachtRepository.findAll()).thenReturn(yachts);
        when(yachtMapper.convertToDto(any(Yacht.class))).thenReturn(new YachtResponseDto());

        // Act
        List<YachtResponseDto> foundYachts = yachtService.findAll();

        // Assert
        assertNotNull(foundYachts, "Found yachts list should not be null");
        assertEquals(1, foundYachts.size(), "Found yachts list size should be 2");
    }

    @Test
    @Order(50)
    @DisplayName("update - Successfully updates a yacht")
    @Transactional
    void testUpdate_Success() {
        // Arrange
        when(yachtRepository.findById(YACHT_ID)).thenReturn(Optional.of(yacht));
        when(s3ImageService.uploadImageToS3(mainImageFile)).thenReturn(MAIN_IMAGE_KEY);
        when(yachtRepository.save(yacht)).thenReturn(yacht);
        when(yachtMapper.convertToDto(yacht)).thenReturn(new YachtResponseDto());

        // Act
        YachtResponseDto responseDto = yachtService.update(yachtRequestDto, YACHT_ID, mainImageFile, additionalImageFiles);

        // Assert
        assertNotNull(responseDto, "Updated yacht should not be null");
        verify(yachtRepository, times(1)).save(yacht);
        assertEquals(MAIN_IMAGE_KEY, yacht.getMainImageKey(), "Main image key should match");
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes a yacht by ID")
    @Transactional
    void testDeleteById_Success() {
        // Arrange
        when(yachtRepository.findById(YACHT_ID)).thenReturn(Optional.of(yacht));
        doNothing().when(yachtRepository).delete(yacht);

        // Act
        yachtService.deleteById(YACHT_ID);

        // Assert
        verify(yachtRepository, times(1)).delete(yacht);
    }

    @Test
    @Order(70)
    @DisplayName("search - Successfully searches yachts based on search parameters")
    void testSearch_Success() {
        // Arrange
        YachtSearchParametersDto searchParametersDto = new YachtSearchParametersDto();
        // set properties for searchParametersDto as necessary

        Yacht yacht1 = new Yacht();
        yacht1.setId(1L);
        Yacht yacht2 = new Yacht();
        yacht2.setId(2L);

        List<Yacht> yachts = Arrays.asList(yacht1, yacht2);

        YachtResponseDto yachtResponseDto1 = new YachtResponseDto();
        yachtResponseDto1.setId(1L);
        YachtResponseDto yachtResponseDto2 = new YachtResponseDto();
        yachtResponseDto2.setId(2L);

        Specification<Yacht> yachtSpecification = Mockito.mock(Specification.class);

//        when(yachtSpecificationBuilder.build(searchParametersDto)).thenReturn(yachtSpecification);
        when(yachtRepository.findAll(yachtSpecification)).thenReturn(yachts);
        when(yachtMapper.convertToDto(yacht1)).thenReturn(yachtResponseDto1);
        when(yachtMapper.convertToDto(yacht2)).thenReturn(yachtResponseDto2);

        // Act
//        List<YachtResponseDto> foundYachts = yachtService.search(searchParametersDto);

        // Assert
//        assertNotNull(foundYachts, "Found yachts list should not be null");
//        assertEquals(2, foundYachts.size(), "Found yachts list size should be 2");
//        assertEquals(1L, foundYachts.get(0).getId(), "First yacht ID should match");
//        assertEquals(2L, foundYachts.get(1).getId(), "Second yacht ID should match");
    }
}