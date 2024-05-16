package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class KeelRequestDto {

    private Long id;

    @JsonProperty("keel_type_name")
    @NotNull(message="Keel type is required")
    @Size(min=3, max=20, message="Fuel type must be between 3 and 20 long.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Keel type must start with a capital letter and can include" +
            " letters, spaces, and hyphens.")
    private String name;

}
