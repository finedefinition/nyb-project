package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserFavouriteYachtsResponseDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User User);

    User saveCognitoUser(UserRegistrationRequestDto userRegistrationRequestDto);

    UserResponseDto findId(Long id);

    List<UserResponseDto> findAll();

    User updateUser(User user, Long id);

    void deleteById(Long id);
    void register(UserRegistrationRequestDto request);

    UserLoginResponseDto authenticate(UserLoginRequestDto request);

    public void addFavouriteYacht(Long userId, Long yachtId);

    public UserFavouriteYachtsResponseDto getFavouriteYachts(Long userId);

}
