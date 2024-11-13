package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.CountryRepository;
import com.norwayyachtbrockers.repository.projections.CountryProjection;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(610)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CountryServiceImplTest {

    @MockBean
    private CountryRepository countryRepository;

    @Autowired
    private CountryServiceImpl countryService;

    private Country country;
    private CountryRequestDto countryRequestDto;

    private static final Long COUNTRY_ID = 1L;
    private static final String COUNTRY_NAME = "CountryName";
    private static final String DTO_NAME = "CountryName";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        country = new Country();
        country.setId(COUNTRY_ID);
        country.setName(COUNTRY_NAME);

        countryRequestDto = new CountryRequestDto();
        countryRequestDto.setName(DTO_NAME);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(countryRepository);
    }

    @Test
    @Order(10)
    @DisplayName("saveCountry - Successfully saves a country from DTO")
    @Transactional
    void testSaveCountry_Success() {
        // Arrange
        when(countryRepository.save(Mockito.any(Country.class))).thenAnswer(invocation -> {
            Country savedCountry = invocation.getArgument(0);
            savedCountry.setId(COUNTRY_ID);
            return savedCountry;
        });

        // Act
        Country savedCountry = countryService.saveCountry(countryRequestDto);

        // Assert
        assertNotNull(savedCountry, "Saved country should not be null");
        assertEquals(COUNTRY_ID, savedCountry.getId(), "Country ID should match");
        assertEquals(COUNTRY_NAME, savedCountry.getName(), "Country name should match");

        ArgumentCaptor<Country> captor = ArgumentCaptor.forClass(Country.class);
        verify(countryRepository, times(1)).save(captor.capture());

        Country capturedCountry = captor.getValue();
        assertEquals(DTO_NAME, capturedCountry.getName(), "Captured country name should match DTO name");
    }

    @Test
    @Order(20)
    @DisplayName("findId - Successfully finds a country by ID")
    void testFindId_Success() {
        // Arrange
        when(countryRepository.findById(COUNTRY_ID)).thenReturn(Optional.of(country));

        // Act
        Country foundCountry = countryService.findId(COUNTRY_ID);

        // Assert
        assertNotNull(foundCountry, "Found country should not be null");
        assertEquals(COUNTRY_ID, foundCountry.getId(), "Country ID should match");
        assertEquals(COUNTRY_NAME, foundCountry.getName(), "Country name should match");
    }

    @Test
    @Order(30)
    @DisplayName("findAll - Successfully retrieves all countries sorted by ID")
    void testFindAll_Success() {
        // Arrange
        CountryProjection anotherCountry = new CountryProjection();
        anotherCountry.setId(2L);
        anotherCountry.setName("AnotherCountry");

        CountryProjection country = new CountryProjection();
        anotherCountry.setId(COUNTRY_ID);
        anotherCountry.setName(COUNTRY_NAME);

        List<CountryProjection> countries = Arrays.asList(country, anotherCountry);
        when(countryRepository.findAllProjections()).thenReturn(countries);

        // Act
        List<CountryProjection> foundCountries = countryService.findAll();

        // Assert
        assertNotNull(foundCountries, "Found countries list should not be null");
        assertEquals(2, foundCountries.size(), "Found countries list size should be 2");
        assertEquals(COUNTRY_ID, foundCountries.get(0).getId(), "First country ID should match");
        assertEquals("AnotherCountry", foundCountries.get(1).getName(),
                "Second country name should match");
    }

    @Test
    @Order(40)
    @DisplayName("updateCountry - Successfully updates a country from DTO")
    @Transactional
    void testUpdateCountry_Success() {
        // Arrange
        when(countryRepository.findById(COUNTRY_ID)).thenReturn(Optional.of(country));
        //doNothing().when(countryMapper).updateCountryFromDto(country, countryRequestDto);
        when(countryRepository.save(country)).thenReturn(country);

        // Act
        Country updatedCountry = countryService.updateCountry(countryRequestDto, COUNTRY_ID);

        // Assert
        assertNotNull(updatedCountry, "Updated country should not be null");
        assertEquals(COUNTRY_ID, updatedCountry.getId(), "Country ID should match");
        assertEquals(COUNTRY_NAME, updatedCountry.getName(), "Country name should match");

        verify(countryRepository, times(1)).save(country);
    }

    @Test
    @Order(50)
    @DisplayName("deleteById - Successfully deletes a country by ID")
    @Transactional
    void testDeleteById_Success() {
        // Arrange
        when(countryRepository.findById(COUNTRY_ID)).thenReturn(Optional.of(country));

        Town town = new Town();
        town.setCountry(country);
        country.setTowns(new HashSet<>(Arrays.asList(town)));

        Yacht yacht = new Yacht();
        yacht.setCountry(country);
        country.setYachts(new HashSet<>(Arrays.asList(yacht)));

        doNothing().when(countryRepository).delete(country);

        // Act
        countryService.deleteById(COUNTRY_ID);

        // Assert
        verify(countryRepository, times(1)).delete(country);
    }
}
