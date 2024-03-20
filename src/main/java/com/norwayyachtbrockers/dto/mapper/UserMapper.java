package com.norwayyachtbrockers.dto.mapper;

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
    
}
