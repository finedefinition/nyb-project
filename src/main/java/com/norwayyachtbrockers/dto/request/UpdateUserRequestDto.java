package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norwayyachtbrockers.validation.annotations.ValidUserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDto {

    @JsonProperty("user_email")
    @Email(message = "Please provide a valid email format.")
    private String username;

    @JsonProperty("user_first_name")
    @Size(max = 30, message="First name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-'.,0-9]*$", message = "First name must start with a capital letter and" +
            " can include letters, spaces, hyphens, apostrophes, periods, and numerals.")
    private String firstName;

    @JsonProperty("user_last_name")
    @NotBlank(message="Last name is required.")
    @Size(max = 30, message="Last name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-'.,0-9]*$", message = "Last name must start with a capital letter and" +
            " can include letters, spaces, hyphens, apostrophes, periods, and numerals.")
    private String lastName;

    @JsonProperty("user_role")
    @ValidUserRole(message = "User role must be one of the following: ROLE_USER, ROLE_ADMIN, ROLE_MANAGER.")
    private String role;
}
