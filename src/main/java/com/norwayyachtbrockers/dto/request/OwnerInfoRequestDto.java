package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.norwayyachtbrockers.util.TrimStringDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OwnerInfoRequestDto {

    private Long id;

    @JsonProperty("first_name")
    @NotNull(message="First name is required.")
    @Size(min = 1, message="First name must be at least 1 character long.")
    @Size(max = 30, message="First name must not exceed 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "First name must start with a capital "
            + "letter and can include letters, spaces, and hyphens")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    private String firstName;

    @JsonProperty("last_name")
    @NotNull(message="Last name is required.")
    @Size(min=1, message="Last name must be at least 1 characters long.")
    @Size(max=30, message="Last name must not exceed 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Last name must start with a capital "
            + "letter and can include letters, spaces, and hyphens")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    private String lastName;

    @JsonProperty("phone_number")
    @NotNull(message = "Telephone number is required")
    @Size(min = 7, max = 15, message = "Telephone number must be between 7 and 15 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Telephone must consist of only numbers")
    private String telephone;

    @JsonProperty("e-mail")
    @NotNull(message = "Email is required.")
    @Email(message = "Please provide a valid email format.")
    private String email;
}
