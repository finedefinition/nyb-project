package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.TownMapper;
import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.CountryRepository;
import com.norwayyachtbrockers.repository.TownRepository;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(660)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TownServiceImplTest {

    @MockBean
    private TownRepository townRepository;

    @MockBean
    private TownMapper townMapper;

    @MockBean
    private CountryRepository countryRepository;

    @Autowired
    private TownServiceImpl townService;

    private Town town;
    private TownRequestDto townRequestDto;
    private Country country;

    private static final Long TOWN_ID = 1L;
    private static final String TOWN_NAME = "TownName";
    private static final Long COUNTRY_ID = 1L;
    private static final String COUNTRY_NAME = "CountryName";
    private static final String DTO_TOWN_NAME = "DtoTownName";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        country = new Country();
        country.setId(COUNTRY_ID);
        country.setName(COUNTRY_NAME);
        country.setTowns(new HashSet<>());  // Initialize towns set

        town = new Town();
        town.setId(TOWN_ID);
        town.setName(TOWN_NAME);
        town.setCountry(country);
        town.setYachts(new HashSet<>());  // Initialize yachts set
        country.getTowns().add(town);  // Add town to country's towns set

        townRequestDto = new TownRequestDto();
        townRequestDto.setName(DTO_TOWN_NAME);
        townRequestDto.setCountryId(COUNTRY_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(townRepository, townMapper, countryRepository);
    }

    @Test
    @Order(10)
    @DisplayName("saveTown - Successfully saves a town from DTO")
    void testSaveTown_Success() {
        // Arrange
        when(countryRepository.findById(COUNTRY_ID)).thenReturn(Optional.of(country));
        when(townMapper.createTownFromDto(townRequestDto)).thenReturn(town);
        when(townRepository.save(town)).thenReturn(town);
        when(townMapper.convertToDto(town)).thenReturn(new TownResponseDto(TOWN_ID, DTO_TOWN_NAME, COUNTRY_NAME));

        // Act
        TownResponseDto savedTown = townService.saveTown(townRequestDto);

        // Assert
        assertNotNull(savedTown, "Saved town should not be null");
        assertEquals(TOWN_ID, savedTown.getTownId(), "Town ID should match");
        assertEquals(DTO_TOWN_NAME, savedTown.getTownName(), "Town name should match");

        verify(townRepository, times(1)).save(town);
    }

    @Test
    @Order(20)
    @DisplayName("findId - Successfully finds a town by ID")
    void testFindId_Success() {
        // Arrange
        when(townRepository.findById(TOWN_ID)).thenReturn(Optional.of(town));
        TownResponseDto expectedDto = new TownResponseDto(TOWN_ID, TOWN_NAME, COUNTRY_NAME);
        when(townMapper.convertToDto(town)).thenReturn(expectedDto);

        // Act
        TownResponseDto foundTownDto = townService.findId(TOWN_ID);

        // Assert
        assertNotNull(foundTownDto, "Found town DTO should not be null");
        assertEquals(TOWN_ID, foundTownDto.getTownId(), "Town ID should match");
        assertEquals(TOWN_NAME, foundTownDto.getTownName(), "Town name should match");
        assertEquals(COUNTRY_NAME, foundTownDto.getCountryName(), "Country name should match");

        verify(townRepository, times(1)).findById(TOWN_ID);
        verify(townMapper, times(1)).convertToDto(town);
    }

    @Test
    @Order(25)
    @DisplayName("findTownById - Successfully finds a town by ID")
    void testFindTownById_Success() {
        // Arrange
        when(townRepository.findById(TOWN_ID)).thenReturn(Optional.of(town));

        // Act
        Town foundTown = townService.findTownById(TOWN_ID);

        // Assert
        assertNotNull(foundTown, "Found town should not be null");
        assertEquals(TOWN_ID, foundTown.getId(), "Town ID should match");
        assertEquals(TOWN_NAME, foundTown.getName(), "Town name should match");

        verify(townRepository, times(1)).findById(TOWN_ID);
    }

    @Test
    @Order(30)
    @DisplayName("findAll - Successfully retrieves all towns sorted by ID")
    void testFindAll_Success() {
        // Arrange
        Town anotherTown = new Town();
        anotherTown.setId(2L);
        anotherTown.setName("AnotherTown");
        anotherTown.setCountry(country);

        List<Town> towns = Arrays.asList(town, anotherTown);
        when(townRepository.findAll()).thenReturn(towns);
        when(townMapper.convertToDto(town)).thenReturn(new TownResponseDto(TOWN_ID, TOWN_NAME, COUNTRY_NAME));
        when(townMapper.convertToDto(anotherTown)).thenReturn(new TownResponseDto(2L, "AnotherTown", COUNTRY_NAME));

        // Act
        List<TownResponseDto> foundTowns = townService.findAll();

        // Assert
        assertNotNull(foundTowns, "Found towns list should not be null");
        assertEquals(2, foundTowns.size(), "Found towns list size should be 2");
        assertEquals(TOWN_ID, foundTowns.get(0).getTownId(), "First town ID should match");
        assertEquals("AnotherTown", foundTowns.get(1).getTownName(), "Second town name should match");
    }

    @Test
    @Order(40)
    @DisplayName("updateTown - Successfully updates a town from DTO")
    void testUpdateTown_Success() {
        // Arrange
        when(townRepository.findById(TOWN_ID)).thenReturn(Optional.of(town));
        when(countryRepository.findById(COUNTRY_ID)).thenReturn(Optional.of(country));
        // Mocking the behavior of updateTownFromDto
        doAnswer(invocation -> {
            Town existingTown = invocation.getArgument(0);
            TownRequestDto dto = invocation.getArgument(1);
            existingTown.setName(dto.getName());
            existingTown.setCountry(country);  // Ensure the country is set correctly
            return null;
        }).when(townMapper).updateTownFromDto(any(Town.class), any(TownRequestDto.class));

        Town updatedTown = new Town();
        updatedTown.setId(TOWN_ID);
        updatedTown.setName(DTO_TOWN_NAME);
        updatedTown.setCountry(country);

        when(townRepository.save(any(Town.class))).thenReturn(updatedTown);
        when(townMapper.convertToDto(any(Town.class)))
                .thenReturn(new TownResponseDto(TOWN_ID, DTO_TOWN_NAME, COUNTRY_NAME));

        // Act
        TownResponseDto updatedTownDto = townService.updateTown(townRequestDto, TOWN_ID);

        // Assert
        assertNotNull(updatedTownDto, "Updated town should not be null");
        assertEquals(TOWN_ID, updatedTownDto.getTownId(), "Town ID should match");
        assertEquals(DTO_TOWN_NAME, updatedTownDto.getTownName(), "Town name should match");
    }

    @Test
    @Order(50)
    @DisplayName("deleteById - Successfully deletes a town by ID")
    void testDeleteById_Success() {
        // Arrange
        when(townRepository.findById(TOWN_ID)).thenReturn(Optional.of(town));
        doNothing().when(townRepository).delete(town);

        // Act
        townService.deleteById(TOWN_ID);

        // Assert
        verify(townRepository, times(1)).delete(town);
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Detach town from its country when country is not null")
    void testDeleteById_DetachFromCountry_NotNull() {
        // Arrange
        Country mockCountry = mock(Country.class);
        Town mockTown = mock(Town.class);
        Set<Town> mockTowns = mock(Set.class);
        when(mockTown.getCountry()).thenReturn(mockCountry);
        when(mockCountry.getTowns()).thenReturn(mockTowns);
        when(townRepository.findById(TOWN_ID)).thenReturn(Optional.of(mockTown));

        // Act
        townService.deleteById(TOWN_ID);

        // Assert
        verify(mockTowns, times(1)).remove(mockTown);
        verify(townRepository, times(1)).delete(mockTown);
    }

    @Test
    @Order(70)
    @DisplayName("deleteById - Do nothing when country is null")
    void testDeleteById_DetachFromCountry_Null() {
        // Arrange
        Town mockTown = mock(Town.class);
        when(mockTown.getCountry()).thenReturn(null);
        when(townRepository.findById(TOWN_ID)).thenReturn(Optional.of(mockTown));

        // Act
        townService.deleteById(TOWN_ID);

        // Assert
        verify(mockTown, times(1)).getCountry();
        verify(townRepository, times(1)).delete(mockTown);
    }

    @Test
    @Order(80)
    @DisplayName("deleteById - Remove yachts associated with the town when yachts is not null")
    void testDeleteById_RemoveYachts_NotNull() {
        // Arrange
        Yacht mockYacht = mock(Yacht.class);
        Town mockTown = mock(Town.class);
        Set<Yacht> mockYachts = new HashSet<>(Arrays.asList(mockYacht));
        when(mockTown.getYachts()).thenReturn(mockYachts);
        when(townRepository.findById(TOWN_ID)).thenReturn(Optional.of(mockTown));

        // Act
        townService.deleteById(TOWN_ID);

        // Assert
        verify(mockYacht, times(1)).setTown(null);
        verify(townRepository, times(1)).delete(mockTown);
    }

    @Test
    @Order(90)
    @DisplayName("deleteById - Do nothing when yachts is null")
    void testDeleteById_RemoveYachts_Null() {
        // Arrange
        Town mockTown = mock(Town.class);
        when(mockTown.getYachts()).thenReturn(null);
        when(townRepository.findById(TOWN_ID)).thenReturn(Optional.of(mockTown));

        // Act
        townService.deleteById(TOWN_ID);

        // Assert
        verify(mockTown, times(1)).getYachts();
        verify(townRepository, times(1)).delete(mockTown);
    }
}