package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norwayyachtbrockers.constants.ApplicationConstants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class YachtModelRequestDto {

    private Long id;

    @JsonProperty("yacht_model_make")
    @NotNull(message = "Make is required.")
    @Size(min = 3,max = 30, message = "Make must be at least 3 characters long and less than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Make must start with a capital letter and can include" +
            " letters, spaces, and hyphens.")
    private String make;

    @JsonProperty("yacht_model_model")
    @NotNull(message="Model is required.")
    @Size(min=1, max = 30, message="Model must be at least 1 characters long and less than 30 characters.")
    private String model;

    @JsonProperty("yacht_model_year")
    @NotNull(message = "Year is required")
    @Min(value = 1930, message = "Year must be no earlier than 1930.")
    @Max(value = ApplicationConstants.NEXT_YEAR,
            message = "Year must be no later than " + ApplicationConstants.NEXT_YEAR)
    @Column(name = "year", nullable = false)
    private Integer year;

    @JsonProperty("yacht_model_length")
    @NotNull(message = "Length overall is required.")
    @DecimalMin(value = "2.5", message = "Length overall must be at least 2.5 meters.")
    @DecimalMax(value = "300", message = "Length overall must not exceed 300 meters.")
    private BigDecimal lengthOverall;

    @JsonProperty("yacht_model_width")
    @NotNull(message="Beam width is required.")
    @DecimalMin(value = "1", message = "Beam width must be at least 1 meter.")
    @DecimalMax(value = "25", message = "Beam width must not exceed 25 meters.")
    private BigDecimal beamWidth;

    @JsonProperty("yacht_model_depth")
    @NotNull(message = "Draft is required.")
    @DecimalMin(value = "0.3", message = "Draft depth must be at least 0.3 meter.")
    @DecimalMax(value = "16", message = "Draft depth must not exceed 16 meters.")
    private BigDecimal draftDepth;

    @JsonProperty("yacht_model_keel_type_id")
    @NotNull(message = "Keel type ID is required.")
    @Positive(message = "Keel type ID must be a positive value.")
    private Long keelTypeId;

    @JsonProperty("yacht_model_fuel_type_id")
    @NotNull(message = "Fuel type ID is required.")
    @Positive(message = "Fuel type ID must be a positive value.")
    private Long fuelTypeId;
}
