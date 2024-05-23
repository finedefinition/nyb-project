package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.enums.UserRoles;
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

@Order(220)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private User user;
    private UserRegistrationRequestDto requestDto;

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Lennon";
    private static final String PASSWORD = "!John2024";
    private static final String EMAIL = "john.lennon@gmail.com";
    private static final Long USER_ID = 1L;
    private static final UserRoles USER_ROLES = UserRoles.ROLE_USER;
    private static final String COGNITO_SUB = "some-cognito-sub-id";
    private static final String INVALID_NAME_WITH_DIGITS = "12345";
    private static final String INVALID_NAME_LOWERCASE = "lowercasename";
    private static final String INVALID_NAME_TOO_LONG = "Abcdefghijklmnopqrstuvwxyzabcde";
    private static final String INVALID_NAME_TOO_SHORT = "";
    private static final String INVALID_EMAIL = "@emailcom";
    private static final String INVALID_PASSWORD_TOO_SHORT = "!John20";
    private static final String INVALID_PASSWORD_TOO_LONG = "!John1234567890123456";
    private static final String INVALID_PASSWORD_NO_SPECIAL_CHAR = "John1234";

    @BeforeEach
    void setUp() {
        user = new User();
        requestDto = new UserRegistrationRequestDto();
    }

    // Test cases for createUserFromDto(UserRegistrationRequestDto dto)

    @Test
    @DisplayName("Throw IllegalArgumentException for null DTO on creation")
    @Order(10)
    void testCreateUserFromDto_NullDto() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> userMapper.createUserFromDto(null),
                "Should throw IllegalArgumentException for null DTO");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName is null on creation")
    @Order(20)
    void testCreateUserFromDto_FirstNameNull() {
        // Arrange
        requestDto.setFirstName(null);  // Setting null as firsName
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the FirstName is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName shorter than 1 character")
    @Order(30)
    void testFirstNameTooShort() {
        // Arrange
        requestDto.setFirstName(INVALID_NAME_TOO_SHORT);  /// Setting empty string as firsName
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the FirstName is too short.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Name longer than 30 characters")
    @Order(40)
    void testFirstNameTooLong() {
        // Arrange
        requestDto.setFirstName(INVALID_NAME_TOO_LONG); // Setting firstName longer than the maximum length
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the FirstName is too long.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName with invalid characters")
    @Order(50)
    void testFirstNameInvalidCharacters() {
        // Arrange
        requestDto.setFirstName(INVALID_NAME_WITH_DIGITS); // Setting firstName with numbers, which are invalid
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the FirstName contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains FirstName not starting with capital letter")
    @Order(60)
    void testFirstNameDoesNotStartWithCapital() {
        // Arrange
        requestDto.setFirstName(INVALID_NAME_LOWERCASE);  // Starting with a lowercase letter
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException for names not starting with a capital letter.");
    }

    @Test
    @DisplayName("FirstName is trimmed correctly")
    @Order(70)
    void testFirstNameTrimString() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME + "     ");  // firstName with trailing spaces
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act
        User createdUser = userMapper.createUserFromDto(requestDto);

        // Assert
        assertEquals(FIRST_NAME, createdUser.getFirstName(), "FirstName should be trimmed correctly");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName is null on creation")
    @Order(80)
    void testCreateUserFromDto_LastNameNull() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(null); // Setting null as lastName
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the LastName is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName shorter than 1 character")
    @Order(90)
    void testLastNameTooShort() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(INVALID_NAME_TOO_SHORT);  // Setting empty string as lastName
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the LastName is too short.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName longer than 30 characters")
    @Order(100)
    void testLastNameTooLong() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(INVALID_NAME_TOO_LONG); // Setting LastName longer than the maximum length
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the LastName is too long.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName with invalid characters")
    @Order(110)
    void testLastNameInvalidCharacters() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(INVALID_NAME_WITH_DIGITS); // Setting lastName with numbers, which are invalid
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the LastName contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains LastName not starting with capital letter")
    @Order(120)
    void testLastNameDoesNotStartWithCapital() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(INVALID_NAME_LOWERCASE);  // Starting with a lowercase letter
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException for names not starting with a capital letter.");
    }

    @Test
    @DisplayName("LastName is trimmed correctly")
    @Order(130)
    void testLastNameTrimString() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME + "     "); // lastName with trailing spaces
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act
        User createdUser = userMapper.createUserFromDto(requestDto);

        // Assert
        assertEquals(FIRST_NAME, createdUser.getFirstName(), "LastName should be trimmed correctly");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Email is null on creation")
    @Order(131)
    void testCreateUserFromDto_EmailNull() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(null); // Setting null as email
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the Email is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Email with invalid characters")
    @Order(140)
    void testEmailInvalidCharacters() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(INVALID_EMAIL); // Setting email with letters, which are invalid
        requestDto.setPassword(PASSWORD);

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the Email contains invalid characters.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains PhoneNumber is null on creation")
    @Order(150)
    void testCreateUserFromDto_PasswordNull() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(null); // Setting null as password

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the Password is null.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Password shorter than 8 character")
    @Order(160)
    void testPasswordTooShort() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(INVALID_PASSWORD_TOO_SHORT); // Setting password shorter than the minimum length

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the Password is too short.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Password longer than 20 characters")
    @Order(170)
    void testPasswordTooLong() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(INVALID_PASSWORD_TOO_LONG); // Setting password longer than the maximum length

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the Password is too long.");
    }

    @Test
    @DisplayName("Throw ConstraintViolationException when DTO contains Password with invalid characters")
    @Order(180)
    void testPasswordInvalidCharacters() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(INVALID_PASSWORD_NO_SPECIAL_CHAR); // Setting password witn invalid char

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.createUserFromDto(requestDto),
                "Should throw ConstraintViolationException when the Password contains invalid characters.");
    }

    @Test
    @DisplayName("Successfully create User from valid DTO")
    @Order(190)
    void testCreateUserFromDto_ValidDto() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act
        User createdUser = userMapper.createUserFromDto(requestDto);

        // Assert
        assertNotNull(createdUser, "User should not be null after creation.");

        assertEquals(FIRST_NAME, createdUser.getFirstName(),
                "The first name in the created User should match the DTO's first name.");

        assertEquals(LAST_NAME, createdUser.getLastName(),
                "The last name in the created User should match the DTO's last name.");

        assertEquals(EMAIL, createdUser.getEmail(),
                "The email address in the created OwnerInfo should match the DTO's email.");
    }

    // Test cases for convertToDto(User existingUser)
    @Test
    @DisplayName("Throw IllegalArgumentException for null User")
    @Order(200)
    void testConvertUserToDto_NullUser() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> userMapper.convertUserToDto(null),
                "Should throw IllegalArgumentException for null DTO");
    }

    @Test
    @DisplayName("Convert User to UserResponseDto with null fields")
    @Order(210)
    void testConvertToDto_WithNullFields() {
        // Arrange
        user.setFirstName(null);
        user.setLastName(null);
        user.setUserRoles(null); // Assuming role can be null

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> userMapper.convertUserToDto(user),
                "Should throw ConstraintViolationException when some of the fields are null.");
    }

    @Test
    @DisplayName("Ensure UserResponseDto handles special characters in names")
    @Order(220)
    void testConvertToDto_SpecialCharacters() {
        // Arrange
        user.setFirstName("Jo-hn");
        user.setLastName("Len-non");
        user.setEmail(EMAIL);
        user.setUserRoles(USER_ROLES);
        user.setCognitoSub(COGNITO_SUB);

        // Act
        UserResponseDto dto = userMapper.convertUserToDto(user);

        // Assert
        assertEquals("Jo-hn", dto.getFirstName(), "First name should handle hyphens");
        assertEquals("Len-non", dto.getLastName(), "Last name should handle apostrophes");
    }

    @Test
    @DisplayName("Convert User to UserResponseDto successfully")
    @Order(300)
    void testConvertToDto_Success() {
        // Arrange
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setUserRoles(USER_ROLES);
        user.setCognitoSub(COGNITO_SUB);

        // Act
        UserResponseDto dto = userMapper.convertUserToDto(user);

        // Assert
        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getFirstName().trim(), dto.getFirstName());
        assertEquals(user.getLastName().trim(), dto.getLastName());
        assertEquals(user.getUserRoles().name(), dto.getRoleName());
        assertEquals(user.getCognitoSub(), dto.getCognitoSub());
    }
}