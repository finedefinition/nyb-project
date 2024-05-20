package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.service.YachtModelService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtModelControllerTest {

    @MockBean
    private YachtModelService yachtModelService;

    @Autowired
    private MockMvc mockMvc;

    private YachtModel yachtModel;
    private YachtModelRequestDto yachtModelRequestDto;

    private static final String BASE_URL = "/yachtModels";
    private static final Long YACHT_MODEL_ID = 1L;
    private static final String YACHT_MODEL_MAKE = "Beneteau";
    private static final String YACHT_MODEL_MODEL = "First 47.7";
    private static final Integer YACHT_MODEL_YEAR = 2007;
    private static final BigDecimal YACHT_MODEL_LENGTH = new BigDecimal(47.00);
    private static final BigDecimal YACHT_MODEL_BEAM = new BigDecimal(14.75);
    private static final BigDecimal YACHT_MODEL_DRAFT = new BigDecimal(7.58);
    private static final Long KEEL_TYPE_ID = 1L;
    private static final Keel YACHT_MODEL_KEEL = new Keel("Bulb Keel");
    private static final Long FUEL_TYPE_ID = 1L;
    private static final Fuel YACHT_MODEL_FUEL = new Fuel("Diesel");

    @BeforeEach
    void setUp() {
        yachtModel = new YachtModel();
        yachtModel.setId(YACHT_MODEL_ID);
        yachtModel.setMake(YACHT_MODEL_MAKE);
        yachtModel.setModel(YACHT_MODEL_MODEL);
        yachtModel.setYear(YACHT_MODEL_YEAR);
        yachtModel.setDraftDepth(YACHT_MODEL_DRAFT);
        yachtModel.setLengthOverall(YACHT_MODEL_LENGTH);
        yachtModel.setBeamWidth(YACHT_MODEL_BEAM);
        yachtModel.setFuelType(YACHT_MODEL_FUEL);
        yachtModel.setKeelType(YACHT_MODEL_KEEL);

        yachtModelRequestDto = new YachtModelRequestDto();
        yachtModelRequestDto.setMake(YACHT_MODEL_MAKE);
        yachtModelRequestDto.setModel(YACHT_MODEL_MODEL);
        yachtModelRequestDto.setYear(YACHT_MODEL_YEAR);
        yachtModelRequestDto.setDraftDepth(YACHT_MODEL_DRAFT);
        yachtModelRequestDto.setLengthOverall(YACHT_MODEL_LENGTH);
        yachtModelRequestDto.setBeamWidth(YACHT_MODEL_BEAM);
        yachtModelRequestDto.setFuelTypeId(FUEL_TYPE_ID);
        yachtModelRequestDto.setKeelTypeId(KEEL_TYPE_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtModelService);
    }

    @Test
    @Order(10)
    @DisplayName("createYachtModel - Successfully creates a yacht model")
    void testCreateYachtModel_Success() throws Exception {
        // Arrange
        when(yachtModelService.saveYachtModel(any(YachtModelRequestDto.class))).thenReturn(yachtModel);

        // Act & assert
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(yachtModelRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.yacht_model_id").value(YACHT_MODEL_ID))
                .andExpect(jsonPath("$.make").value(YACHT_MODEL_MAKE))
                .andExpect(jsonPath("$.model").value(YACHT_MODEL_MODEL))
                .andReturn();

        verify(yachtModelService, times(1)).saveYachtModel(any(YachtModelRequestDto.class));
    }

    @Test
    @Order(20)
    @DisplayName("getYachtModelById - Successfully retrieves a yacht model by ID")
    void testGetYachtModelById_Success() throws Exception {
        // Arrange
        when(yachtModelService.findId(YACHT_MODEL_ID)).thenReturn(yachtModel);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + YACHT_MODEL_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yacht_model_id").value(YACHT_MODEL_ID))
                .andExpect(jsonPath("$.make").value(YACHT_MODEL_MAKE))
                .andExpect(jsonPath("$.model").value(YACHT_MODEL_MODEL))
                .andReturn();

        verify(yachtModelService, times(1)).findId(YACHT_MODEL_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllYachtModels - Successfully retrieves all yacht models")
    void testGetAllYachtModels_Success() throws Exception {
        // Arrange
        YachtModel anotherYachtModel = new YachtModel();
        anotherYachtModel.setId(2L);
        anotherYachtModel.setModel("Another Model");
        anotherYachtModel.setMake("Another Make");

        List<YachtModel> yachtModels = Arrays.asList(yachtModel, anotherYachtModel);
        when(yachtModelService.findAll()).thenReturn(yachtModels);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].yacht_model_id").value(YACHT_MODEL_ID))
                .andExpect(jsonPath("$[0].model").value(YACHT_MODEL_MODEL))
                .andExpect(jsonPath("$[0].make").value(YACHT_MODEL_MAKE))
                .andExpect(jsonPath("$[1].yacht_model_id").value(2L))
                .andExpect(jsonPath("$[1].model").value("Another Model"))
                .andExpect(jsonPath("$[1].make").value("Another Make"))
                .andReturn();

        verify(yachtModelService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllYachtModels - Returns NO_CONTENT when no yacht models are found")
    void testGetAllYachtModels_NoContent() throws Exception {
        // Arrange
        when(yachtModelService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(yachtModelService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateYachtModel - Successfully updates a yacht model")
    void testUpdateYachtModel_Success() throws Exception {
        // Arrange
        when(yachtModelService.updateYachtModel(any(YachtModelRequestDto.class), eq(YACHT_MODEL_ID))).thenReturn(yachtModel);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/" + YACHT_MODEL_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(yachtModelRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yacht_model_id").value(YACHT_MODEL_ID))
                .andExpect(jsonPath("$.make").value(YACHT_MODEL_MAKE))
                .andExpect(jsonPath("$.model").value(YACHT_MODEL_MODEL))
                .andReturn();

        verify(yachtModelService, times(1)).updateYachtModel(any(YachtModelRequestDto.class), eq(YACHT_MODEL_ID));
    }

    @Test
    @Order(60)
    @DisplayName("deleteYachtModelById - Successfully deletes a yacht model by ID")
    void testDeleteYachtModelById_Success() throws Exception {
        // Arrange
        doNothing().when(yachtModelService).deleteById(YACHT_MODEL_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + YACHT_MODEL_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(yachtModelService, times(1)).deleteById(YACHT_MODEL_ID);
    }

    @Test
    @Order(70)
    @DisplayName("getYachtModelByKeelTypeId - Successfully retrieves yacht models by keel type ID")
    void testGetYachtModelByKeelTypeId_Success() throws Exception {
        // Arrange
        List<YachtModel> yachtModels = Arrays.asList(yachtModel);
        when(yachtModelService.findByKeelType_Id(1L)).thenReturn(yachtModels);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/byKeelType/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].yacht_model_id").value(YACHT_MODEL_ID))
                .andExpect(jsonPath("$[0].make").value(YACHT_MODEL_MAKE))
                .andReturn();

        verify(yachtModelService, times(1)).findByKeelType_Id(1L);
    }

    @Test
    @Order(80)
    @DisplayName("getYachtModelByFuelTypeId - Successfully retrieves yacht models by fuel type ID")
    void testGetYachtModelByFuelTypeId_Success() throws Exception {
        // Arrange
        List<YachtModel> yachtModels = Arrays.asList(yachtModel);
        when(yachtModelService.findByFuelType_Id(1L)).thenReturn(yachtModels);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/byFuelType/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].yacht_model_id").value(YACHT_MODEL_ID))
                .andExpect(jsonPath("$[0].make").value(YACHT_MODEL_MAKE))
                .andReturn();

        verify(yachtModelService, times(1)).findByFuelType_Id(1L);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}