package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.repository.projections.OwnerInfoProjection;
import com.norwayyachtbrockers.service.OwnerInfoService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Order(80)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OwnerInfoControllerTest {

    @MockBean
    private OwnerInfoService ownerInfoService;

    @Autowired
    private MockMvc mockMvc;

    private OwnerInfo ownerInfo;
    private OwnerInfoRequestDto ownerInfoRequestDto;

    private static final String BASE_URL = "/ownerInfos";
    private static final Long OWNER_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String UPDATED_FIRST_NAME = "Ringo";
    private static final String LAST_NAME = "Lennon";
    private static final String UPDATED_LAST_NAME = "Starr";
    private static final String PHONE_NUMBER = "1234567";
    private static final String UPDATED_PHONE_NUMBER = "7654321";
    private static final String EMAIL = "john.lennon@gmail.com";
    private static final String UPDATED_EMAIL = "ringo.starr@gmail.com";

    @BeforeEach
    void setUp() {
        ownerInfo = new OwnerInfo();
        ownerInfo.setId(OWNER_ID);
        ownerInfo.setFirstName(FIRST_NAME);
        ownerInfo.setLastName(LAST_NAME);
        ownerInfo.setTelephone(PHONE_NUMBER);
        ownerInfo.setEmail(EMAIL);


        ownerInfoRequestDto = new OwnerInfoRequestDto();
        ownerInfoRequestDto.setFirstName(FIRST_NAME);
        ownerInfoRequestDto.setLastName(LAST_NAME);
        ownerInfoRequestDto.setPhoneNumber(PHONE_NUMBER);
        ownerInfoRequestDto.setEmail(EMAIL);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(ownerInfoService);
    }

    @Test
    @Order(10)
    @DisplayName("createOwnerInfo - Successfully creates an owner info")
    void testCreateOwnerInfo_Success() throws Exception {
        // Arrange
        when(ownerInfoService.save(any(OwnerInfoRequestDto.class))).thenReturn(ownerInfo);

        // Act & assert
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ownerInfoRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.owner_info_id").value(OWNER_ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.telephone").value(PHONE_NUMBER))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andReturn();

        verify(ownerInfoService, times(1)).save(any(OwnerInfoRequestDto.class));
    }

    @Test
    @Order(20)
    @DisplayName("getOwnerInfoById - Successfully retrieves an owner info by ID")
    void testGetOwnerInfoById_Success() throws Exception {
        // Arrange
        when(ownerInfoService.findId(OWNER_ID)).thenReturn(ownerInfo);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner_info_id").value(OWNER_ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andReturn();

        verify(ownerInfoService, times(1)).findId(OWNER_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllOwnerInfos - Successfully retrieves all owner infos")
    void testGetAllOwnerInfos_Success() throws Exception {
        // Arrange
        OwnerInfoProjection anotherOwnerInfo = new OwnerInfoProjection();
        anotherOwnerInfo.setId(2L);
        anotherOwnerInfo.setFirstName("AnotherOwner");
        anotherOwnerInfo.setLastName(LAST_NAME);
        anotherOwnerInfo.setTelephone(PHONE_NUMBER);
        anotherOwnerInfo.setEmail(EMAIL);

        OwnerInfoProjection ownerInfo = new OwnerInfoProjection();
        ownerInfo.setId(OWNER_ID);
        ownerInfo.setFirstName(FIRST_NAME);
        ownerInfo.setLastName(LAST_NAME);
        ownerInfo.setTelephone(PHONE_NUMBER);
        ownerInfo.setEmail(EMAIL);

        List<OwnerInfoProjection> ownerInfos = Arrays.asList(ownerInfo, anotherOwnerInfo);
        when(ownerInfoService.findAll()).thenReturn(ownerInfos);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].owner_info_id").value(OWNER_ID))
                .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$[1].owner_info_id").value(2L))
                .andExpect(jsonPath("$[1].firstName").value("AnotherOwner"))
                .andReturn();

        verify(ownerInfoService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllOwnerInfos - Returns NO_CONTENT when no owner infos are found")
    void testGetAllOwnerInfos_NoContent() throws Exception {
        // Arrange
        when(ownerInfoService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(ownerInfoService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateOwnerInfo - Successfully updates an owner info")
    void testUpdateOwnerInfo_Success() throws Exception {
        // Arrange
        OwnerInfo updatedOwnerInfo = new OwnerInfo();
        updatedOwnerInfo.setId(OWNER_ID);
        updatedOwnerInfo.setFirstName(UPDATED_FIRST_NAME);
        updatedOwnerInfo.setLastName(UPDATED_LAST_NAME);
        updatedOwnerInfo.setTelephone(UPDATED_PHONE_NUMBER);
        updatedOwnerInfo.setEmail(UPDATED_EMAIL);

        when(ownerInfoService.update(any(OwnerInfoRequestDto.class), eq(OWNER_ID))).thenReturn(updatedOwnerInfo);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/" + OWNER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ownerInfoRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner_info_id").value(OWNER_ID))
                .andExpect(jsonPath("$.firstName").value(UPDATED_FIRST_NAME))
                .andReturn();

        verify(ownerInfoService, times(1)).update(any(OwnerInfoRequestDto.class), eq(OWNER_ID));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes an owner info by ID")
    void testDeleteById_Success() throws Exception {
        // Arrange
        doNothing().when(ownerInfoService).deleteById(OWNER_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + OWNER_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(ownerInfoService, times(1)).deleteById(OWNER_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
