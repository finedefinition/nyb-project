package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;
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

@Order(200)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OwnerInfoMapperTest {
    private OwnerInfo ownerInfo;
    private OwnerInfoRequestDto dto;

    private static final String FIRST_NAME = "John";
    private static final String UPDATED_FIRST_NAME = "Ringo";
    private static final String LAST_NAME = "Lennon";
    private static final String UPDATED_LAST_NAME = "Starr";
    private static final String PHONE_NUMBER = "1234567";
    private static final String UPDATED_PHONE_NUMBER = "7654321";
    private static final String EMAIL = "john.lennon@gmail.com";
    private static final String UPDATED_EMAIL = "ringo.starr@gmail.com";
    private static final String INVALID_NAME_WITH_DIGITS = "12345";
    private static final String INVALID_NAME_LOWERCASE = "lowercasename";
    private static final String INVALID_NAME_TOO_LONG = "Abcdefghijklmnopqrstuvwxyzabcde";
    private static final String INVALID_NAME_TOO_SHORT = "";
    private static final String INVALID_PHONE_NUMBER_TOO_LONG = "12345678901234567";
    private static final String INVALID_PHONE_NUMBER_TOO_SHORT = "123456";
    private static final String INVALID_PHONE_NUMBER_LETTERS = "a1234567";
    private static final String INVALID_EMAIL = "@emailcom";

    @BeforeEach
    void setUp() {
        ownerInfo = new OwnerInfo();
        dto = new OwnerInfoRequestDto();
    }

    // Test cases for createOwnerInfoFromDto(OwnerInfo dto)

    @Test
    @DisplayName("Successfully create OwnerInfo from valid DTO")
    @Order(190)
    void testCreateOwnerInfoFromDto_ValidDto() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act
        OwnerInfo createdOwnerInfo = OwnerInfoMapper.createOwnerInfoFromDto(dto);

        // Assert
        assertNotNull(createdOwnerInfo, "OwnerInfo should not be null after creation.");

        assertEquals(FIRST_NAME, createdOwnerInfo.getFirstName(),
                "The first name in the created OwnerInfo should match the DTO's first name.");

        assertEquals(LAST_NAME, createdOwnerInfo.getLastName(),
                "The last name in the created OwnerInfo should match the DTO's last name.");

        assertEquals(PHONE_NUMBER, createdOwnerInfo.getTelephone(),
                "The telephone number in the created OwnerInfo should match the DTO's phone number.");

        assertEquals(EMAIL, createdOwnerInfo.getEmail(),
                "The email address in the created OwnerInfo should match the DTO's email.");
    }

    // Test cases for updateOwnerInfoFromDto(OwnerInfo ownerInfo, OwnerInfoRequestDto dto)

    @Test
    @DisplayName("Update OwnerInfo using valid OwnerInfo and DTO")
    @Order(220)
    void testUpdateOwnerInfoFromDto_ValidArguments() {
        // Arrange
        ownerInfo.setFirstName(FIRST_NAME);
        ownerInfo.setLastName(LAST_NAME);
        ownerInfo.setTelephone(PHONE_NUMBER);
        ownerInfo.setEmail(EMAIL);
        dto.setFirstName(UPDATED_FIRST_NAME);
        dto.setLastName(UPDATED_LAST_NAME);
        dto.setPhoneNumber(UPDATED_PHONE_NUMBER);
        dto.setEmail(UPDATED_EMAIL);

        // Act
        OwnerInfo updatedOwnerInfo = OwnerInfoMapper.updateOwnerInfoFromDto(ownerInfo, dto);

        // Assert
        assertEquals(UPDATED_FIRST_NAME, updatedOwnerInfo.getFirstName(), "First name should be updated to match the DTO");
        assertEquals(UPDATED_LAST_NAME, updatedOwnerInfo.getLastName(), "Last name should be updated to match the DTO");
        assertEquals(UPDATED_PHONE_NUMBER, updatedOwnerInfo.getTelephone(), "Telephone should be updated to match the DTO");
        assertEquals(UPDATED_EMAIL, updatedOwnerInfo.getEmail(), "Email should be updated to match the DTO");
    }
}
