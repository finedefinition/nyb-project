package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.service.YachtService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Order(120)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtControllerTest {

    @MockBean
    private YachtService yachtService;

    @Autowired
    private MockMvc mockMvc;

    private YachtResponseDto yachtResponseDto;
    private YachtRequestDto yachtRequestDto;

    private static final String BASE_URL = "/yachts";
    private static final Long YACHT_ID = 1L;
    private static final boolean IS_FEATURED = true;
    private static final boolean VAT_INCLUDED = true;
    private static final BigDecimal PRICE = new BigDecimal("150000");
    private static final BigDecimal PRICE_ANOTHER_YACHT = new BigDecimal("200000");
    private static final Long YACHT_MODEL_ID = 1L;
    private static final Long COUNTRY_ID = 1L;
    private static final Long TOWN_ID = 1L;
    private static final Long YACHT_DETAIL_ID = 1L;
    private static final Long OWNER_INFO_ID = 1L;

    @BeforeEach
    void setUp() {
        yachtResponseDto = new YachtResponseDto();
        yachtResponseDto.setId(YACHT_ID);
        yachtResponseDto.setFeatured(IS_FEATURED);
        yachtResponseDto.setPrice("150000");


        yachtRequestDto = new YachtRequestDto();
        yachtRequestDto.setFeatured(IS_FEATURED);
        yachtRequestDto.setVatIncluded(VAT_INCLUDED);
        yachtRequestDto.setPrice(PRICE);
        yachtRequestDto.setYachtModelId(YACHT_MODEL_ID);
        yachtRequestDto.setCountryId(COUNTRY_ID);
        yachtRequestDto.setTownId(TOWN_ID);
        yachtRequestDto.setYachtDetailId(YACHT_DETAIL_ID);
        yachtRequestDto.setOwnerInfoId(OWNER_INFO_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtService);
    }

    @Test
    @Order(10)
    @DisplayName("createYacht - Successfully creates a yacht")
    @WithMockUser(roles = "ADMIN")
    void testCreateYacht_Success() throws Exception {
        // Arrange
        when(yachtService.save(any(YachtRequestDto.class), any(MultipartFile.class), any(List.class)))
                .thenReturn(yachtResponseDto);

        MockMultipartFile mainImageFile =
                new MockMultipartFile("mainImage", "main.jpg",
                        "image/jpeg", "main image".getBytes());
        MockMultipartFile additionalImageFile =
                new MockMultipartFile("additionalImages", "additional.jpg",
                        "image/jpeg", "additional image".getBytes());
        MockMultipartFile yachtData =
                new MockMultipartFile("yachtData", "",
                        "application/json", asJsonString(yachtRequestDto).getBytes());

        // Act & assert
        mockMvc.perform(multipart(BASE_URL)
                        .file(mainImageFile)
                        .file(additionalImageFile)
                        .file(yachtData)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.yacht_id").value(YACHT_ID))
                .andExpect(jsonPath("$.yacht_price").value(PRICE))
                .andReturn();

        verify(yachtService, times(1)).save(any(YachtRequestDto.class),
                any(MultipartFile.class), any(List.class));
    }

    @Test
    @Order(20)
    @DisplayName("getYachtById - Successfully retrieves a yacht by ID")
    void testGetYachtById_Success() throws Exception {
        // Arrange
        when(yachtService.findId(YACHT_ID)).thenReturn(yachtResponseDto);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + YACHT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yacht_id").value(YACHT_ID))
                .andExpect(jsonPath("$.yacht_price").value(PRICE))
                .andReturn();

        verify(yachtService, times(1)).findId(YACHT_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllYachts - Successfully retrieves all yachts")
    void testGetAllYachts_Success() throws Exception {
        // Arrange
        YachtResponseDto anotherYacht = new YachtResponseDto();
        anotherYacht.setId(2L);
        anotherYacht.setPrice("200000");


        List<YachtResponseDto> yachts = Arrays.asList(yachtResponseDto, anotherYacht);
        when(yachtService.findAll()).thenReturn(yachts);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].yacht_id").value(YACHT_ID))
                .andExpect(jsonPath("$[0].yacht_price").value(PRICE))
                .andExpect(jsonPath("$[1].yacht_id").value(2L))
                .andExpect(jsonPath("$[1].yacht_price").value(PRICE_ANOTHER_YACHT))
                .andReturn();

        verify(yachtService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllYachts - Returns NO_CONTENT when no yachts are found")
    void testGetAllYachts_NoContent() throws Exception {
        // Arrange
        when(yachtService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(yachtService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateYacht - Successfully updates a yacht")
    @WithMockUser(roles = "ADMIN")
    void testUpdateYacht_Success() throws Exception {
        // Arrange
        when(yachtService.update(any(YachtRequestDto.class), eq(YACHT_ID), any(MockMultipartFile.class), any(List.class)))
                .thenReturn(yachtResponseDto);

        MockMultipartFile mainImageFile = new MockMultipartFile("mainImage", "main.jpg", "image/jpeg", "main image".getBytes());
        MockMultipartFile additionalImageFile = new MockMultipartFile("additionalImages", "additional.jpg", "image/jpeg", "additional image".getBytes());
        MockMultipartFile yachtData = new MockMultipartFile("yachtData", "", "application/json", asJsonString(yachtRequestDto).getBytes());

        // Act & Assert
        mockMvc.perform(multipart(BASE_URL + "/" + YACHT_ID)
                        .file(mainImageFile)
                        .file(additionalImageFile)
                        .file(yachtData)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yacht_id").value(YACHT_ID))
                .andExpect(jsonPath("$.yacht_price").value(PRICE))
                .andReturn();

        verify(yachtService, times(1)).update(any(YachtRequestDto.class), eq(YACHT_ID), any(MockMultipartFile.class), any(List.class));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes a yacht by ID")
    @WithMockUser(roles = "ADMIN")
    void testDeleteById_Success() throws Exception {
        // Arrange
        doNothing().when(yachtService).deleteById(YACHT_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + YACHT_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(yachtService, times(1)).deleteById(YACHT_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}