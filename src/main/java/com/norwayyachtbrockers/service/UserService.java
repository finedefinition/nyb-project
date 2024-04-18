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

    //Favourites
    void addFavouriteYachtToUser(String cognitoSub, Long yachtId);

    UserFavouriteYachtsResponseDto getFavouriteYachts(String cognitoSub);

    void removeFavouriteYacht(String cognitoSub, Long yachtId);
}
