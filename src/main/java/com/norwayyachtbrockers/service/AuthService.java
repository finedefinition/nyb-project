package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;

public interface AuthService {

    void register(UserRegistrationRequestDto request);

    void confirmUser(String email, String confirmationCode);

    UserLoginResponseDto authenticate(UserLoginRequestDto request);

    void initiatePasswordRecovery(String email);

    void confirmPasswordRecovery(String email, String confirmationCode, String newPassword);


    void updateUserAttributesAndManageGroups(String username, String firstName, String lastName,
                                             String newRole, String userPoolId);

    void deleteUser(String username);

}
