package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.service.FuelService;
import com.norwayyachtbrockers.service.KeelService;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@Order(260)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class YachtModelMapperTest {

    @MockBean
    private KeelService keelService;
    @MockBean
    private FuelService fuelService;

    @Autowired
    private YachtModelMapper yachtModelMapper;

    private YachtModel yachtModel;
    private YachtModelRequestDto dto;

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
        MockitoAnnotations.openMocks(this);
        yachtModel = new YachtModel();
        dto = new YachtModelRequestDto();
        dto.setId(YACHT_MODEL_ID);
        dto.setMake(YACHT_MODEL_MAKE);
        dto.setModel(YACHT_MODEL_MODEL);
        dto.setYear(YACHT_MODEL_YEAR);
        dto.setLengthOverall(YACHT_MODEL_LENGTH);
        dto.setBeamWidth(YACHT_MODEL_BEAM);
        dto.setDraftDepth(YACHT_MODEL_DRAFT);
        dto.setKeelTypeId(KEEL_TYPE_ID);
        dto.setFuelTypeId(FUEL_TYPE_ID);

        when(keelService.findId(eq(KEEL_TYPE_ID))).thenReturn(YACHT_MODEL_KEEL);
        when(fuelService.findId(eq(FUEL_TYPE_ID))).thenReturn(YACHT_MODEL_FUEL);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(keelService, fuelService);
    }

    @Test
    @DisplayName("Create YachtModel from DTO")
    @Order(10)
    void testCreateYachtModelFromDto() {
        // Act
        YachtModel createdYachtModel = yachtModelMapper.createYachtModelFromDto(dto);

        // Assert
        assertNotNull(createdYachtModel, "The created yachtModel should not be null");
        assertEquals(YACHT_MODEL_MAKE, createdYachtModel.getMake(),
                "The make should match the input DTO");

    }

    @Test
    @DisplayName("Update YachtModel from DTO")
    @Order(20)
    void testUpdateYachtModelFromDto() {
        // Act
        YachtModel updatedYachtModel = yachtModelMapper.updateYachtModelFromDto(yachtModel, dto);

        // Assert
        assertSame(yachtModel, updatedYachtModel,
                "The updated yacht should be the same instance as the input yacht");
    }
}