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

    @NotNull(message="Keel type name is required")
    @Size(min=3, message="Keel type must be at least 3 characters long")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Keeel type must start with a capital "
            + "letter and can consist of letters, spaces, and hyphens")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    @JsonProperty("keel_type_name")
    private String name;

}
