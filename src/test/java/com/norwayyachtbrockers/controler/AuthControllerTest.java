package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.UpdateUserRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.service.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;

@Order(30)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private static final String USER_POOL_ID = "test-user-pool-id";
    private static final String USERNAME = "testUser";
    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "User";
    private static final String ROLE = "ROLE_USER";
    private static final String EMAIL = "testUser@example.com";
    private static final String REGISTRATION_SUCCESS = "User registered successfully";
    private static final String UPDATE_SUCCESS = "User updated successfully";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(authService);
    }

    @Test
    @Order(10)
    @DisplayName("registerUser - Successfully registers a user")
    void testRegisterUser_Success() {
        // Arrange
        UserRegistrationRequestDto userDto = new UserRegistrationRequestDto();
        doNothing().when(authService).register(userDto);

        // Act
        ResponseEntity<?> response = authController.registerUser(userDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response status should be 201");
        assertEquals(REGISTRATION_SUCCESS, response.getBody(), "Response body should match");
    }

//    @Test
//    @Order(30)
//    @DisplayName("updateUser - Successfully updates a user")
//    void testUpdateUser_Success() {
//        // Arrange
//        UpdateUserRequestDto request = new UpdateUserRequestDto();
//        request.setUsername(USERNAME);
//        request.setFirstName(FIRST_NAME);
//        request.setLastName(LAST_NAME);
//        request.setRole(ROLE);
//        doNothing().when(authService).updateUserAttributesAndManageGroups(
//                USERNAME, FIRST_NAME, LAST_NAME, ROLE, USER_POOL_ID);
//
//        // Act
//        ResponseEntity<String> response = authController.updateUser(request);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be 200");
//        assertEquals(UPDATE_SUCCESS, response.getBody(), "Response body should match");
//    }

    @Test
    @Order(40)
    @DisplayName("deleteUserByEmail - Successfully deletes a user by email")
    void testDeleteUserByEmail_Success() {
        // Arrange
        doNothing().when(authService).deleteUser(EMAIL);

        // Act
        ResponseEntity<String> response = authController.deleteUserByEmail(EMAIL);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Response status should be 204");
        assertNull(response.getBody(), "Response body should be null");
    }
}