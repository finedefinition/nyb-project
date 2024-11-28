package com.norwayyachtbrockers.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class YachtSearchParametersDto {
    private Boolean featured;
    private Boolean vatIncluded;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String model;
    private String make;
    private String country;
    private String town;
    private String keelType;
    private String fuelType;
    private Integer minYear;
    private Integer maxYear;
    private BigDecimal minLengthOverall;
    private BigDecimal maxLengthOverall;
    private BigDecimal minBeamWidth;
    private BigDecimal maxBeamWidth;
    private BigDecimal minDraftDepth;
    private BigDecimal maxDraftDepth;
    private Integer minCabinNumber;
    private Integer maxCabinNumber;
    private Integer minBerthNumber;
    private Integer maxBerthNumber;
    private Integer minHeadsNumber;
    private Integer maxHeadsNumber;
    private Integer minShowerNumber;
    private Integer maxShowerNumber;
    private String ownerFirstName;
    private String ownerLastName;
}
