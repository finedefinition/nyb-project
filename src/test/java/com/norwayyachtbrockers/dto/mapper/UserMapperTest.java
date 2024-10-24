package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.enums.UserRoles;
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

@Order(220)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserMapperTest {
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
    @DisplayName("Successfully create User from valid DTO")
    @Order(190)
    void testCreateUserFromDto_ValidDto() {
        // Arrange
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setEmail(EMAIL);
        requestDto.setPassword(PASSWORD);

        // Act
        User createdUser = UserMapper.createUserFromDto(requestDto);

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
        UserResponseDto dto = UserMapper.convertUserToDto(user);

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
        UserResponseDto dto = UserMapper.convertUserToDto(user);

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