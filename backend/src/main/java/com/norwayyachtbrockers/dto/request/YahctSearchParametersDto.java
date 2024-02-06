package com.norwayyachtbrockers.dto.request;

import java.math.BigDecimal;

public record YahctSearchParametersDto(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String model,
        String country,
        String town,
        String keelType,
        String fuelType,
        Integer minYear,
        Integer maxYear,
        BigDecimal minLengthOverall,
        BigDecimal maxLengthOverall,
        BigDecimal minBeamWidth,
        BigDecimal maxBeamWidth,
        BigDecimal minDraftDepth,
        BigDecimal maxDraftDepth,
        Integer minCabinNumber,
        Integer maxCabinNumber,
        Integer minBerthNumber,
        Integer maxBerthNumber,
        Integer minHeadsNumber,
        Integer maxHeadsNumber,
        Integer minShowerNumber,
        Integer maxShowerNumber
) {
}
