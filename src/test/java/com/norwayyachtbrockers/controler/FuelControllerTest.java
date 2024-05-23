package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.service.FuelService;
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

@Order(60)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FuelControllerTest {

    @MockBean
    private FuelService fuelService;

    @Autowired
    private MockMvc mockMvc;

    private Fuel fuel;
    private FuelRequestDto fuelRequestDto;

    private static final String BASE_URL = "/fuels";
    private static final Long FUEL_ID = 1L;
    private static final String FUEL_NAME = "FuelName";
    private static final String DTO_NAME = "DtoFuelName";

    @BeforeEach
    void setUp() {
        fuel = new Fuel();
        fuel.setId(FUEL_ID);
        fuel.setName(FUEL_NAME);

        fuelRequestDto = new FuelRequestDto();
        fuelRequestDto.setName(DTO_NAME);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(fuelService);
    }

    @Test
    @Order(10)
    @DisplayName("createFuel - Successfully creates a fuel")
    void testCreateFuel_Success() throws Exception {
        // Arrange
        when(fuelService.saveFuel(any(FuelRequestDto.class))).thenReturn(fuel);

        // Act & assert
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fuelRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fuel_type_id").value(FUEL_ID))
                .andExpect(jsonPath("$.fuel_type_name").value(FUEL_NAME))
                .andReturn();

        verify(fuelService, times(1)).saveFuel(any(FuelRequestDto.class));
    }

    @Test
    @Order(20)
    @DisplayName("getFuelById - Successfully retrieves a fuel by ID")
    void testGetFuelById_Success() throws Exception {
        // Arrange
        when(fuelService.findId(FUEL_ID)).thenReturn(fuel);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + FUEL_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fuel_type_id").value(FUEL_ID))
                .andExpect(jsonPath("$.fuel_type_name").value(FUEL_NAME))
                .andReturn();

        verify(fuelService, times(1)).findId(FUEL_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllFuels - Successfully retrieves all fuels")
    void testGetAllFuels_Success() throws Exception {
        // Arrange
        Fuel anotherFuel = new Fuel();
        anotherFuel.setId(2L);
        anotherFuel.setName("AnotherFuel");

        List<Fuel> fuels = Arrays.asList(fuel, anotherFuel);
        when(fuelService.findAll()).thenReturn(fuels);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fuel_type_id").value(FUEL_ID))
                .andExpect(jsonPath("$[0].fuel_type_name").value(FUEL_NAME))
                .andExpect(jsonPath("$[1].fuel_type_id").value(2L))
                .andExpect(jsonPath("$[1].fuel_type_name").value("AnotherFuel"))
                .andReturn();

        verify(fuelService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllFuels - Returns NO_CONTENT when no fuels are found")
    void testGetAllFuels_NoContent() throws Exception {
        // Arrange
        when(fuelService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(fuelService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateFuel - Successfully updates a fuel")
    void testUpdateFuel_Success() throws Exception {
        // Arrange
        when(fuelService.updateFuel(any(FuelRequestDto.class), eq(FUEL_ID))).thenReturn(fuel);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/" + FUEL_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fuelRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fuel_type_id").value(FUEL_ID))
                .andExpect(jsonPath("$.fuel_type_name").value(FUEL_NAME))
                .andReturn();

        verify(fuelService, times(1)).updateFuel(any(FuelRequestDto.class), eq(FUEL_ID));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes a fuel by ID")
    void testDeleteById_Success() throws Exception {
        // Arrange
        doNothing().when(fuelService).deleteById(FUEL_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + FUEL_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(fuelService, times(1)).deleteById(FUEL_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
