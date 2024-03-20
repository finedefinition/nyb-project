package com.norwayyachtbrockers.service.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.norwayyachtbrockers.dto.mapper.UserMapper;
import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserFavouriteYachtsResponseDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.enums.UserRoles;
import com.norwayyachtbrockers.repository.UserRepository;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.service.UserService;
import com.norwayyachtbrockers.util.CognitoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final YachtRepository yachtRepository;
    private final UserMapper userMapper;
    private final AWSCognitoIdentityProvider cognitoClient;
    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;
    @Value("${aws.cognito.clientId}")
    private String clientId;
    @Value("${aws.cognito.clientSecret}")
    private String clientSecret;

    public UserServiceImpl(UserRepository userRepository, YachtRepository yachtRepository, UserMapper userMapper, AWSCognitoIdentityProvider cognitoClient) {
        this.userRepository = userRepository;
        this.yachtRepository = yachtRepository;
        this.userMapper = userMapper;
        this.cognitoClient = cognitoClient;
    }

    @Override
    public void register(UserRegistrationRequestDto request) {

        try {
        String secretHash = CognitoUtils.generateSecretHash(request.getEmail(), clientId, clientSecret);
        // First, create the user
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

        cognitoClient.signUp(signUpRequest);

        } catch (Exception e) {
            throw new RuntimeException("Error while calculating ");
        }
        // Auto-confirm the user
        AdminConfirmSignUpRequest confirmSignUpRequest = new AdminConfirmSignUpRequest()
                .withUserPoolId(userPoolId)
                .withUsername(request.getEmail());

        cognitoClient.adminConfirmSignUp(confirmSignUpRequest);

        // Then, add the user to the appropriate group
        AdminAddUserToGroupRequest groupRequest = new AdminAddUserToGroupRequest()
                .withUserPoolId(userPoolId)
                .withUsername(request.getEmail())
                .withGroupName("ROLE_USER");

        cognitoClient.adminAddUserToGroup(groupRequest);
        User user = userMapper.convertToUser(request);
        user.setUserRoles(UserRoles.ROLE_USER);
        userRepository.save(user);
    }

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
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
        String token = "";
        UserLoginResponseDto dto = new UserLoginResponseDto(token);
        return dto;
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User saveCognitoUser(UserRegistrationRequestDto userRegistrationRequestDto) {
        return userRepository.save(userMapper.convertToUser(userRegistrationRequestDto));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findId(Long id) {
        User user = userRepository.findByIdAndFetchYachtsEagerly(id)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found with id " + id));
        return userMapper.convertToDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAllAndFetchYachtsEagerly()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User updateUser(User user, Long id) {
        userRepository.findByIdAndFetchYachtsEagerly(id)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found with id " + id));
        User userUpdated = new User();
        userUpdated.setId(id);
        userUpdated.setFirstName(user.getFirstName());
        userUpdated.setLastName(user.getLastName());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setUserRoles(user.getUserRoles());
        userUpdated.setCreatedAt(user.getCreatedAt());
        return userRepository.save(userUpdated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User userNew = userRepository.findByIdAndFetchYachtsEagerly(id)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found with id " + id));
        userRepository.delete(userNew);

    }

    @Override
    @Transactional
    public void addFavouriteYacht(Long userId, Long yachtId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found with id " + userId));
        Yacht yacht = yachtRepository.findById(yachtId)
                .orElseThrow(() -> new AppEntityNotFoundException("Yacht not found with id " + yachtId));

        user.getFavouriteYachts().add(yacht);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserFavouriteYachtsResponseDto getFavouriteYachts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppEntityNotFoundException("User not found with id " + userId));

        Set<Long> yachtIds = user.getFavouriteYachts().stream()
                .map(Yacht::getId)
                .collect(Collectors.toSet());

        UserFavouriteYachtsResponseDto dto = new UserFavouriteYachtsResponseDto();
        dto.setUserId(userId);
        dto.setFavouriteYachtIds(yachtIds);
        dto.setCount(yachtIds.size());

        return dto;
    }
}
