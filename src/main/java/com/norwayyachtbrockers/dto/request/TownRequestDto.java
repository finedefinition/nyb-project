package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TownRequestDto {

    private Long id;

    @JsonProperty("town_name")
    @NotNull(message="Town is required.")
    @Size(min=3, max=30, message="Town must be at least 3 characters long and less than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Town must start with a capital letter and can include" +
            " letters, spaces, and hyphens.")
    private String name;

    @JsonProperty("town_country_id")
    @NotNull(message = "Country ID is required.")
    @Positive(message = "Country ID must be a positive value.")
    private Long countryId;
}
