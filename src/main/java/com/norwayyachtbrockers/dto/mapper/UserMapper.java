package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@AllArgsConstructor
public class UserMapper {

    public User createUserFromDto(@Valid UserRegistrationRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Failed to create User: DTO cannot be null");
        }
        User user = new User();
        user.setFirstName(dto.getFirstName().trim());
        user.setLastName(dto.getLastName().trim());
        user.setEmail(dto.getEmail());

        return user;
    }

    public UserResponseDto convertUserToDto(@Valid User existingUser) {
        if (existingUser == null) {
            throw new IllegalArgumentException("Failed to create UserResponseDto: User cannot be null");
        }
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
