package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    public UserResponseDto convertToDto(User existingUser) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(existingUser.getId());
        dto.setEmail(existingUser.getEmail());
        dto.setFirstName(existingUser.getFirstName());
        dto.setLastName(existingUser.getLastName());
        dto.setRoleName(existingUser.getUserRoles().name());

        return dto;
    }

    public User convertToUser(UserRegistrationRequestDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        return user;
    }
}
