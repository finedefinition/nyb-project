package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.service.YachtDetailService;
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

@Order(130)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtDetailControllerTest {

    @MockBean
    private YachtDetailService yachtDetailService;

    @Autowired
    private MockMvc mockMvc;

    private YachtDetailRequestDto yachtDetailRequestDto;
    private YachtDetail yachtDetail;

    private static final String BASE_URL = "/yachtDetails";
    private static final Long YACHT_ID = 1L;
    private static final String YACHT_DESCRIPTION = "Luxury Yacht";

    @BeforeEach
    void setUp() {
        yachtDetail = new YachtDetail();
        yachtDetail.setId(YACHT_ID);
        yachtDetail.setDescription(YACHT_DESCRIPTION);

        yachtDetailRequestDto = new YachtDetailRequestDto();
        yachtDetailRequestDto.setId(YACHT_ID);
        yachtDetailRequestDto.setDescription(YACHT_DESCRIPTION);

    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtDetailService);
    }

    @Test
    @Order(10)
    @DisplayName("createYacht - Successfully creates a yacht detail")
    void testCreateYacht_Success() throws Exception {
        // Arrange
        when(yachtDetailService.save(any(YachtDetailRequestDto.class))).thenReturn(yachtDetail);

        // Act & assert
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(yachtDetailRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.yacht_detail_id").value(YACHT_ID))
                .andExpect(jsonPath("$.description").value(YACHT_DESCRIPTION))
                .andReturn();

        verify(yachtDetailService, times(1)).save(any(YachtDetailRequestDto.class));
    }

    @Test
    @Order(20)
    @DisplayName("getYachtById - Successfully retrieves a yacht detail by ID")
    void testGetYachtById_Success() throws Exception {
        // Arrange
        when(yachtDetailService.findId(YACHT_ID)).thenReturn(yachtDetail);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + YACHT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yacht_detail_id").value(YACHT_ID))
                .andExpect(jsonPath("$.description").value(YACHT_DESCRIPTION))
                .andReturn();

        verify(yachtDetailService, times(1)).findId(YACHT_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllYachts - Successfully retrieves all yacht details")
    void testGetAllYachts_Success() throws Exception {
        // Arrange
        YachtDetail anotherYacht = new YachtDetail();
        anotherYacht.setId(2L);
        anotherYacht.setDescription("Another Yacht");

        List<YachtDetail> yachts = Arrays.asList(yachtDetail, anotherYacht);
        when(yachtDetailService.findAll()).thenReturn(yachts);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].yacht_detail_id").value(YACHT_ID))
                .andExpect(jsonPath("$[0].description").value(YACHT_DESCRIPTION))
                .andExpect(jsonPath("$[1].yacht_detail_id").value(2L))
                .andExpect(jsonPath("$[1].description").value("Another Yacht"))
                .andReturn();

        verify(yachtDetailService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllYachts - Returns NO_CONTENT when no yacht details are found")
    void testGetAllYachts_NoContent() throws Exception {
        // Arrange
        when(yachtDetailService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(yachtDetailService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateYacht - Successfully updates a yacht detail")
    void testUpdateYacht_Success() throws Exception {
        // Arrange
        when(yachtDetailService.update(any(YachtDetailRequestDto.class), eq(YACHT_ID))).thenReturn(yachtDetail);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/" + YACHT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(yachtDetailRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yacht_detail_id").value(YACHT_ID))
                .andExpect(jsonPath("$.description").value(YACHT_DESCRIPTION))
                .andReturn();

        verify(yachtDetailService, times(1))
                .update(any(YachtDetailRequestDto.class), eq(YACHT_ID));
    }

    @Test
    @Order(6)
    @DisplayName("deleteYachtById - Successfully deletes a yacht detail by ID")
    void testDeleteYachtById_Success() throws Exception {
        // Arrange
        doNothing().when(yachtDetailService).deleteById(YACHT_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + YACHT_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(yachtDetailService, times(1)).deleteById(YACHT_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
