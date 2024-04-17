package com.norwayyachtbrockers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @NotBlank
    @Email
    private String username;
    private String firstName;
    private String lastName;
    private String role;
}
