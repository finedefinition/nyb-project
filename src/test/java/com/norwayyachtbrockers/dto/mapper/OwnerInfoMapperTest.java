package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;
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

@Order(200)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OwnerInfoMapperTest {

    @Autowired
    private OwnerInfoMapper ownerInfoMapper;

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
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(10)
    void testCreateOwnerInfoFromDto_NullDto() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> ownerInfoMapper.createOwnerInfoFromDto(null),
                "Should throw IllegalArgumentException for null DTO");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName is null on creation")
    @Order(20)
    void testCreateOwnerInfoFromDto_FirstNameNull() {
        // Arrange
        dto.setFirstName(null);  // Setting null as firsName
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the FirstName is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName shorter than 1 character")
    @Order(30)
    void testFirstNameTooShort() {
        // Arrange
        dto.setFirstName(INVALID_NAME_TOO_SHORT);  // Setting empty string as firsName
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the FirstName is too short.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name longer than 30 characters")
    @Order(40)
    void testFirstNameTooLong() {
        // Arrange

        dto.setFirstName(INVALID_NAME_TOO_LONG); // Setting firstName longer than the maximum length
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the FirstName is too long.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName with invalid characters")
    @Order(50)
    void testFirstNameInvalidCharacters() {

        // Arrange
        dto.setFirstName(INVALID_NAME_WITH_DIGITS); // Setting firstName with numbers, which are invalid
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the FirstName contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName not starting with capital letter")
    @Order(60)
    void testFirstNameDoesNotStartWithCapital() {
        // Arrange
        dto.setFirstName(INVALID_NAME_LOWERCASE);  // Starting with a lowercase letter
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException for names not starting with a capital letter.");
    }

    @Test
    @DisplayName("FirstName is trimmed correctly")
    @Order(70)
    void testFirstNameTrimString() {
        // Arrange
        dto.setFirstName(FIRST_NAME + "     ");  // firstName with trailing spaces
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act
        OwnerInfo createdOwnerInfo = ownerInfoMapper.createOwnerInfoFromDto(dto);

        // Assert
        assertEquals(FIRST_NAME, createdOwnerInfo.getFirstName(), "FirstName should be trimmed correctly");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName is null on creation")
    @Order(80)
    void testCreateOwnerInfoFromDto_LastNameNull() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(null); // Setting null as lastName
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the LastName is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName shorter than 1 character")
    @Order(90)
    void testLastNameTooShort() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(INVALID_NAME_TOO_SHORT);  // Setting empty string as lastName
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the LastName is too short.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName longer than 30 characters")
    @Order(100)
    void testLastNameTooLong() {
        // Arrange

        dto.setFirstName(FIRST_NAME);
        dto.setLastName(INVALID_NAME_TOO_LONG); // Setting LastName longer than the maximum length
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the LastName is too long.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName with invalid characters")
    @Order(110)
    void testLastNameInvalidCharacters() {

        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(INVALID_NAME_WITH_DIGITS); // Setting lastName with numbers, which are invalid
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the LastName contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName not starting with capital letter")
    @Order(120)
    void testLastNameDoesNotStartWithCapital() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(INVALID_NAME_LOWERCASE);  // Starting with a lowercase letter
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException for names not starting with a capital letter.");
    }

    @Test
    @DisplayName("LastName is trimmed correctly")
    @Order(130)
    void testLastNameTrimString() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME + "     "); // lastName with trailing spaces
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(EMAIL);

        // Act
        OwnerInfo createdOwnerInfo = ownerInfoMapper.createOwnerInfoFromDto(dto);

        // Assert
        assertEquals(FIRST_NAME, createdOwnerInfo.getFirstName(), "LastName should be trimmed correctly");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains PhoneNumber is null on creation")
    @Order(140)
    void testCreateOwnerInfoFromDto_PhoneNumberNull() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(null); // Setting null as phoneNumber
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the PhoneNumber is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains PhoneNumber shorter than 7 character")
    @Order(150)
    void testPhoneNumberTooShort() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(INVALID_PHONE_NUMBER_TOO_SHORT); // Setting phoneNumber shorter than the minimum length
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the PhoneNumber is too short.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains PhoneNumber longer than 15 characters")
    @Order(160)
    void testPhoneNumberTooLong() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(INVALID_PHONE_NUMBER_TOO_LONG); // Setting PhoneNumber longer than the maximum length
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the PhoneNumber is too long.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains PhoneNumber with invalid characters")
    @Order(170)
    void testPhoneNumberInvalidCharacters() {

        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(INVALID_PHONE_NUMBER_LETTERS); // Setting phoneNumber with letters, which are invalid
        dto.setEmail(EMAIL);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the PhoneNumber contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Email is null on creation")
    @Order(171)
    void testCreateOwnerInfoFromDto_EmailNull() {
        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(null); // Setting null as email

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the Email is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Email with invalid characters")
    @Order(180)
    void testEmailInvalidCharacters() {

        // Arrange
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setEmail(INVALID_EMAIL); // Setting email with letters, which are invalid

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> ownerInfoMapper.createOwnerInfoFromDto(dto),
                "Should throw ConstraintViolationException when the Email contains invalid characters.");
    }

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
        OwnerInfo createdOwnerInfo = ownerInfoMapper.createOwnerInfoFromDto(dto);

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
    @DisplayName("Throw ConstraintViolationException for null OwnerInfo on update")
    @Order(200)
    void testUpdateOwnerInfoFromDto_NullOwnerInfo() {
        // Assert
        assertThrows(ConstraintViolationException.class, () -> ownerInfoMapper.updateOwnerInfoFromDto(null, dto),
                "Should throw ConstraintViolationException when the Country is null.");
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on update")
    @Order(210)
    void testUpdateOwnerInfoFromDto_NullDto() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> ownerInfoMapper.updateOwnerInfoFromDto(ownerInfo, null),
                "Should throw ConstraintViolationException when the DTO is null.");
    }

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
        OwnerInfo updatedOwnerInfo = ownerInfoMapper.updateOwnerInfoFromDto(ownerInfo, dto);

        // Assert
        assertEquals(UPDATED_FIRST_NAME, updatedOwnerInfo.getFirstName(), "First name should be updated to match the DTO");
        assertEquals(UPDATED_LAST_NAME, updatedOwnerInfo.getLastName(), "Last name should be updated to match the DTO");
        assertEquals(UPDATED_PHONE_NUMBER, updatedOwnerInfo.getTelephone(), "Telephone should be updated to match the DTO");
        assertEquals(UPDATED_EMAIL, updatedOwnerInfo.getEmail(), "Email should be updated to match the DTO");
    }
}