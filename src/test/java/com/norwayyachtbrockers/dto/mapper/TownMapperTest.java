package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.service.CountryService;
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
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(210)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TownMapperTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private TownMapper townMapper;

    private Town town;
    private Country country;
    private TownRequestDto dto;

    @BeforeEach
    void setUp() {
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

    @AfterEach
    public void tearDown() {
        Mockito.reset(countryService);
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(10)
    void testCreateTownFromDto_NullDto() {
        assertThrows(IllegalArgumentException.class,
                () -> townMapper.createTownFromDto(null),
                "Should throw IllegalArgumentException for null DTO");
    }

    @Test
    @DisplayName("Successfully create town from DTO")
    @Order(20)
    void testCreateTownFromDto_ValidDto() {
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

    @Test
    @DisplayName("Convert Town to TownResponseDto")
    @Order(40)
    void testConvertToDto() {
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
    @Order(50)
    void testUpdateTownFromDto_NullInputs() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> townMapper.updateTownFromDto(null, dto),
                "Should throw IllegalArgumentException when Town is null");
        assertThrows(IllegalArgumentException.class,
                () -> townMapper.updateTownFromDto(town, null),
                "Should throw IllegalArgumentException when DTO is null");
    }

    @Test
    @DisplayName("Successfully update town from DTO")
    @Order(70)
    void testUpdateTownFromDto_ValidDto() {
        // Arrange
        Country updatedCountry = new Country();
        updatedCountry.setId(dto.getCountryId());
        updatedCountry.setName("UpdatedCountry");

        when(countryService.findId(dto.getCountryId())).thenReturn(updatedCountry);

        // Act
        Town updatedTown = townMapper.updateTownFromDto(town, dto);

        // Assert
        assertAll("Should update town properly",
                () -> assertEquals(dto.getName(), updatedTown.getName(), "Town name should be updated"),
                () -> assertEquals(updatedCountry, updatedTown.getCountry(), "Town country should be updated")
        );

        verify(countryService, times(1)).findId(dto.getCountryId());
    }
}