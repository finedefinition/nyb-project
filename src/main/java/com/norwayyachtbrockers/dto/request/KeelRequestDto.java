package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.norwayyachtbrockers.util.TrimStringDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KeelRequestDto {

    private Long id;

    @JsonProperty("keel_type_name")
    @NotNull(message="Keel type is required")
    @Size(min=3, max=20, message="Keel type must be at least 3 characters long and less than 20 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Keel type must start with a capital letter and can include" +
            " letters, spaces, and hyphens.")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    private String name;

}
