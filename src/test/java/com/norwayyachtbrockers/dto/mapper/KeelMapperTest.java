package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Order(190)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class KeelMapperTest {
    private Keel keel;
    private KeelRequestDto dto;

    private static final String KEEL_NAME = "Bulb Keel";
    private static final String UPDATED_KEEL_NAME = "Swing Keel";

    @BeforeEach
    public void setUp() {
        keel = new Keel();
        dto = new KeelRequestDto();
    }

    // Test cases for createKeelFromDto(KeelRequestDto dto)

    @Test
    @DisplayName("Successfully create Keel from valid DTO")
    @Order(80)
    void testCreateKeelFromDto_ValidDto() {
        // Arrange
        dto.setName(KEEL_NAME);

        // Act
        Keel createdKeel = KeelMapper.createKeelFromDto(dto);

        // Assert
        assertNotNull(createdKeel, "The created Keel should not be null.");
        assertEquals(KEEL_NAME, createdKeel.getName(),
                "The name of the created Keel should match the DTO name.");
    }

    // Test cases for updateKeelFromDto(Keel keel, KeelRequestDto dto)

    @Test
    @DisplayName("Update Keel name using valid Keel and DTO")
    @Order(110)
    void testUpdateKeelFromDto_ValidArguments() {
        // Arrange
        keel.setName(KEEL_NAME);
        dto.setName(UPDATED_KEEL_NAME);

        // Act
        Keel updateKeel = KeelMapper.updateKeelFromDto(keel, dto);

        // Assert
        assertEquals(UPDATED_KEEL_NAME, updateKeel.getName(),
                "The country name should be updated to match the name specified in the DTO.");
    }
}
