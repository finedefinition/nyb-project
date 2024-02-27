//package com.norwayyachtbrockers.service.impl;
//
//import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
//import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
//import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpRequest;
//import com.amazonaws.services.cognitoidp.model.AttributeType;
//import com.amazonaws.services.cognitoidp.model.AuthFlowType;
//import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
//import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
//import com.amazonaws.services.cognitoidp.model.SignUpRequest;
//import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
//import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
//import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;
//import com.norwayyachtbrockers.service.UserService;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//    private final AWSCognitoIdentityProvider cognitoClient;
//    @Value("${aws.cognito.userPoolId}")
//    private String userPoolId;
//    @Value("${aws.cognito.clientId}")
//    private String clientId;
//
//    @Override
//    public void register(UserRegistrationRequestDto request) {
//        // First, create the user
//        SignUpRequest signUpRequest = new SignUpRequest()
//                .withClientId(clientId)
//                .withUsername(request.getEmail())
//                .withPassword(request.getPassword())
//                .withUserAttributes(Arrays.asList(
//                        new AttributeType().withName("email").withValue(request.getEmail()),
//                        new AttributeType().withName("given_name").withValue(request.getFirstName()),
//                        new AttributeType().withName("family_name").withValue(request.getLastName())
//                ));
//
//        cognitoClient.signUp(signUpRequest);
//
//        // Auto-confirm the user
//        AdminConfirmSignUpRequest confirmSignUpRequest = new AdminConfirmSignUpRequest()
//                .withUserPoolId(userPoolId)
//                .withUsername(request.getEmail());
//
//        cognitoClient.adminConfirmSignUp(confirmSignUpRequest);
//
//        // Then, add the user to the appropriate group
//        AdminAddUserToGroupRequest groupRequest = new AdminAddUserToGroupRequest()
//                .withUserPoolId(userPoolId)
//                .withUsername(request.getEmail())
//                .withGroupName("USER");
//
//        cognitoClient.adminAddUserToGroup(groupRequest);
//    }
//
//    @Override
//    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
//        Map<String, String> authParams = new HashMap<>();
//        authParams.put("USERNAME", request.getEmail());
//        authParams.put("PASSWORD", request.getPassword());
//
//        InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest()
//                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
//                .withClientId(clientId)
//                .withAuthParameters(authParams);
//
//        InitiateAuthResult initiateAuthResult = cognitoClient.initiateAuth(initiateAuthRequest);
//        String jwtToken = initiateAuthResult.getAuthenticationResult().getIdToken();
//        return new UserLoginResponseDto(jwtToken);
//    }
//}
