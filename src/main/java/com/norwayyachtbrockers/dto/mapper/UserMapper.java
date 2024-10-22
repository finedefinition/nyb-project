package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.model.User;

public class UserMapper {

    public static User createUserFromDto(UserRegistrationRequestDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName().trim());
        user.setLastName(dto.getLastName().trim());
        user.setEmail(dto.getEmail());

        return user;
    }

    public static UserResponseDto convertUserToDto(User existingUser) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(existingUser.getId());
        dto.setEmail(existingUser.getEmail());
        dto.setFirstName(existingUser.getFirstName().trim());
        dto.setLastName(existingUser.getLastName().trim());
        dto.setRoleName(existingUser.getUserRoles().name());
        dto.setCognitoSub(existingUser.getCognitoSub());

        return dto;
    }
}
