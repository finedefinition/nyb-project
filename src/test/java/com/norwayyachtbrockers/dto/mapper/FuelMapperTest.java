package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Order(180)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FuelMapperTest {
    private Fuel fuel;
    private FuelRequestDto dto;

    private static final String FUEL_NAME = "Diesel";
    private static final String UPDATED_FUEL_NAME = "Updated Name";
    private static final String INVALID_NAME_TOO_LONG = "Abcdefghijklmnopqrstuvwxyz";
    private static final String INVALID_NAME_TOO_SHORT = "Ab";
    private static final String INVALID_NAME_WITH_DIGITS = "12345";
    private static final String INVALID_NAME_LOWERCASE = "lowercasename";

    @BeforeEach
    void setUp() {
        fuel = new Fuel();
        dto = new FuelRequestDto();
    }

    // Test cases for createFuelFromDto(FuelRequestDto dto)

    @Test
    @DisplayName("Successfully create Fuel from valid DTO")
    @Order(80)
    void testCreateFuelFromDto_ValidDto() {
        // Arrange
        dto.setName(FUEL_NAME);

        // Act
        Fuel createdFuel = FuelMapper.createFuelFromDto(dto);

        // Assert
        assertNotNull(createdFuel, "The created Fuel should not be null.");
        assertEquals(FUEL_NAME, createdFuel.getName(),
                "The name of the created Fuel should match the DTO name.");
    }

    // Test cases for updateFuelFromDto(Fuel fuel, FuelRequestDto dto)

    @Test
    @DisplayName("Update Fuel name using valid Fuel and DTO")
    @Order(110)
    void testUpdateFuelFromDto_ValidArguments() {
        // Arrange
        fuel.setName(FUEL_NAME);
        dto.setName(UPDATED_FUEL_NAME);

        // Act
        Fuel updatedFuel = FuelMapper.updateFuelFromDto(fuel, dto);

        // Assert
        assertEquals(UPDATED_FUEL_NAME, updatedFuel.getName(),
                "The country name should be updated to match the name specified in the DTO.");
    }
}
