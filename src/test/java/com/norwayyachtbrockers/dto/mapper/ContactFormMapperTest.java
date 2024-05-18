package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.ContactFormRequestDto;
import com.norwayyachtbrockers.model.ContactForm;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Order(10)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactFormMapperTest {

    @Autowired
    private ContactFormMapper contactFormMapper;

    private ContactForm contactForm;
    private ContactFormRequestDto dto;

    private static final String USER_EMAIL = "test@gmail.com";
    private static final String NAME = "John Doe";
    private static final String MESSAGE = "Hello! I would like to buy a yacht!";
    private static final String INVALID_EMAIL = "@emailcom";
    private static final String INVALID_NAME_WITH_DIGITS = "12345";
    private static final String INVALID_NAME_LOWERCASE = "lowercasename";
    private static final String INVALID_NAME_TOO_LONG = "Abcdefghijklmnopqrstuvwxyzabcde";
    private static final String INVALID_NAME_TOO_SHORT = "";

    @BeforeEach
    void setUp() {
        contactForm = new ContactForm();
        dto = new ContactFormRequestDto();
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(10)
    void testCreateContactFormFromDto_NullDto() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> contactFormMapper.createContactFormFromDto(null),
                "Should throw IllegalArgumentException when the DTO is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when Email is null")
    @Order(20)
    void testCreateContactFormFromDto_EmailNull() {
        // Arrange
        dto.setUserEmail(null); // Setting Email null
        dto.setName(NAME);
        dto.setMessage(MESSAGE);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException when Email is null");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when Email is empty")
    @Order(30)
    void testCreateContactFormFromDto_EmailEmpty() {
        // Arrange
        dto.setUserEmail(""); // Setting Email empty
        dto.setName(NAME);
        dto.setMessage(MESSAGE);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException when Email is empty");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains not valid Email on creation")
    @Order(40)
    void testCreateContactFormFromDto_EmailNotValid() {
        // Arrange
        dto.setUserEmail(INVALID_EMAIL); // Setting not valid Email
        dto.setName(NAME);
        dto.setMessage(MESSAGE);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException when the Email contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name is null on creation")
    @Order(50)
    void testCreateContactFormFromDto_NameNull() {
        // Arrange
        dto.setUserEmail(USER_EMAIL);
        dto.setName(null);  // Setting null as Name
        dto.setMessage(MESSAGE);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException when the Name is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName shorter than 1 character")
    @Order(60)
    void testCreateContactFormFromDto_NameTooShort() {
        // Arrange
        dto.setUserEmail(USER_EMAIL);
        dto.setName(INVALID_NAME_TOO_SHORT);  // Setting empty string as Name
        dto.setMessage(MESSAGE);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException when the Name is empty.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when Name over 30 signs on creation")
    @Order(70)
    void testCreateContactFormFromDto_NameTooLong() {
        // Arrange
        dto.setUserEmail(USER_EMAIL);
        dto.setName(INVALID_NAME_TOO_LONG);  // Setting invalid Name
        dto.setMessage(MESSAGE);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException when the Name is too long.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name with invalid characters")
    @Order(71)
    void testNameInvalidCharacters() {

        // Arrange
        dto.setUserEmail(USER_EMAIL);
        dto.setName(INVALID_NAME_WITH_DIGITS);  // Setting invalid Name
        dto.setMessage(MESSAGE);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException when the Name contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name not starting with capital letter")
    @Order(72)
    void testNameDoesNotStartWithCapital() {
        // Arrange
        // Arrange
        dto.setUserEmail(USER_EMAIL);
        dto.setName(INVALID_NAME_LOWERCASE);  // Setting invalid Name
        dto.setMessage(MESSAGE);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException for names not starting with a capital letter.");
    }

    @Test
    @DisplayName("Name is trimmed correctly")
    @Order(79)
    void testTrimString() {
        // Arrange
        ContactFormRequestDto dto = new ContactFormRequestDto();

        dto.setUserEmail(USER_EMAIL);
        dto.setName(NAME + "     ");// Name with trailing spaces
        dto.setMessage(MESSAGE);

        // Act
        ContactForm createdContactForm = contactFormMapper.createContactFormFromDto(dto);

        // Assert
        assertEquals(NAME, createdContactForm.getName(), "Name should be trimmed correctly");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Message is null on creation")
    @Order(80)
    void testCreateContactFormFromDto_MessageNull() {
        // Arrange
        dto.setUserEmail(USER_EMAIL);
        dto.setName(NAME);
        dto.setMessage(null); // Setting null as Message

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException for Message is null");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains empty Message on creation")
    @Order(90)
    void testCreateContactFormFromDto_MessageEmpty() {
        // Arrange
        dto.setUserEmail(USER_EMAIL);
        dto.setName(NAME);
        dto.setMessage(""); // Setting empty string as a Message

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException for empty Message");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when Message over 5000 signs on creation")
    @Order(100)
    void testCreateContactFormFromDto_MessageOver5000() {
        // Arrange
        dto.setUserEmail(USER_EMAIL);
        dto.setName(NAME);  // Setting Long string as Name
        dto.setMessage(randomAlphabetic(5001));

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> contactFormMapper.createContactFormFromDto(dto),
                "Should throw ConstraintViolationException for Message length over 5000 signs");
    }

    @Test
    @DisplayName("Successfully create ContactForm from valid DTO")
    @Order(110)
    void testCreateContactFormFromDto_ValidDto() {
        dto.setUserEmail(USER_EMAIL);
        dto.setName(NAME);
        dto.setMessage(MESSAGE);
        contactForm = contactFormMapper.createContactFormFromDto(dto);
        assertNotNull(contactForm);
        assertEquals(USER_EMAIL, contactForm.getUserEmail());
        assertEquals(NAME, contactForm.getName());
        assertEquals(MESSAGE, contactForm.getMessage());
    }
}