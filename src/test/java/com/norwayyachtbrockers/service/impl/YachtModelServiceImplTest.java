package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtModelMapper;
import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.FuelRepository;
import com.norwayyachtbrockers.repository.KeelRepository;
import com.norwayyachtbrockers.repository.yachtmodel.YachtModelRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Order(700)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtModelServiceImplTest {

    @MockBean
    private YachtModelRepository yachtModelRepository;
    @MockBean
    private YachtModelMapper yachtModelMapper;
    @MockBean
    private KeelRepository keelRepository;
    @MockBean
    private FuelRepository fuelRepository;

    @Autowired
    private YachtModelServiceImpl yachtModelService;

    private YachtModel yachtModel;
    private YachtModelRequestDto yachtModelRequestDto;
    private Keel keel;
    private Fuel fuel;

    private static final Long YACHT_MODEL_ID = 1L;
    private static final Long KEEL_TYPE_ID = 1L;
    private static final Long FUEL_TYPE_ID = 1L;
    private static final String YACHT_MODEL = "YachtModelName";
    private static final String DTO_NAME = "DtoYachtModelName";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        yachtModel = new YachtModel();
        yachtModel.setId(YACHT_MODEL_ID);
        yachtModel.setModel(YACHT_MODEL);

        keel = new Keel();
        keel.setId(KEEL_TYPE_ID);
        keel.setName("Keel");

        fuel = new Fuel();
        fuel.setId(FUEL_TYPE_ID);
        fuel.setName("Fuel");

        yachtModelRequestDto = new YachtModelRequestDto();
        yachtModelRequestDto.setKeelTypeId(KEEL_TYPE_ID);
        yachtModelRequestDto.setFuelTypeId(FUEL_TYPE_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtModelMapper, yachtModelRepository, keelRepository, fuelRepository);
    }

    @Test
    @Order(10)
    @DisplayName("saveYachtModel - Successfully saves a yacht model from DTO")
    void testSaveYachtModel_Success() {
        // Arrange
        when(keelRepository.findById(KEEL_TYPE_ID)).thenReturn(Optional.of(keel));
        when(fuelRepository.findById(FUEL_TYPE_ID)).thenReturn(Optional.of(fuel));
        when(yachtModelMapper.createYachtModelFromDto(yachtModelRequestDto)).thenReturn(yachtModel);
        when(yachtModelRepository.save(yachtModel)).thenReturn(yachtModel);

        // Act
        YachtModel savedYachtModel = yachtModelService.saveYachtModel(yachtModelRequestDto);

        // Assert
        assertNotNull(savedYachtModel, "Saved yacht model should not be null");
        assertEquals(YACHT_MODEL_ID, savedYachtModel.getId(), "Yacht model ID should match");
        assertEquals(YACHT_MODEL, savedYachtModel.getModel(), "Yacht model name should match");

        verify(yachtModelRepository, times(1)).save(yachtModel);
    }

    @Test
    @Order(20)
    @DisplayName("findId - Successfully finds a yacht model by ID")
    void testFindId_Success() {
        // Arrange
        when(yachtModelRepository.findById(YACHT_MODEL_ID)).thenReturn(Optional.of(yachtModel));

        // Act
        YachtModel foundYachtModel = yachtModelService.findId(YACHT_MODEL_ID);

        // Assert
        assertNotNull(foundYachtModel, "Found yacht model should not be null");
        assertEquals(YACHT_MODEL_ID, foundYachtModel.getId(), "Yacht model ID should match");
        assertEquals(YACHT_MODEL, foundYachtModel.getModel(), "Yacht model name should match");
    }

    @Test
    @Order(30)
    @DisplayName("findAll - Successfully retrieves all yacht models sorted by ID")
    void testFindAll_Success() {
        // Arrange
        YachtModel anotherYachtModel = new YachtModel();
        anotherYachtModel.setId(2L);
        anotherYachtModel.setModel("AnotherYachtModel");

        List<YachtModel> yachtModels = Arrays.asList(yachtModel, anotherYachtModel);
        when(yachtModelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(yachtModels);

        // Act
        List<YachtModel> foundYachtModels = yachtModelService.findAll();

        // Assert
        assertNotNull(foundYachtModels,
                "Found yacht models list should not be null");
        assertEquals(2, foundYachtModels.size(),
                "Found yacht models list size should be 2");
        assertEquals(YACHT_MODEL_ID, foundYachtModels.get(0).getId(),
                "First yacht model ID should match");
        assertEquals("AnotherYachtModel", foundYachtModels.get(1).getModel(),
                "Second yacht model name should match");
    }

    @Test
    @Order(40)
    @DisplayName("updateYachtModel - Successfully updates a yacht model from DTO")
    void testUpdateYachtModel_Success() {
        // Arrange
        when(yachtModelRepository.findById(YACHT_MODEL_ID)).thenReturn(Optional.of(yachtModel));
        when(keelRepository.findById(KEEL_TYPE_ID)).thenReturn(Optional.of(keel));
        when(fuelRepository.findById(FUEL_TYPE_ID)).thenReturn(Optional.of(fuel));
        when(yachtModelRepository.save(yachtModel)).thenReturn(yachtModel);

        // Act
        YachtModel updatedYachtModel = yachtModelService
                .updateYachtModel(yachtModelRequestDto, YACHT_MODEL_ID);

        // Assert
        assertNotNull(updatedYachtModel, "Updated yacht model should not be null");
        assertEquals(YACHT_MODEL_ID, updatedYachtModel.getId(), "Yacht model ID should match");
        assertEquals(YACHT_MODEL, updatedYachtModel.getModel(), "Yacht model name should match");

        verify(yachtModelRepository, times(1)).save(yachtModel);
    }

    @Test
    @Order(41)
    @DisplayName("updateYachtModel - Successfully updates the keel type of a yacht model")
    void testUpdateYachtModel_KeelTypeChanged() {
        // Arrange
        when(yachtModelRepository.findById(YACHT_MODEL_ID)).thenReturn(Optional.of(yachtModel));
        when(keelRepository.findById(KEEL_TYPE_ID)).thenReturn(Optional.of(keel));
        when(fuelRepository.findById(FUEL_TYPE_ID)).thenReturn(Optional.of(fuel));
        when(yachtModelRepository.save(yachtModel)).thenReturn(yachtModel);

        yachtModel.setKeelType(keel);

        // Act
        YachtModel updatedYachtModel = yachtModelService.updateYachtModel(yachtModelRequestDto, YACHT_MODEL_ID);

        // Assert
        assertNotNull(updatedYachtModel, "Updated yacht model should not be null");
        assertEquals(keel, updatedYachtModel.getKeelType(), "Keel type should be updated");

        verify(yachtModelRepository, times(1)).save(yachtModel);
    }

    @Test
    @Order(42)
    @DisplayName("updateYachtModel - Successfully updates the fuel type of a yacht model")
    void testUpdateYachtModel_FuelTypeChanged() {
        // Arrange
        when(yachtModelRepository.findById(YACHT_MODEL_ID)).thenReturn(Optional.of(yachtModel));
        when(keelRepository.findById(KEEL_TYPE_ID)).thenReturn(Optional.of(keel));
        when(fuelRepository.findById(FUEL_TYPE_ID)).thenReturn(Optional.of(fuel));
        when(yachtModelRepository.save(yachtModel)).thenReturn(yachtModel);

        yachtModel.setFuelType(fuel);

        // Act
        YachtModel updatedYachtModel = yachtModelService.updateYachtModel(yachtModelRequestDto, YACHT_MODEL_ID);

        // Assert
        assertNotNull(updatedYachtModel, "Updated yacht model should not be null");
        assertEquals(fuel, updatedYachtModel.getFuelType(), "Fuel type should be updated");

        verify(yachtModelRepository, times(1)).save(yachtModel);
    }

    @Test
    @Order(50)
    @DisplayName("deleteById - Successfully deletes a yacht model by ID")
    void testDeleteById_Success() {
        // Arrange
        when(yachtModelRepository.findById(YACHT_MODEL_ID)).thenReturn(Optional.of(yachtModel));

        doNothing().when(yachtModelRepository).delete(yachtModel);

        // Act
        yachtModelService.deleteById(YACHT_MODEL_ID);

        // Assert
        verify(yachtModelRepository, times(1)).delete(yachtModel);
    }

    @Test
    @Order(60)
    @DisplayName("findByKeelType_Id - Successfully finds yacht models by keel type ID")
    void testFindByKeelType_Id_Success() {
        // Arrange
        when(keelRepository.findById(KEEL_TYPE_ID)).thenReturn(Optional.of(keel));
        when(yachtModelRepository.findByKeelType_Id(KEEL_TYPE_ID)).thenReturn(Collections.singletonList(yachtModel));

        // Act
        List<YachtModel> foundYachtModels = yachtModelService.findByKeelType_Id(KEEL_TYPE_ID);

        // Assert
        assertNotNull(foundYachtModels, "Found yacht models list should not be null");
        assertEquals(1, foundYachtModels.size(), "Found yacht models list size should be 1");
        assertEquals(YACHT_MODEL_ID, foundYachtModels.get(0).getId(), "Yacht model ID should match");
    }

    @Test
    @Order(70)
    @DisplayName("findByFuelType_Id - Successfully finds yacht models by fuel type ID")
    void testFindByFuelType_Id_Success() {
        // Arrange
        when(fuelRepository.findById(FUEL_TYPE_ID)).thenReturn(Optional.of(fuel));
        when(yachtModelRepository.findByFuelType_Id(FUEL_TYPE_ID)).thenReturn(Collections.singletonList(yachtModel));

        // Act
        List<YachtModel> foundYachtModels = yachtModelService.findByFuelType_Id(FUEL_TYPE_ID);

        // Assert
        assertNotNull(foundYachtModels, "Found yacht models list should not be null");
        assertEquals(1, foundYachtModels.size(), "Found yacht models list size should be 1");
        assertEquals(YACHT_MODEL_ID, foundYachtModels.get(0).getId(), "Yacht model ID should match");
    }
}