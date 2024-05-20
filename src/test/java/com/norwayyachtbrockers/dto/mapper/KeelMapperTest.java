package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Order(190)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class KeelMapperTest {

    @Autowired
    private KeelMapper keelMapper;

    private Keel keel;
    private KeelRequestDto dto;

    private static final String KEEL_NAME = "Bulb Keel";
    private static final String UPDATED_KEEL_NAME = "Swing Keel";
    private static final String INVALID_NAME_WITH_DIGITS = "12345";
    private static final String INVALID_NAME_LOWERCASE = "lowercasename";
    private static final String INVALID_NAME_TOO_LONG = "Abcdefghijklmnopqrstuvwxyz";
    private static final String INVALID_NAME_TOO_SHORT = "Ab";

    @BeforeEach
    public void setUp() {
        keel = new Keel();
        dto = new KeelRequestDto();
    }

    // Test cases for createKeelFromDto(KeelRequestDto dto)

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(10)
    void testCreateKeelFromDto_NullDto() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> keelMapper.createKeelFromDto(null),
                "Should throw IllegalArgumentException when the DTO is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name is null on creation")
    @Order(20)
    void testCreateKeelFromDto_NameNull() {
        // Arrange
        dto.setName(null);  // Setting null as Name

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> keelMapper.createKeelFromDto(dto),
                "Should throw ConstraintViolationException when the Name is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name shorter than 3 characters")
    @Order(30)
    void testNameTooShort() {
        // Arrange
        dto.setName(INVALID_NAME_TOO_SHORT);  // Setting Name shorter than the minimum length

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> keelMapper.createKeelFromDto(dto),
                "Should throw ConstraintViolationException when the Name is too short.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name longer than 20 characters")
    @Order(40)
    void testNameTooLong() {
        // Arrange
        dto.setName(INVALID_NAME_TOO_LONG);  // Setting Name longer than the maximum length

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> keelMapper.createKeelFromDto(dto),
                "Should throw ConstraintViolationException when the Name is too long");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name with invalid characters")
    @Order(50)
    void testNameInvalidCharacters() {
        // Arrange
        dto.setName(INVALID_NAME_WITH_DIGITS);  // Setting Name with numbers, which are invalid

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> keelMapper.createKeelFromDto(dto),
                "Should throw ConstraintViolationException when the Name contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name not starting with capital letter")
    @Order(60)
    void testNameDoesNotStartWithCapital() {
        // Arrange
        dto.setName(INVALID_NAME_LOWERCASE);  // Starting with a lowercase letter

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> keelMapper.createKeelFromDto(dto),
                "Should throw ConstraintViolationException for names not starting with a capital letter.");
    }

    @Test
    @DisplayName("Keel name is trimmed correctly")
    @Order(70)
    void testTrimString() {
        // Arrange
        dto.setName(KEEL_NAME + "     ");  // Name with trailing spaces

        // Act
        Keel createdKeel = keelMapper.createKeelFromDto(dto);

        // Assert
        assertEquals(KEEL_NAME, createdKeel.getName(), "Name should be trimmed correctly");
    }

    @Test
    @DisplayName("Successfully create Keel from valid DTO")
    @Order(80)
    void testCreateKeelFromDto_ValidDto() {
        // Arrange
        dto.setName(KEEL_NAME);

        // Act
        Keel createdKeel = keelMapper.createKeelFromDto(dto);

        // Assert
        assertNotNull(createdKeel, "The created Keel should not be null.");
        assertEquals(KEEL_NAME, createdKeel.getName(),
                "The name of the created Keel should match the DTO name.");
    }

    // Test cases for updateKeelFromDto(Keel keel, KeelRequestDto dto)

    @Test
    @DisplayName("Throw ConstraintViolationException for null Keel on update")
    @Order(90)
    void testUpdateKeelFromDto_NullKeel() {
        // Asserts that ConstraintViolationException is thrown when keel is null
        assertThrows(ConstraintViolationException.class, () -> keelMapper.updateKeelFromDto(null, dto),
                "Should throw ConstraintViolationException when the Keel is null.");
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on update")
    @Order(100)
    void testUpdateKeelFromDto_NullDto() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> keelMapper.updateKeelFromDto(keel, null),
                "Should throw IllegalArgumentException when the DTO is null.");
    }

    @Test
    @DisplayName("Update Keel name using valid Keel and DTO")
    @Order(110)
    void testUpdateKeelFromDto_ValidArguments() {
        // Arrange
        keel.setName(KEEL_NAME);
        dto.setName(UPDATED_KEEL_NAME);

        // Act
        Keel updateKeel = keelMapper.updateKeelFromDto(keel, dto);

        // Assert
        assertEquals(UPDATED_KEEL_NAME, updateKeel.getName(),
                "The country name should be updated to match the name specified in the DTO.");
    }
}