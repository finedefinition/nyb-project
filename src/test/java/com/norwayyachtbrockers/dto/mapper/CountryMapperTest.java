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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Order(170)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CountryMapperTest {
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
    @DisplayName("Country name is trimmed correctly")
    @Order(70)
    void testTrimString() {
        // Arrange
        dto.setName(COUNTRY_NAME + "     ");  // Name with trailing spaces

        // Act
        Country createdCountry = CountryMapper.createCountryFromDto(dto);

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
        Country createdCountry = CountryMapper.createCountryFromDto(dto);

        // Assert
        assertNotNull(createdCountry, "The created Country should not be null.");
        assertEquals(COUNTRY_NAME, createdCountry.getName(),
                "The name of the created Country should match the DTO name.");
    }

    // Test cases for updateCountryFromDto(Country country, CountryRequestDto dto)

    @Test
    @DisplayName("Update Country name using valid Country and DTO")
    @Order(110)
    void testUpdateCountryFromDto_ValidArguments() {
        // Arrange
        country.setName(COUNTRY_NAME);
        dto.setName(UPDATED_COUNTRY_NAME);

        // Act
        Country updatedCountry = CountryMapper.updateCountryFromDto(country, dto);

        // Assert
        assertEquals(UPDATED_COUNTRY_NAME, updatedCountry.getName(),
                "The country name should be updated to match the name specified in the DTO.");
    }
}