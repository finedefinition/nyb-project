package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Order(230)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtDetailMapperTest {
    private YachtDetail yachtDetail;
    private YachtDetailRequestDto dto;

    @BeforeEach
    void setUp() {
        yachtDetail = new YachtDetail();
        dto = new YachtDetailRequestDto();
        dto.setId(1L);
        dto.setCabin(3);
        dto.setBerth(5);
        dto.setHeads(2);
        dto.setShower(2);
        dto.setDescription("Luxury yacht with state-of-the-art facilities.");
    }

    @Test
    @DisplayName("Create YachtDetail from valid DTO")
    @Order(20)
    void testCreateYachtDetailFromDto_ValidDto() {
        YachtDetail createdYachtDetail = YachtDetailMapper.createYachtDetailFromDto(dto);
        assertAll(
                () -> assertNotNull(createdYachtDetail, "YachtDetail should not be null"),
                () -> assertEquals(dto.getCabin(), createdYachtDetail.getCabin(), "Cabin count should match"),
                () -> assertEquals(dto.getBerth(), createdYachtDetail.getBerth(), "Berth count should match"),
                () -> assertEquals(dto.getHeads(), createdYachtDetail.getHeads(), "Head count should match"),
                () -> assertEquals(dto.getShower(), createdYachtDetail.getShower(), "Shower count should match"),
                () -> assertEquals(dto.getDescription(), createdYachtDetail.getDescription(), "Descriptions should match")
        );
    }

    @Test
    @DisplayName("Update YachtDetail from valid DTO")
    @Order(30)
    void testUpdateYachtDetailFromDto_ValidDto() {
        YachtDetail updatedYachtDetail = YachtDetailMapper.updateYachtDetailFromDto(yachtDetail, dto);
        assertAll(
                () -> assertNotNull(updatedYachtDetail, "Updated YachtDetail should not be null"),
                () -> assertEquals(dto.getCabin(), updatedYachtDetail.getCabin(), "Cabin count should match after update"),
                () -> assertEquals(dto.getBerth(), updatedYachtDetail.getBerth(), "Berth count should match after update"),
                () -> assertEquals(dto.getHeads(), updatedYachtDetail.getHeads(), "Head count should match after update"),
                () -> assertEquals(dto.getShower(), updatedYachtDetail.getShower(), "Shower count should match after update"),
                () -> assertEquals(dto.getDescription(), updatedYachtDetail.getDescription(), "Descriptions should match after update")
        );
    }
}
