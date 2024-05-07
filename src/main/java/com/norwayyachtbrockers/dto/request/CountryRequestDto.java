package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryRequestDto {

    private Long id;

    @JsonProperty("country_name")
    @NotNull(message="Country is required.")
    @Size(min=3, max=20, message="Country name must be between 3 and 20 characters long.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Country must start with a capital letter and can include" +
            " letters, spaces, and hyphens.")
    private String name;
}
