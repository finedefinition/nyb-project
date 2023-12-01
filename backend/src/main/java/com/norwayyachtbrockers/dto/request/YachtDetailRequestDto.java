package com.norwayyachtbrockers.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class YachtDetailRequestDto {

    private Long id;

    @Min(value = 0)
    @Max(value = 10, message = "Cabin count must not exceed 10")
    private Integer cabin;

    @Min(value = 0)
    @Max(value = 20, message = "Berth count must not exceed 20")
    private Integer berth;

    @Min(value = 0)
    @Max(value = 10, message = "Head count must not exceed 10 ")
    private Integer heads;


    @Min(value = 0)
    @Max(value = 10, message = "Shower count must not exceed 10")
    private Integer shower;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;
}
