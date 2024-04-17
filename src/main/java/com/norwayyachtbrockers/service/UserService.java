package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.response.UserFavouriteYachtsResponseDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User User);

    UserResponseDto findId(Long id);

    Long findUserIdByEmail(String email);

    List<UserResponseDto> findAll();

    User updateUser(User user, Long id);

    void deleteById(Long id);

    void addFavouriteYachtToUser(Long userId, Long yachtId);

    UserFavouriteYachtsResponseDto getFavouriteYachts(Long userId);

    void removeFavouriteYacht(Long userId, Long yachtId);
}
