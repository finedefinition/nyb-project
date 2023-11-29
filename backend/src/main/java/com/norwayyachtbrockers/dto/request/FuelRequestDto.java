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

    @NotNull(message="Fuel type name is required")
    @Size(min=3, message="Fuel type must be at least 3 characters long")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Fuel type must start with a capital "
            + "letter and can consist of letters, spaces, and hyphens")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    @JsonProperty("fuel_type_name")
    private String name;
}
