package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.service.CountryService;
import com.norwayyachtbrockers.service.TownService;
import com.norwayyachtbrockers.service.YachtDetailService;
import com.norwayyachtbrockers.service.YachtModelService;
import com.norwayyachtbrockers.service.OwnerInfoService;
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
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Order(250)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtMapperTest {

    @MockBean
    private YachtModelService yachtModelService;
    @MockBean
    private TownService townService;
    @MockBean
    private CountryService countryService;
    @MockBean
    private YachtDetailService yachtDetailService;
    @MockBean
    private OwnerInfoService ownerInfoService;

    @Autowired
    private YachtMapper yachtMapper;

    private Yacht yacht;
    private YachtRequestDto dto;
    private YachtModel yachtModel;
    private Country country;
    private Town town;
    private YachtDetail yachtDetail;
    private OwnerInfo ownerInfo;

    private static final boolean IS_FEATURED = true;
    private static final boolean VAT_INCLUDED = true;
    private static final BigDecimal PRICE = new BigDecimal(150000);
    private static final BigDecimal PRICE_OLD = new BigDecimal(200000);
    private static final Long YACHT_MODEL_ID = 1L;
    private static final String YACHT_MODEL_MAKE = "Beneteau";
    private static final String YACHT_MODEL_MODEL = "First 47.7";
    private static final Integer YACHT_MODEL_YEAR = 2007;
    private static final BigDecimal YACHT_MODEL_LENGTH = new BigDecimal(47.00);
    private static final BigDecimal YACHT_MODEL_BEAM = new BigDecimal(14.75);
    private static final BigDecimal YACHT_MODEL_DRAFT = new BigDecimal(7.58);
    private static final Keel YACHT_MODEL_KEEL = new Keel("Bulb Keel");
    private static final Fuel YACHT_MODEL_FUEL = new Fuel("Diesel");
    private static final Long COUNTRY_ID = 1L;
    private static final String COUNTRY_NAME = "Turkey";
    private static final Long TOWN_ID = 1L;
    private static final String TOWN_NAME = "Istanbul";
    private static final Long YACHT_DETAIL_ID = 1L;
    private static final Long OWNER_INFO_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        yacht = new Yacht();
        dto = new YachtRequestDto();
        dto.setVatIncluded(VAT_INCLUDED);
        dto.setPrice(PRICE);
        dto.setYachtModelId(YACHT_MODEL_ID);
        dto.setCountryId(COUNTRY_ID);
        dto.setTownId(TOWN_ID);
        dto.setYachtDetailId(YACHT_DETAIL_ID);
        dto.setOwnerInfoId(OWNER_INFO_ID);

        yachtModel = new YachtModel();
        when(yachtModelService.findId(eq(YACHT_MODEL_ID))).thenReturn(yachtModel);
        country = new Country();
        when(countryService.findId(eq(COUNTRY_ID))).thenReturn(country);
        town = new Town();
        when(townService.findTownById(eq(TOWN_ID))).thenReturn(town);
        yachtDetail = new YachtDetail();
        when(yachtDetailService.findId(eq(YACHT_DETAIL_ID))).thenReturn(yachtDetail);
        ownerInfo = new OwnerInfo();
        when(ownerInfoService.findId(eq(OWNER_INFO_ID))).thenReturn(ownerInfo);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtModelService, townService, countryService, yachtDetailService, ownerInfoService);
    }

    @Test
    @DisplayName("Create Yacht from DTO")
    @Order(10)
    void testCreateYachtFromDto() {
        // Act
        Yacht createdYacht = yachtMapper.createYachtFromDto(dto);

        // Assert
        assertNotNull(createdYacht, "The created yacht should not be null");
        assertTrue(createdYacht.isVatIncluded(), "The yacht should have VAT included");
        assertEquals(0, createdYacht.getPrice().compareTo(PRICE),
                "The price should match the input DTO");

    }

//    @Test
//    @DisplayName("Update Yacht from DTO")
//    @Order(20)
//    void testUpdateYachtFromDto() {
//        // Arrange (done in setUp)
//
//        // Act
//        Yacht updatedYacht = yachtMapper.updateYachtFromDto(yacht, dto);
//
//        // Assert
//        assertSame(yacht, updatedYacht, "The updated yacht should be the same instance as the input yacht");
//        assertTrue(updatedYacht.isVatIncluded(), "The yacht should have VAT included after update");
//    }

    @Test
    @DisplayName("Convert Yacht to DTO")
    @Order(30)
    void testConvertToDto() {
        // Arrange
        yachtModel.setId(YACHT_MODEL_ID);
        yachtModel.setMake(YACHT_MODEL_MAKE);
        yachtModel.setModel(YACHT_MODEL_MODEL);
        yachtModel.setYear(YACHT_MODEL_YEAR);
        yachtModel.setLengthOverall(YACHT_MODEL_LENGTH);
        yachtModel.setBeamWidth(YACHT_MODEL_BEAM);
        yachtModel.setDraftDepth(YACHT_MODEL_DRAFT);
        yachtModel.setKeelType(YACHT_MODEL_KEEL);
        yachtModel.setFuelType(YACHT_MODEL_FUEL);

        country.setId(COUNTRY_ID);
        country.setName(COUNTRY_NAME);

        town.setId(TOWN_ID);
        town.setName(TOWN_NAME);
        town.setCountry(country);

        yacht.setFeatured(IS_FEATURED);
        yacht.setVatIncluded(VAT_INCLUDED);
        yacht.setPrice(PRICE);
        yacht.setPriceOld(PRICE_OLD);
        yacht.setMainImageKey("main-key");
        yacht.setYachtModel(yachtModel);
        yacht.setCountry(country);
        yacht.setTown(town);
        yacht.setYachtDetail(yachtDetail);
        yacht.setOwnerInfo(ownerInfo);
        yacht.setFavouritedByUsers(new HashSet<User>());
        yacht.setYachtImages(new HashSet<YachtImage>());

        // Act
        YachtResponseDto yachtDto = yachtMapper.convertToDto(yacht);

        // Assert
        assertNotNull(yachtDto, "The converted DTO should not be null");
        assertEquals(yacht.getId(), yachtDto.getId(), "DTO ID should match yacht ID");
        assertTrue(yachtDto.isVatIncluded(), "DTO should have VAT included");
        assertEquals(PRICE, new BigDecimal(yachtDto.getPrice()), "DTO price should match yacht price");
        assertEquals(PRICE_OLD, new BigDecimal(yachtDto.getPriceOld()), "DTO old price should match yacht old price");
        assertTrue(yachtDto.isHotPrice(), "DTO should have hot price flag set");
    }
}