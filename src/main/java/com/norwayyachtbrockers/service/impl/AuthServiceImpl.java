package com.norwayyachtbrockers.service.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.norwayyachtbrockers.dto.mapper.UserMapper;
import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.enums.UserRoles;
import com.norwayyachtbrockers.service.AuthService;
import com.norwayyachtbrockers.service.UserService;
import com.norwayyachtbrockers.util.CognitoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AWSCognitoIdentityProvider cognitoClient;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.clientSecret}")
    private String clientSecret;

    public AuthServiceImpl(UserService userService, UserMapper userMapper, AWSCognitoIdentityProvider cognitoClient) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.cognitoClient = cognitoClient;
    }

    @Override
    @Transactional
    public void register(UserRegistrationRequestDto request) {
        try {
            String secretHash = generateSecretHash(request.getEmail());
            SignUpRequest signUpRequest = new SignUpRequest()
                    .withClientId(clientId)
                    .withSecretHash(secretHash)
                    .withUsername(request.getEmail())
                    .withPassword(request.getPassword())
                    .withUserAttributes(Arrays.asList(
                            new AttributeType().withName("email").withValue(request.getEmail()),
                            new AttributeType().withName("given_name").withValue(request.getFirstName()),
                            new AttributeType().withName("family_name").withValue(request.getLastName())
                    ));

            SignUpResult signUpResult = cognitoClient.signUp(signUpRequest);

            // Add user to the "ROLE_USER" group
            AdminAddUserToGroupRequest groupRequest = new AdminAddUserToGroupRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(request.getEmail())
                    .withGroupName("ROLE_USER");

            cognitoClient.adminAddUserToGroup(groupRequest);

            User user = userMapper.createUserFromDto(request);
            user.setCognitoSub(signUpResult.getUserSub());
            user.setUserRoles(UserRoles.ROLE_USER);

            userService.saveUser(user);

        } catch (Exception e) {
            throw new RuntimeException("Error during registration: " + e.getMessage(), e);
        }
    }

    @Override
    public UserLoginResponseDto confirmUser(String email, String confirmationCode, String password) {
        try {
            String secretHash = generateSecretHash(email);

            ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest()
                    .withClientId(clientId)
                    .withUsername(email)
                    .withConfirmationCode(confirmationCode)
                    .withSecretHash(secretHash);

            cognitoClient.confirmSignUp(confirmSignUpRequest);

            String jwtToken = initiateAuthenticationAndGetToken(email, password, secretHash);
            return new UserLoginResponseDto(jwtToken);

        } catch (Exception e) {
            throw new RuntimeException("Error during user confirmation: " + e.getMessage(), e);
        }
    }

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
        try {
            String secretHash = generateSecretHash(request.getEmail());
            String jwtToken = initiateAuthenticationAndGetToken(request.getEmail(), request.getPassword(), secretHash);
            return new UserLoginResponseDto(jwtToken);
        } catch (NotAuthorizedException | UserNotConfirmedException e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateUserAttributesAndManageGroups(String username, String firstName, String lastName, String newRole,
                                                    String userPoolId) {
        // Step 1: Retrieve current user attributes
        AdminGetUserRequest getUserRequest = new AdminGetUserRequest()
                .withUsername(username)
                .withUserPoolId(userPoolId);
        AdminGetUserResult userResult = cognitoClient.adminGetUser(getUserRequest);

        // Step 2: Retrieve current group memberships
        AdminListGroupsForUserRequest listGroupsRequest = new AdminListGroupsForUserRequest()
                .withUsername(username)
                .withUserPoolId(userPoolId);
        AdminListGroupsForUserResult listGroupsResult = cognitoClient.adminListGroupsForUser(listGroupsRequest);

        String currentRole = listGroupsResult.getGroups().stream()
                .findFirst() // Assuming one role per user scenario
                .map(GroupType::getGroupName)
                .orElse(null);

        // Step 3: Update user attributes if necessary
        List<AttributeType> attributesToUpdate = new ArrayList<>();
        if (firstName != null && !firstName.isEmpty() && !firstName.equals(getAttributeValue(userResult, "given_name"))) {
            attributesToUpdate.add(new AttributeType().withName("given_name").withValue(firstName));
        }
        if (lastName != null && !lastName.isEmpty() && !lastName.equals(getAttributeValue(userResult, "family_name"))) {
            attributesToUpdate.add(new AttributeType().withName("family_name").withValue(lastName));
        }

        if (!attributesToUpdate.isEmpty()) {
            AdminUpdateUserAttributesRequest updateAttributesRequest = new AdminUpdateUserAttributesRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(username)
                    .withUserAttributes(attributesToUpdate);
            cognitoClient.adminUpdateUserAttributes(updateAttributesRequest);
        }

        // Step 4: Manage user group membership
        if (newRole != null && !newRole.equals(currentRole)) {
            if (currentRole != null) {
                removeUserFromGroup(username, currentRole, userPoolId);
            }
            if (!newRole.isEmpty()) {
                addUserToGroup(username, newRole, userPoolId);
            }
        }
        // Step 5: Add user to database
        Long id = userService.findUserIdByEmail(username);
        User user = new User();
        user.setId(id);
        user.setEmail(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRoles(UserRoles.fromString(newRole));

        userService.updateUser(user, id);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        try {
            // First, attempt to delete the user from AWS Cognito
            deleteUserFromCognito(username);

            // If successful, delete the user from the local database
            Long id = userService.findUserIdByEmail(username);
            userService.deleteById(id);
        } catch (Exception e) {
            // Log the error and handle it, possibly rethrowing if the operation should be known to fail
            throw new AppEntityNotFoundException("Failed to delete user: " + username + ". User doesn't exist.");
        }
    }

    @Override
    public void initiatePasswordRecovery(String email) {
        try {
            String secretHash = generateSecretHash(email);

            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest()
                    .withClientId(clientId)
                    .withSecretHash(secretHash)
                    .withUsername(email);

            cognitoClient.forgotPassword(forgotPasswordRequest);

        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public void confirmPasswordRecovery(String email, String confirmationCode, String newPassword) {
        try {
            String secretHash = generateSecretHash(email);

            ConfirmForgotPasswordRequest confirmForgotPasswordRequest = new ConfirmForgotPasswordRequest()
                    .withClientId(clientId)
                    .withSecretHash(secretHash)
                    .withUsername(email)
                    .withConfirmationCode(confirmationCode)
                    .withPassword(newPassword);

            cognitoClient.confirmForgotPassword(confirmForgotPasswordRequest);

        } catch (Exception e) {
            throw new RuntimeException("Error confirming password recovery: " + e.getMessage(), e);
        }
    }

    private void deleteUserFromCognito(String username) {
        AdminDeleteUserRequest deleteRequest = new AdminDeleteUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username);
        cognitoClient.adminDeleteUser(deleteRequest);
    }


    private void addUserToGroup(String username, String groupName, String userPoolId) {
        AdminAddUserToGroupRequest request = new AdminAddUserToGroupRequest()
                .withUsername(username)
                .withGroupName(groupName)
                .withUserPoolId(userPoolId);
        cognitoClient.adminAddUserToGroup(request);
    }

    private void removeUserFromGroup(String username, String groupName, String userPoolId) {
        AdminRemoveUserFromGroupRequest request = new AdminRemoveUserFromGroupRequest()
                .withUsername(username)
                .withGroupName(groupName)
                .withUserPoolId(userPoolId);
        cognitoClient.adminRemoveUserFromGroup(request);
    }

    private String getAttributeValue(AdminGetUserResult userResult, String attributeName) {
        return userResult.getUserAttributes().stream()
                .filter(attr -> attr.getName().equals(attributeName))
                .findFirst()
                .map(AttributeType::getValue)
                .orElse(null);
    }

    private String generateSecretHash(String email) {
        return CognitoUtils.generateSecretHash(email, clientId, clientSecret);
    }

    private String initiateAuthenticationAndGetToken(String email, String password, String secretHash) {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", email);
        authParams.put("PASSWORD", password);
        authParams.put("SECRET_HASH", secretHash);

        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(clientId)
                .withAuthParameters(authParams);

        InitiateAuthResult authResult = cognitoClient.initiateAuth(authRequest);
        return authResult.getAuthenticationResult().getIdToken();
    }
}
