package com.norwayyachtbrockers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDto {

    @NotBlank(message = "User email is required.")
    @Email(message = "Please provide a valid email format.")
    private String username;

    @NotBlank(message="First name is required.")
    @Size(max = 30, message="First name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "First name must start with a capital letter and can" +
            " include letters, spaces, and hyphens.")
    private String firstName;

    @NotBlank(message="Last name is required.")
    @Size(max = 30, message="Last name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Last name must start with a capital letter and can" +
            " include letters, spaces, and hyphens.")
    private String lastName;

    private String role;
}
