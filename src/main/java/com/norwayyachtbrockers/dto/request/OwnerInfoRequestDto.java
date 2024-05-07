package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OwnerInfoRequestDto {

    private Long id;

    @JsonProperty("first_name")
    @NotBlank(message="First name is required.")
    @Size(max = 30, message="First name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "First name must start with a capital "
            + "letter and can include letters, spaces, and hyphens.")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message="Last name is required.")
    @Size(max = 30, message="Last name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Last name must start with a capital "
            + "letter and can include letters, spaces, and hyphens.")
    private String lastName;

    @JsonProperty("phone_number")
    @NotNull(message = "Telephone number is required.")
    @Size(min = 7, max = 15, message = "Telephone number must be between 7 and 15 digits long.")
    @Pattern(regexp = "^[0-9]+$", message = "Telephone must consist of only numbers.")
    private String phoneNumber;

    @JsonProperty("e-mail")
    @NotBlank(message = "User email is required.")
    @Email(message = "Please provide a valid email format.")
    private String email;
}
