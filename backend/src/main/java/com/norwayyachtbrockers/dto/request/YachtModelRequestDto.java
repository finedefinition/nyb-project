package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.norwayyachtbrockers.util.TrimStringDeserializer;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class YachtModelRequestDto {

    private Long id;

    @NotNull(message="Make is required")
    @Size(min=3, message="Make must be at least 3 characters long")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Make must start with a capital "
            + "letter and can consist of letters, spaces, and hyphens")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    private String make;

    @NotNull(message="Model is required")
    @Size(min=1, message="Model cannot be empty")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    private String model;

    @Min(value=1930, message="Year must be later than 1930")
    @Max(value=2025, message="Year must be earlier than 2025")
    private Integer year;

    @NotNull(message="Length overall is required")
    @DecimalMin(value="0.0", inclusive=false, message="Length overall must be greater than 0")
    private BigDecimal lengthOverall;

    @NotNull(message="Beam width is required")
    @DecimalMin(value="0.0", inclusive=false, message="Beam must be greater than 0")
    private BigDecimal beamWidth;

    @NotNull(message="Draft depth is required")
    @DecimalMin(value="0.0", inclusive=false, message="Draft must be greater than 0")
    private BigDecimal draftDepth;

    private Long keelTypeId;

    private Long fuelTypeId;
}
