package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.service.CountryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Order(40)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

public class CountryControllerTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    private Country country;
    private CountryRequestDto countryRequestDto;

    private static final String BASE_URL = "/countries";
    private static final Long COUNTRY_ID = 1L;
    private static final String COUNTRY_NAME = "Norway";
    private static final String DTO_NAME = "Ireland";

    @BeforeEach
    void setUp() {
        country = new Country();
        country.setId(COUNTRY_ID);
        country.setName(COUNTRY_NAME);

        countryRequestDto = new CountryRequestDto();
        countryRequestDto.setName(DTO_NAME);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(countryService);
    }

    @Test
    @Order(10)
    @DisplayName("createCountry - Successfully creates a country")
    void testCreateCountry_Success() throws Exception {
        // Arrange
        when(countryService.saveCountry(any(CountryRequestDto.class))).thenReturn(country);

        // Act & assert
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(countryRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country_id").value(COUNTRY_ID))
                .andExpect(jsonPath("$.country_name").value(COUNTRY_NAME))
                .andReturn();

        verify(countryService, times(1)).saveCountry(any(CountryRequestDto.class));
    }

    @Test
    @Order(20)
    @DisplayName("getCountryById - Successfully retrieves a country by ID")
    void testGetCountryById_Success() throws Exception {
        // Arrange
        when(countryService.findId(anyLong())).thenReturn(country);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + COUNTRY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country_id").value(COUNTRY_ID))
                .andExpect(jsonPath("$.country_name").value(COUNTRY_NAME))
                .andReturn();

        verify(countryService, times(1)).findId(COUNTRY_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllCountries - Successfully retrieves all countries")
    void testGetAllCountries_Success() throws Exception {
        // Arrange
        Country anotherCountry = new Country();
        anotherCountry.setId(2L);
        anotherCountry.setName("AnotherCountry");

        List<Country> countries = Arrays.asList(country, anotherCountry);
        when(countryService.findAll()).thenReturn(countries);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].country_id").value(COUNTRY_ID))
                .andExpect(jsonPath("$[0].country_name").value(COUNTRY_NAME))
                .andExpect(jsonPath("$[1].country_id").value(2L))
                .andExpect(jsonPath("$[1].country_name").value("AnotherCountry"))
                .andReturn();

        verify(countryService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllCountries - Returns NO_CONTENT when no countries are found")
    void testGetAllCountries_NoContent() throws Exception {
        // Arrange
        when(countryService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(countryService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateCountry - Successfully updates a country")
    void testUpdateCountry_Success() throws Exception {
        // Arrange
        when(countryService.updateCountry(any(CountryRequestDto.class), eq(COUNTRY_ID))).thenReturn(country);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/" + COUNTRY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(countryRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country_id").value(COUNTRY_ID))
                .andExpect(jsonPath("$.country_name").value(COUNTRY_NAME))
                .andReturn();

        verify(countryService, times(1)).updateCountry(any(CountryRequestDto.class), eq(COUNTRY_ID));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes a country by ID")
    void testDeleteById_Success() throws Exception {
        // Arrange
        doNothing().when(countryService).deleteById(COUNTRY_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + COUNTRY_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(countryService, times(1)).deleteById(COUNTRY_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}