package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TownMapperTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private TownMapper townMapper;

    private Town town;
    private Country country;
    private TownRequestDto dto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        townMapper = new TownMapper(countryService);
        dto = new TownRequestDto();

        town = new Town();
        town.setId(1L);
        town.setName("OldTown");
        town.setCountry(new Country());

        country = new Country();
        country.setId(dto.getCountryId());
        country.setName("CountryName");
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(1)
    public void testCreateTownFromDto_NullDto() {
        assertThrows(IllegalArgumentException.class,
                () -> townMapper.createTownFromDto(null),
                "Should throw IllegalArgumentException for null DTO");
    }

    @Test
    @DisplayName("Successfully create town from DTO")
    @Order(2)
    public void testCreateTownFromDto_ValidDto() {
        // Arrange
        dto.setName("Springfield");
        dto.setCountryId(1L);

        when(countryService.findId(dto.getCountryId())).thenReturn(country);

        // Act
        Town town = townMapper.createTownFromDto(dto);

        // Assert
        assertNotNull(town);
        assertEquals("Springfield", town.getName());
        assertEquals(country, town.getCountry());
    }

//    @Test
//    @DisplayName("Throw ConstraintViolationException when DTO contains invalid town name")
//    @Order(3)
//    public void testCreateTownFromDto_InvalidName() {
//        // Arrange
//        dto.setName(" "); // Invalid name due to length < 3
//        dto.setCountryId(1L);
//
//        // Act & Assert
//        assertThrows(ConstraintViolationException.class,
//                () -> townMapper.createTownFromDto(dto),
//                "Should throw ConstraintViolationException when the town name is too short.");
//    }

    @Test
    @DisplayName("Convert Town to TownResponseDto")
    @Order(4)
    public void testConvertToDto() {
        // Arrange
        Town town = new Town();
        town.setId(1L);
        town.setName("Springfield");

        Country country = new Country();
        country.setId(1L);
        country.setName("CountryName");
        town.setCountry(country);

        // Act
        TownResponseDto responseDto = townMapper.convertToDto(town);

        // Assert
        assertEquals(town.getId(), responseDto.getTownId());
        assertEquals(town.getName(), responseDto.getTownName());
        assertEquals(country.getName(), responseDto.getCountryName());
    }

    @Test
    @DisplayName("Throw IllegalArgumentException if town or DTO is null")
    @Order(5)
    public void testUpdateTownFromDto_NullInputs() {
        // Test null town
        assertThrows(IllegalArgumentException.class,
                () -> townMapper.updateTownFromDto(null, dto),
                "Should throw IllegalArgumentException when Town is null");

        // Test null dto
        assertThrows(IllegalArgumentException.class,
                () -> townMapper.updateTownFromDto(town, null),
                "Should throw IllegalArgumentException when DTO is null");
    }

//    @Test
//    @DisplayName("Throw ConstraintViolationException for invalid DTO")
//    @Order(6)
//    public void testUpdateTownFromDto_InvalidDto() {
//        dto.setName("nt");  // Invalid name due to length < 3
//
//        assertThrows(ConstraintViolationException.class,
//                () -> townMapper.updateTownFromDto(town, dto),
//                "Should throw ConstraintViolationException for invalid DTO");
//    }

    @Test
    @DisplayName("Successfully update town from DTO")
    @Order(7)
    public void testUpdateTownFromDto_ValidDto() {
        Country updatedCountry = new Country();
        updatedCountry.setId(dto.getCountryId());
        updatedCountry.setName("UpdatedCountry");

        when(countryService.findId(dto.getCountryId())).thenReturn(updatedCountry);

        Town updatedTown = townMapper.updateTownFromDto(town, dto);

        assertAll("Should update town properly",
                () -> assertEquals(dto.getName(), updatedTown.getName(), "Town name should be updated"),
                () -> assertEquals(updatedCountry, updatedTown.getCountry(), "Town country should be updated")
        );

        verify(countryService, times(1)).findId(dto.getCountryId());
    }
}