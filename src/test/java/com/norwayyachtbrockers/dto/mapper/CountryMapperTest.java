package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.model.Country;
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
@Order(170)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CountryMapperTest {

    @Autowired
    private CountryMapper countryMapper;

    private Country country;
    private CountryRequestDto dto;

    private static final String COUNTRY_NAME = "Canada";
    private static final String UPDATED_COUNTRY_NAME = "Costa Rica";
    private static final String INVALID_NAME_WITH_DIGITS = "12345";
    private static final String INVALID_NAME_LOWERCASE = "lowercasename";
    private static final String INVALID_NAME_TOO_LONG = "Abcdefghijklmnopqrstuvwxyz";
    private static final String INVALID_NAME_TOO_SHORT = "Ab";

    @BeforeEach
    void setUp() {
        country = new Country();
        dto = new CountryRequestDto();
    }

    // Test cases for createCountryFromDto(CountryRequestDto dto)

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(10)
    void testCreateCountryFromDto_NullDto() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> countryMapper.createCountryFromDto(null),
                "Should throw IllegalArgumentException when the DTO is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name is null on creation")
    @Order(20)
    void testCreateCountryFromDto_NameNull() {
        // Arrange
        dto.setName(null);  // Setting null as Name

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> countryMapper.createCountryFromDto(dto),
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
                () -> countryMapper.createCountryFromDto(dto),
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
                () -> countryMapper.createCountryFromDto(dto),
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
                () -> countryMapper.createCountryFromDto(dto),
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
                () -> countryMapper.createCountryFromDto(dto),
                "Should throw ConstraintViolationException for names not starting with a capital letter.");
    }

    @Test
    @DisplayName("Country name is trimmed correctly")
    @Order(70)
    void testTrimString() {
        // Arrange
        dto.setName(COUNTRY_NAME + "     ");  // Name with trailing spaces

        // Act
        Country createdCountry = countryMapper.createCountryFromDto(dto);

        // Assert
        assertEquals(COUNTRY_NAME, createdCountry.getName(), "Name should be trimmed correctly");
    }

    @Test
    @DisplayName("Successfully create Country from valid DTO")
    @Order(80)
    void testCreateCountryFromDto_ValidDto() {
        // Arrange
        dto.setName(COUNTRY_NAME);

        // Act
        Country createdCountry = countryMapper.createCountryFromDto(dto);

        // Assert
        assertNotNull(createdCountry, "The created Country should not be null.");
        assertEquals(COUNTRY_NAME, createdCountry.getName(),
                "The name of the created Country should match the DTO name.");
    }

    // Test cases for updateCountryFromDto(Country country, CountryRequestDto dto)

    @Test
    @DisplayName("Throw ConstraintViolationException for null Country on update")
    @Order(90)
    void testUpdateCountryFromDto_NullCountry() {
        // Assert
        assertThrows(ConstraintViolationException.class,
                () -> countryMapper.updateCountryFromDto(null, dto),
                "Should throw ConstraintViolationException when the Country is null.");
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on update")
    @Order(100)
    void testUpdateCountryFromDto_NullDto() {
        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> countryMapper.updateCountryFromDto(country, null),
                "Should throw IllegalArgumentException when the DTO is null.");
    }

    @Test
    @DisplayName("Update Country name using valid Country and DTO")
    @Order(110)
    void testUpdateCountryFromDto_ValidArguments() {
        // Arrange
        country.setName(COUNTRY_NAME);
        dto.setName(UPDATED_COUNTRY_NAME);

        // Act
        Country updatedCountry = countryMapper.updateCountryFromDto(country, dto);

        // Assert
        assertEquals(UPDATED_COUNTRY_NAME, updatedCountry.getName(),
                "The country name should be updated to match the name specified in the DTO.");
    }
}