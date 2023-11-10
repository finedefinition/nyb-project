package com.norwayyachtbrockers.dto.request;

import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BoatModelRequestDto {

    private String make;

    private String model;

    private Integer year;

    private BigDecimal lengthOverall;

    private BigDecimal beamWidth;

    private BigDecimal draftDepth;

    private Keel keelType;

    private Fuel fuelType;
}
