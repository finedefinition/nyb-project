package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Order(180)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FuelMapperTest {

    @Autowired
    private FuelMapper fuelMapper;

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
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(10)
    void testCreateFuelFromDto_NullDto() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fuelMapper.createFuelFromDto(null),
                "Should throw IllegalArgumentException when the DTO is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name is null on creation")
    @Order(20)
    void testCreateFuelFromDto_NameNull() {
        // Arrange
        dto.setName(null);  // Setting null as Name

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> fuelMapper.createFuelFromDto(dto),
                "Should throw ConstraintViolationException when the Name is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name shorter than 3 characters")
    @Order(30)
    void testNameTooShort() {
        // Arrange
        dto.setName(INVALID_NAME_TOO_SHORT);  // Setting Name shorter than the minimum length

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> fuelMapper.createFuelFromDto(dto),
                "Should throw ConstraintViolationException when the Name is too short.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name longer than 20 characters")
    @Order(40)
    void testNameTooLong() {
        // Arrange
        dto.setName(INVALID_NAME_TOO_LONG);  // Setting Name longer than the maximum length

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> fuelMapper.createFuelFromDto(dto),
                "Should throw ConstraintViolationException when the Name is too long");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name with invalid characters")
    @Order(50)
    void testNameInvalidCharacters() {
        // Arrange
        dto.setName(INVALID_NAME_WITH_DIGITS);  // Setting Name with numbers, which are invalid

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> fuelMapper.createFuelFromDto(dto),
                "Should throw ConstraintViolationException when the Name contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name not starting with capital letter")
    @Order(60)
    void testNameDoesNotStartWithCapital() {
        // Arrange
        dto.setName(INVALID_NAME_LOWERCASE);  // Starting with a lowercase letter

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> fuelMapper.createFuelFromDto(dto),
                "Should throw ConstraintViolationException for names not starting with a capital letter.");
    }

    @Test
    @DisplayName("Fuel name is trimmed correctly")
    @Order(70)
    void testTrimString() {
        // Arrange
        dto.setName(FUEL_NAME + "     ");  // Name with trailing spaces

        // Act
        Fuel createdFuel = fuelMapper.createFuelFromDto(dto);

        // Assert
        assertEquals(FUEL_NAME, createdFuel.getName(), "Name should be trimmed correctly");
    }

    @Test
    @DisplayName("Successfully create Fuel from valid DTO")
    @Order(80)
    void testCreateFuelFromDto_ValidDto() {
        // Arrange
        dto.setName(FUEL_NAME);

        // Act
        Fuel createdFuel = fuelMapper.createFuelFromDto(dto);

        // Assert
        assertNotNull(createdFuel, "The created Fuel should not be null.");
        assertEquals(FUEL_NAME, createdFuel.getName(),
                "The name of the created Fuel should match the DTO name.");
    }

    // Test cases for updateFuelFromDto(Fuel fuel, FuelRequestDto dto)

    @Test
    @DisplayName("Throw ConstraintViolationException for null Fuel on update")
    @Order(90)
    void testUpdateFuelFromDto_NullFuel() {
        // Assert
        assertThrows(ConstraintViolationException.class, () -> fuelMapper.updateFuelFromDto(null, dto),
                "Should throw ConstraintViolationException when the Fuel is null.");
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on update")
    @Order(100)
    void testUpdateFuelFromDto_NullDto() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> fuelMapper.updateFuelFromDto(fuel, null),
                "Should throw IllegalArgumentException when the DTO is null.");
    }

    @Test
    @DisplayName("Update Fuel name using valid Fuel and DTO")
    @Order(110)
    void testUpdateFuelFromDto_ValidArguments() {
        // Arrange
        fuel.setName(FUEL_NAME);
        dto.setName(UPDATED_FUEL_NAME);

        // Act
        Fuel updatedFuel = fuelMapper.updateFuelFromDto(fuel, dto);

        // Assert
        assertEquals(UPDATED_FUEL_NAME, updatedFuel.getName(),
                "The country name should be updated to match the name specified in the DTO.");
    }
}