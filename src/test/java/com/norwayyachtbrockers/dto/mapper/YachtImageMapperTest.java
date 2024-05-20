package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Order(240)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtImageMapperTest {

    @Autowired
    private YachtImageMapper yachtImageMapper;

    private YachtImage yachtImage;
    private Yacht yacht;

    @BeforeEach
    void setUp() {
        yacht = new Yacht();
        yacht.setId(1L);  // Assuming there's a setter for ID in Yacht

        yachtImage = new YachtImage();
        yachtImage.setId(10L);
        yachtImage.setImageKey("image-key-123");
        yachtImage.setImageIndex(5);
        yachtImage.setYacht(yacht);
    }

    @Test
    @DisplayName("Convert YachtImage to YachtImageResponseDto")
    @Order(10)
    void testConvertToResponseDto() {
        // Act
        YachtImageResponseDto dto = yachtImageMapper.convertToResponseDto(yachtImage);

        // Assert
        assertAll("Ensure mapping is correct",
                () -> assertEquals(yachtImage.getId(), dto.getId(), "ID should match"),
                () -> assertEquals(yachtImage.getImageKey(), dto.getImageKey(), "Image key should match"),
                () -> assertEquals(yachtImage.getImageIndex(), dto.getImageIndex(), "Image index should match"),
                () -> assertEquals(yachtImage.getYacht().getId(), dto.getYachtId(), "Yacht ID should match")
        );
    }
}