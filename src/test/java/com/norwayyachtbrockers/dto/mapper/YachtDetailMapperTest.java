package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Order(80)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class YachtDetailMapperTest {

    @Autowired
    private YachtDetailMapper yachtDetailMapper;

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
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(10)
    void testCreateYachtDetailFromDto_NullDto() {
        assertThrows(IllegalArgumentException.class, () -> yachtDetailMapper.createYachtDetailFromDto(null),
                "Should throw IllegalArgumentException for null DTO");
    }

    @Test
    @DisplayName("Create YachtDetail from valid DTO")
    @Order(20)
    void testCreateYachtDetailFromDto_ValidDto() {
        YachtDetail createdYachtDetail = yachtDetailMapper.createYachtDetailFromDto(dto);
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
        YachtDetail updatedYachtDetail = yachtDetailMapper.updateYachtDetailFromDto(yachtDetail, dto);
        assertAll(
                () -> assertNotNull(updatedYachtDetail, "Updated YachtDetail should not be null"),
                () -> assertEquals(dto.getCabin(), updatedYachtDetail.getCabin(), "Cabin count should match after update"),
                () -> assertEquals(dto.getBerth(), updatedYachtDetail.getBerth(), "Berth count should match after update"),
                () -> assertEquals(dto.getHeads(), updatedYachtDetail.getHeads(), "Head count should match after update"),
                () -> assertEquals(dto.getShower(), updatedYachtDetail.getShower(), "Shower count should match after update"),
                () -> assertEquals(dto.getDescription(), updatedYachtDetail.getDescription(), "Descriptions should match after update")
        );
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO or YachtDetail on update")
    @Order(40)
    void testUpdateYachtDetailFromDto_NullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> yachtDetailMapper.updateYachtDetailFromDto(null, dto),
                "Should throw IllegalArgumentException when YachtDetail is null");
        assertThrows(IllegalArgumentException.class,
                () -> yachtDetailMapper.updateYachtDetailFromDto(yachtDetail, null),
                "Should throw IllegalArgumentException when DTO is null");
    }
}