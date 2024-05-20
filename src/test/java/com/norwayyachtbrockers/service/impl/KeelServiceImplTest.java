package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.KeelMapper;
import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.repository.KeelRepository;
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
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Order(640)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class KeelServiceImplTest {

    @MockBean
    private KeelRepository keelRepository;

    @MockBean
    private KeelMapper keelMapper;

    @Autowired
    private KeelServiceImpl keelService;

    private KeelRequestDto requestDto;
    private Keel keel;

    private static final Long KEEL_ID = 1L;
    private static final String KEEL_NAME = "Test Keel";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new KeelRequestDto();
        requestDto.setName(KEEL_NAME);

        keel = new Keel();
        keel.setId(KEEL_ID);
        keel.setName(KEEL_NAME);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(keelRepository, keelMapper);
    }

    @Test
    @Order(10)
    @DisplayName("saveKeel - Successfully saves a Keel")
    void testSaveKeel_Success() {
        // Arrange
        when(keelMapper.createKeelFromDto(requestDto)).thenReturn(keel);
        when(keelRepository.save(keel)).thenReturn(keel);

        // Act
        Keel savedKeel = keelService.saveKeel(requestDto);

        // Assert
        assertNotNull(savedKeel, "Saved keel should not be null");
        assertEquals(KEEL_ID, savedKeel.getId(), "Keel ID should match");
        assertEquals(KEEL_NAME, savedKeel.getName(), "Keel name should match");

        verify(keelMapper, times(1)).createKeelFromDto(requestDto);
        verify(keelRepository, times(1)).save(keel);
    }

    @Test
    @Order(20)
    @DisplayName("findId - Successfully finds a Keel by ID")
    void testFindId_Success() {
        // Arrange
        when(keelRepository.findById(KEEL_ID)).thenReturn(Optional.of(keel));

        // Act
        Keel foundKeel = keelService.findId(KEEL_ID);

        // Assert
        assertNotNull(foundKeel, "Found keel should not be null");
        assertEquals(KEEL_ID, foundKeel.getId(), "Keel ID should match");
        assertEquals(KEEL_NAME, foundKeel.getName(), "Keel name should match");

        verify(keelRepository, times(1)).findById(KEEL_ID);
    }

    @Test
    @Order(30)
    @DisplayName("findAll - Successfully retrieves all Keels sorted by ID")
    void testFindAll_Success() {
        // Arrange
        List<Keel> keels = Arrays.asList(keel);
        when(keelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(keels);

        // Act
        List<Keel> foundKeels = keelService.findAll();

        // Assert
        assertNotNull(foundKeels, "Found keels list should not be null");
        assertFalse(foundKeels.isEmpty(), "Found keels list should not be empty");
        assertEquals(1, foundKeels.size(), "Found keels list size should be 1");
        assertEquals(KEEL_ID, foundKeels.get(0).getId(), "Keel ID should match");
        assertEquals(KEEL_NAME, foundKeels.get(0).getName(), "Keel name should match");

        verify(keelRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    @Order(40)
    @DisplayName("updateKeel - Successfully updates a Keel")
    void testUpdateKeel_Success() {
        // Arrange
        Keel updatedKeel = new Keel();
        updatedKeel.setId(KEEL_ID);
        updatedKeel.setName("Updated Keel Name");

        when(keelRepository.findById(KEEL_ID)).thenReturn(Optional.of(keel));
        when(keelMapper.updateKeelFromDto(keel, requestDto)).thenReturn(keel);
        when(keelRepository.save(any(Keel.class))).thenReturn(updatedKeel);

        // Act
        Keel result = keelService.updateKeel(requestDto, KEEL_ID);

        // Assert
        assertNotNull(result, "Updated keel should not be null");
        assertEquals(KEEL_ID, result.getId(), "Keel ID should match");
        assertEquals("Updated Keel Name", result.getName(), "Keel name should match");

        verify(keelRepository, times(1)).findById(KEEL_ID);
        verify(keelMapper, times(1)).updateKeelFromDto(eq(keel), eq(requestDto));
        verify(keelRepository, times(1)).save(keel);
    }

    @Test
    @Order(50)
    @DisplayName("deleteById - Successfully deletes a Keel by ID")
    void testDeleteById_Success() {
        // Arrange
        when(keelRepository.findById(KEEL_ID)).thenReturn(Optional.of(keel));
        doNothing().when(keelRepository).delete(keel);

        // Act
        keelService.deleteById(KEEL_ID);

        // Assert
        verify(keelRepository, times(1)).findById(KEEL_ID);
        verify(keelRepository, times(1)).delete(keel);
    }
}