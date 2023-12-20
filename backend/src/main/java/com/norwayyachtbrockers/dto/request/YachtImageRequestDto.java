package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class YachtImageRequestDto {

    @NotNull(message = "Yacht ID cannot be null")
    @Positive(message = "Yacht ID must be a positive number")
    @JsonProperty("yacht_id")
    private Long yachtId;
}
