package com.norwayyachtbrockers.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class YachtDetailRequestDto {

    private Long id;

    @Min(value = 0, message = "Cabin count must be a non-negative number.")
    @Max(value = 10, message = "Cabin count must not exceed 10. Please provide a valid number of cabins.")
    private Integer cabin;

    @Min(value = 0, message = "Berth count must be a non-negative number.")
    @Max(value = 20, message = "Berth count must not exceed 20. Please provide a valid number of berths.")
    private Integer berth;

    @Min(value = 0, message = "Head count must be a non-negative number.")
    @Max(value = 10, message = "Head count must not exceed 10. Please provide a valid number of heads.")
    private Integer heads;

    @Min(value = 0, message = "Shower count must be a non-negative number.")
    @Max(value = 10, message = "Shower count must not exceed 10. Please provide a valid number of showers.")
    private Integer shower;

    @Size(max = 5000, message = "Description must not exceed 5000 characters. Please shorten your description.")
    private String description;
}
