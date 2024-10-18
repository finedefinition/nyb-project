package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.FuelMapper;
import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.repository.FuelRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
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

@Order(630)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FuelServiceImplTest {

    @MockBean
    private FuelRepository fuelRepository;

    @Autowired
    private FuelServiceImpl fuelService;

    private FuelRequestDto requestDto;
    private Fuel fuel;

    private static final Long FUEL_ID = 1L;
    private static final String FUEL_NAME = "Diesel";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new FuelRequestDto();
        requestDto.setName(FUEL_NAME);

        fuel = new Fuel();
        fuel.setId(FUEL_ID);
        fuel.setName(FUEL_NAME);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(fuelRepository);
    }

    @Test
    @Order(10)
    @DisplayName("saveFuel - Successfully saves a Fuel")
    @Transactional
    void testSaveFuel_Success() {
        // Arrange
        when(fuelRepository.save(fuel)).thenReturn(fuel);

        when(fuelRepository.save(Mockito.any(Fuel.class))).thenAnswer(invocation -> {
            Fuel savedFuel = invocation.getArgument(0);
            savedFuel.setId(FUEL_ID);
            return savedFuel;
        });

        // Act
        Fuel savedFuel = fuelService.saveFuel(requestDto);

        // Assert
        assertNotNull(savedFuel, "Saved fuel should not be null");
        assertEquals(FUEL_ID, savedFuel.getId(), "Fuel ID should match");
        assertEquals(FUEL_NAME, savedFuel.getName(), "Fuel name should match");

        ArgumentCaptor<Fuel> captor = ArgumentCaptor.forClass(Fuel.class);
        verify(fuelRepository, times(1)).save(captor.capture());

        Fuel capturedFuel = captor.getValue();
        assertEquals(FUEL_NAME, capturedFuel.getName(), "Captured fuel name should match DTO name");
    }

    @Test
    @Order(20)
    @DisplayName("findId - Successfully finds a Fuel by ID")
    void testFindId_Success() {
        // Arrange
        when(fuelRepository.findById(FUEL_ID)).thenReturn(Optional.of(fuel));

        // Act
        Fuel foundFuel = fuelService.findId(FUEL_ID);

        // Assert
        assertNotNull(foundFuel, "Found fuel should not be null");
        assertEquals(FUEL_ID, foundFuel.getId(), "Fuel ID should match");
        assertEquals(FUEL_NAME, foundFuel.getName(), "Fuel name should match");

        verify(fuelRepository, times(1)).findById(FUEL_ID);
    }

    @Test
    @Order(30)
    @DisplayName("findAll - Successfully retrieves all Fuels sorted by ID")
    void testFindAll_Success() {
        // Arrange
        List<Fuel> fuels = Arrays.asList(fuel);
        when(fuelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(fuels);

        // Act
        List<Fuel> foundFuels = fuelService.findAll();

        // Assert
        assertNotNull(foundFuels, "Fuel list should not be null");
        assertFalse(foundFuels.isEmpty(), "Found fuels list should not be empty");
        assertEquals(1, foundFuels.size(), "Fuel list size should be 1");
        assertEquals(FUEL_ID, foundFuels.get(0).getId(), "Fuel ID should match");
        assertEquals(FUEL_NAME, foundFuels.get(0).getName(), "Fuel name should match");

        verify(fuelRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    @Order(40)
    @DisplayName("updateFuel - Successfully updates fuel")
    @Transactional
    void testUpdateFuel_Success() {
        // Arrange
        Fuel updatedFuel = new Fuel();
        updatedFuel.setId(FUEL_ID);
        updatedFuel.setName("Updated Fuel Name");

        when(fuelRepository.findById(FUEL_ID)).thenReturn(Optional.of(fuel));
        when(fuelRepository.save(any(Fuel.class))).thenReturn(updatedFuel);

        // Act
        Fuel result = fuelService.updateFuel(requestDto, FUEL_ID);

        // Assert
        assertNotNull(result, "Updated fuel should not be null");
        assertEquals(FUEL_ID, result.getId(), "Fuel ID should match");
        assertEquals("Updated Fuel Name", result.getName(), "Fuel name should match");

        verify(fuelRepository, times(1)).findById(FUEL_ID);
        verify(fuelRepository, times(1)).save(fuel);
    }

    @Test
    @Order(50)
    @DisplayName("deleteById - Successfully deletes fuel by ID")
    @Transactional
    void testDeleteById_Success() {
        // Arrange
        when(fuelRepository.findById(FUEL_ID)).thenReturn(Optional.of(fuel));
        doNothing().when(fuelRepository).delete(fuel);

        // Act
        fuelService.deleteById(FUEL_ID);

        // Assert
        verify(fuelRepository, times(1)).findById(FUEL_ID);
        verify(fuelRepository, times(1)).delete(fuel);
    }
}