package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.norwayyachtbrockers.util.TrimStringDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FuelRequestDto {

    private Long id;

    @JsonProperty("fuel_type_name")
    @NotNull(message="Fuel type is required")
    @Size(min=3, max=20, message="Fuel type must be between 3 and 20 long.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Fuel type must start with a capital letter and can include" +
            " letters, spaces, and hyphens.")
    private String name;
}
