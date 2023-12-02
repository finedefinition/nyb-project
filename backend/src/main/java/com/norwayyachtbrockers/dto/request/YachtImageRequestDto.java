package com.norwayyachtbrockers.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class YachtImageRequestDto {

    private Long id;

    @NotNull(message = "Image key cannot be null")
    @Size(min = 1, max = 40, message = "Image key must be between 1 and 255 characters long")
    private String imageKey;

//    @NotNull(message = "Yacht ID cannot be null")
//    @Positive(message = "Yacht ID must be a positive number")
//    private Long yachtId;
}
