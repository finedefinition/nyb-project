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

    @NotNull(message="First name is required")
    @Size(min=1, message="First name must be at least 1 characters long")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "First name must start with a capital "
            + "letter and can consist of letters, spaces, and hyphens")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message="Last name is required")
    @Size(min=1, message="Last name must be at least 1 characters long")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Last name must start with a capital "
            + "letter and can consist of letters, spaces, and hyphens")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Telephone number is required")
    @Size(min = 7, max = 15, message = "Telephone number must be between 7 and 15 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Telephone must consist of only numbers")
    @JsonProperty("phone_number")
    private String telephone;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @JsonProperty("e-mail")
    private String email;
}
