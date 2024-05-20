package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.service.TownService;
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
@Order(60)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TownControllerTest {

    @MockBean
    private TownService townService;

    @Autowired
    private MockMvc mockMvc;

    private TownResponseDto townResponseDto;
    private TownRequestDto townRequestDto;

    private static final String BASE_URL = "/towns";
    private static final Long TOWN_ID = 1L;
    private static final Long COUNTRY_ID = 1L;
    private static final String TOWN_NAME = "Oslo";
    private static final String COUNTRY_NAME = "Norway";
    private static final String DTO_NAME = "Norway";

    @BeforeEach
    void setUp() {
        townResponseDto = new TownResponseDto();
        townResponseDto.setTownId(TOWN_ID);
        townResponseDto.setTownName(TOWN_NAME);
        townResponseDto.setCountryName(COUNTRY_NAME);

        townRequestDto = new TownRequestDto();
        townRequestDto.setName(DTO_NAME);
        townRequestDto.setCountryId(COUNTRY_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(townService);
    }

    @Test
    @Order(10)
    @DisplayName("createTown - Successfully creates a town")
    void testCreateTown_Success() throws Exception {
        // Arrange
        when(townService.saveTown(any(TownRequestDto.class))).thenReturn(townResponseDto);

        // Act & assert
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(townRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.town_id").value(TOWN_ID))
                .andExpect(jsonPath("$.town_name").value(TOWN_NAME))
                .andExpect(jsonPath("$.town_country_name").value(COUNTRY_NAME))
                .andReturn();

        verify(townService, times(1)).saveTown(any(TownRequestDto.class));
    }

    @Test
    @Order(20)
    @DisplayName("getTownById - Successfully retrieves a town by ID")
    void testGetTownById_Success() throws Exception {
        // Arrange
        when(townService.findId(TOWN_ID)).thenReturn(townResponseDto);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + TOWN_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.town_id").value(TOWN_ID))
                .andExpect(jsonPath("$.town_name").value(TOWN_NAME))
                .andReturn();

        verify(townService, times(1)).findId(TOWN_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllTowns - Successfully retrieves all towns")
    void testGetAllTowns_Success() throws Exception {
        // Arrange
        TownResponseDto anotherTown = new TownResponseDto();
        anotherTown.setTownId(2L);
        anotherTown.setTownName("AnotherTown");

        List<TownResponseDto> towns = Arrays.asList(townResponseDto, anotherTown);
        when(townService.findAll()).thenReturn(towns);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].town_id").value(TOWN_ID))
                .andExpect(jsonPath("$[0].town_name").value(TOWN_NAME))
                .andExpect(jsonPath("$[1].town_id").value(2L))
                .andExpect(jsonPath("$[1].town_name").value("AnotherTown"))
                .andReturn();

        verify(townService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllTowns - Returns NO_CONTENT when no towns are found")
    void testGetAllTowns_NoContent() throws Exception {
        // Arrange
        when(townService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(townService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateTown - Successfully updates a town")
    void testUpdateTown_Success() throws Exception {
        // Arrange
        when(townService.updateTown(any(TownRequestDto.class), eq(TOWN_ID))).thenReturn(townResponseDto);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/" + TOWN_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(townRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.town_id").value(TOWN_ID))
                .andExpect(jsonPath("$.town_name").value(TOWN_NAME))
                .andReturn();

        verify(townService, times(1)).updateTown(any(TownRequestDto.class), eq(TOWN_ID));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes a town by ID")
    void testDeleteById_Success() throws Exception {
        // Arrange
        doNothing().when(townService).deleteById(TOWN_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + TOWN_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(townService, times(1)).deleteById(TOWN_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
