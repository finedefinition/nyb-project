package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;

public interface UserService {
    void register(UserRegistrationRequestDto request);

    UserLoginResponseDto authenticate(UserLoginRequestDto request);
}
