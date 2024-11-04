package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.repository.projections.KeelProjection;
import com.norwayyachtbrockers.service.KeelService;
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

@Order(70)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class KeelControllerTest {

    @MockBean
    private KeelService keelService;

    @Autowired
    private MockMvc mockMvc;

    private Keel keel;
    private KeelRequestDto keelRequestDto;

    private static final String BASE_URL = "/keels";
    private static final Long KEEL_ID = 1L;
    private static final String KEEL_NAME = "KeelName";
    private static final String DTO_NAME = "DtoKeelName";

    @BeforeEach
    void setUp() {
        keel = new Keel();
        keel.setId(KEEL_ID);
        keel.setName(KEEL_NAME);

        keelRequestDto = new KeelRequestDto();
        keelRequestDto.setName(DTO_NAME);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(keelService);
    }

    @Test
    @Order(10)
    @DisplayName("createKeel - Successfully creates a keel")
    void testCreateKeel_Success() throws Exception {
        // Arrange
        when(keelService.saveKeel(any(KeelRequestDto.class))).thenReturn(keel);

        // Act & assert
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(keelRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.keel_type_id").value(KEEL_ID))
                .andExpect(jsonPath("$.keel_type_name").value(KEEL_NAME))
                .andReturn();

        verify(keelService, times(1)).saveKeel(any(KeelRequestDto.class));
    }

    @Test
    @Order(20)
    @DisplayName("getKeelById - Successfully retrieves a keel by ID")
    void testGetKeelById_Success() throws Exception {
        // Arrange
        when(keelService.findId(KEEL_ID)).thenReturn(keel);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + KEEL_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keel_type_id").value(KEEL_ID))
                .andExpect(jsonPath("$.keel_type_name").value(KEEL_NAME))
                .andReturn();

        verify(keelService, times(1)).findId(KEEL_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllKeels - Successfully retrieves all keels")
    void testGetAllKeels_Success() throws Exception {
        // Arrange
        KeelProjection anotherKeel = new KeelProjection();
        anotherKeel.setId(2L);
        anotherKeel.setName("AnotherKeel");

        KeelProjection keel = new KeelProjection();
        keel.setId(KEEL_ID);
        keel.setName(KEEL_NAME);

        List<KeelProjection> keels = Arrays.asList(keel, anotherKeel);
        when(keelService.findAll()).thenReturn(keels);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].keel_type_id").value(KEEL_ID))
                .andExpect(jsonPath("$[0].keel_type_name").value(KEEL_NAME))
                .andExpect(jsonPath("$[1].keel_type_id").value(2L))
                .andExpect(jsonPath("$[1].keel_type_name").value("AnotherKeel"))
                .andReturn();

        verify(keelService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllKeels - Returns NO_CONTENT when no keels are found")
    void testGetAllKeels_NoContent() throws Exception {
        // Arrange
        when(keelService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(keelService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateKeel - Successfully updates a keel")
    void testUpdateKeel_Success() throws Exception {
        // Arrange
        when(keelService.updateKeel(any(KeelRequestDto.class), eq(KEEL_ID))).thenReturn(keel);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/" + KEEL_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(keelRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keel_type_id").value(KEEL_ID))
                .andExpect(jsonPath("$.keel_type_name").value(KEEL_NAME))
                .andReturn();

        verify(keelService, times(1)).updateKeel(any(KeelRequestDto.class), eq(KEEL_ID));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes a keel by ID")
    void testDeleteById_Success() throws Exception {
        // Arrange
        doNothing().when(keelService).deleteById(KEEL_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + KEEL_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(keelService, times(1)).deleteById(KEEL_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}