package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @JsonProperty("user_first_name")
    @NotBlank(message="First name is required.")
    @Size(max = 30, message="First name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "First name must start with a capital letter and can" +
            " include letters, spaces, and hyphens.")
    private String firstName;

    @JsonProperty("user_last_name")
    @NotBlank(message="Last name is required.")
    @Size(max = 30, message="Last name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Last name must start with a capital letter and can" +
            " include letters, spaces, and hyphens.")
    private String lastName;

    @JsonProperty("user_email")
    @NotBlank(message = "User email is required.")
    @Email(message = "Please provide a valid email format.")
    private String email;

    @JsonProperty("user_password")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least one uppercase, one lowercase, one digit," +
                    " one special character, and no spaces")
    private String password;
}
