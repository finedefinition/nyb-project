package com.norwayyachtbrockers.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class YachtModelRequestDto {

    private String make;

    private String model;

    private Integer year;

    private BigDecimal lengthOverall;

    private BigDecimal beamWidth;

    private BigDecimal draftDepth;

    private Long keelTypeId;

    private Long fuelTypeId;
}
