package com.norwayyachtbrockers.service.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.norwayyachtbrockers.dto.mapper.UserMapper;
import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.enums.UserRoles;
import com.norwayyachtbrockers.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static com.amazonaws.util.ValidationUtils.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceImplTest {

    @Autowired
    private AuthServiceImpl authService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private AWSCognitoIdentityProvider cognitoClient;

    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "test@example.com";
    private static final String USER_PASSWORD = "Password123!";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final UserRoles USER_ROLES = UserRoles.ROLE_USER;
    private static final String USER_POOL_ID = "us-east-1_testpoolid";
    private static final String CLIENT_ID = "testclientid";
    private static final String CLIENT_SECRET = "testclientsecret";
    private static final String VALID_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlcnN1YiIsI" +
            "m5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    private UserRegistrationRequestDto registrationRequestDto;
    private UserLoginRequestDto loginRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock environment variables
        ReflectionTestUtils.setField(authService, "userPoolId", USER_POOL_ID);
        ReflectionTestUtils.setField(authService, "clientId", CLIENT_ID);
        ReflectionTestUtils.setField(authService, "clientSecret", CLIENT_SECRET);

        registrationRequestDto = new UserRegistrationRequestDto();
        registrationRequestDto.setEmail(USER_EMAIL);
        registrationRequestDto.setPassword(USER_PASSWORD);
        registrationRequestDto.setFirstName(USER_FIRST_NAME);
        registrationRequestDto.setLastName(USER_LAST_NAME);

        loginRequestDto = new UserLoginRequestDto();
        loginRequestDto.setEmail(USER_EMAIL);
        loginRequestDto.setPassword(USER_PASSWORD);
    }

    @Test
    @Order(10)
    @DisplayName("register - Successfully registers a new user")
    void testRegister_Success() {
        // Arrange
        SignUpResult signUpResult = new SignUpResult();
        AdminConfirmSignUpResult confirmSignUpResult = new AdminConfirmSignUpResult();
        AdminAddUserToGroupResult addUserToGroupResult = new AdminAddUserToGroupResult();
        AuthenticationResultType authResultType = new AuthenticationResultType().withIdToken(VALID_JWT_TOKEN);
        InitiateAuthResult initiateAuthResult = new InitiateAuthResult().withAuthenticationResult(authResultType);
        User user = new User();

        when(cognitoClient.signUp(any(SignUpRequest.class))).thenReturn(signUpResult);
        when(cognitoClient.adminConfirmSignUp(any(AdminConfirmSignUpRequest.class))).thenReturn(confirmSignUpResult);
        when(cognitoClient.adminAddUserToGroup(any(AdminAddUserToGroupRequest.class))).thenReturn(addUserToGroupResult);
        when(cognitoClient.initiateAuth(any(InitiateAuthRequest.class))).thenReturn(initiateAuthResult);
        when(userMapper.createUserFromDto(any(UserRegistrationRequestDto.class))).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Act
        authService.register(registrationRequestDto);

        // Assert
        verify(cognitoClient).signUp(any(SignUpRequest.class));
        verify(cognitoClient).adminConfirmSignUp(any(AdminConfirmSignUpRequest.class));
        verify(cognitoClient).adminAddUserToGroup(any(AdminAddUserToGroupRequest.class));
        verify(cognitoClient).initiateAuth(any(InitiateAuthRequest.class));
        verify(userMapper).createUserFromDto(any(UserRegistrationRequestDto.class));
        verify(userService).saveUser(any(User.class));
    }

    @Test
    @Order(20)
    @DisplayName("authenticate - Successfully authenticates a user")
    void testAuthenticate_Success() {
        // Arrange
        AuthenticationResultType authResultType = new AuthenticationResultType().withIdToken(VALID_JWT_TOKEN);
        InitiateAuthResult initiateAuthResult = new InitiateAuthResult().withAuthenticationResult(authResultType);

        when(cognitoClient.initiateAuth(any(InitiateAuthRequest.class))).thenReturn(initiateAuthResult);

        // Act
        UserLoginResponseDto response = authService.authenticate(loginRequestDto);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(VALID_JWT_TOKEN, response.getToken(), "JWT token should match");
    }

    @Test
    @Order(21)
    @DisplayName("authenticate - handles exceptions and returns error response")
    void testAuthenticateExceptionHandling() {
        // Arrange
        String expectedErrorMessage = "Error during login, please check logs.";

        // Simulate an exception being thrown when generateSecretHash or initiateAuthenticationAndGetToken is called
        when(cognitoClient.initiateAuth(any(InitiateAuthRequest.class))).
                thenThrow(new RuntimeException("Simulated authentication failure"));

        // Act
        UserLoginResponseDto response = authService.authenticate(loginRequestDto);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(expectedErrorMessage, response.getToken(),
                "Error message should be returned in the JWT token field");
        verify(cognitoClient, times(1))
                .initiateAuth(any(InitiateAuthRequest.class)); // Ensure that the mocked method was called
    }

    @Test
    @Order(30)
    @DisplayName("updateUserAttributesAndManageGroups - Successfully updates user attributes and manages groups")
    void testUpdateUserAttributesAndManageGroups_Success() {
        // Arrange
        AdminGetUserResult userResult = new AdminGetUserResult().withUserAttributes(Arrays.asList(
                new AttributeType().withName("given_name").withValue(USER_FIRST_NAME),
                new AttributeType().withName("family_name").withValue(USER_LAST_NAME)
        ));
        AdminListGroupsForUserResult groupsResult = new AdminListGroupsForUserResult().withGroups(
                new GroupType().withGroupName(USER_ROLES.toString())
        );
        User user = new User();

        when(cognitoClient.adminGetUser(any(AdminGetUserRequest.class))).thenReturn(userResult);
        when(cognitoClient.adminListGroupsForUser(any(AdminListGroupsForUserRequest.class))).thenReturn(groupsResult);
        when(userService.findUserIdByEmail(anyString())).thenReturn(1L);
        when(userService.updateUser(any(User.class), anyLong())).thenReturn(user);

        // Act
        authService.updateUserAttributesAndManageGroups(USER_EMAIL, "NewFirstName",
                "NewLastName", UserRoles.ROLE_MANAGER.toString(), USER_POOL_ID);

        // Assert
        verify(cognitoClient).adminGetUser(any(AdminGetUserRequest.class));
        verify(cognitoClient).adminListGroupsForUser(any(AdminListGroupsForUserRequest.class));
        verify(cognitoClient).adminUpdateUserAttributes(any(AdminUpdateUserAttributesRequest.class));
        verify(cognitoClient).adminRemoveUserFromGroup(any(AdminRemoveUserFromGroupRequest.class));
        verify(cognitoClient).adminAddUserToGroup(any(AdminAddUserToGroupRequest.class));
        verify(userService).updateUser(any(User.class), anyLong());
    }

    @Test
    @Order(40)
    @DisplayName("deleteUser - Successfully deletes a user")
    void testDeleteUser_Success() {
        // Arrange

        when(userService.findUserIdByEmail(USER_EMAIL)).thenReturn(USER_ID);  // Mock to return the userId when the email is provided.
        doNothing().when(userService).deleteById(USER_ID);  // Correctly apply doNothing() for a void method.

        // Act
        authService.deleteUser(USER_EMAIL);  // This is the method under test.

        // Assert
        verify(userService).findUserIdByEmail(USER_EMAIL);  // Verify that this method was called.
        verify(userService).deleteById(USER_ID);  // Verify that this method was called.
    }

    @Test
    @Order(50)
    @DisplayName("deleteUser - Handles exception and rethrows as RuntimeException")
    void testDeleteUser_ExceptionHandling() {
        // Arrange: Setup the scenario to throw an exception when deleteUserFromCognito is called
        doThrow(new RuntimeException("Cognito deletion failed")).when(cognitoClient).adminDeleteUser(any(AdminDeleteUserRequest.class));

        // Act & Assert: Ensure the exception is rethrown as a RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.deleteUser(USER_EMAIL);
        });

        // Verify the exception message and cause
        assertEquals("Failed to delete user: " + USER_EMAIL, exception.getMessage());
        assertNotNull(exception.getCause(), "The cause of the RuntimeException should not be null");
        assertEquals(RuntimeException.class, exception.getCause().getClass());
        assertEquals("Cognito deletion failed", exception.getCause().getMessage());
    }
}
