package com.norwayyachtbrockers.repository.projections;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class YachtModelProjection {
    private Long id;
    private String make;
    private String model;
    private Integer year;
    private BigDecimal lengthOverall;
    private BigDecimal beamWidth;
    private BigDecimal draftDepth;
    private Long keelTypeId;
    private String keelTypeName;
    private LocalDateTime keelTypeCreatedAt;
    private LocalDateTime keelTypeUpdatedAt;

    // Поля из Fuel
    private Long fuelTypeId;
    private String fuelTypeName;
    private LocalDateTime fuelTypeCreatedAt;
    private LocalDateTime fuelTypeUpdatedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Конструктор с параметрами
    public YachtModelProjection(Long id, String make, String model,
                                Integer year, BigDecimal lengthOverall,
                                BigDecimal beamWidth, BigDecimal draftDepth,
                                Long keelTypeId, String keelTypeName, LocalDateTime keelTypeCreatedAt, LocalDateTime keelTypeUpdatedAt,
                                Long fuelTypeId, String fuelTypeName, LocalDateTime fuelTypeCreatedAt, LocalDateTime fuelTypeUpdatedAt,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.lengthOverall = lengthOverall;
        this.beamWidth = beamWidth;
        this.draftDepth = draftDepth;

        this.keelTypeId = keelTypeId;
        this.keelTypeName = keelTypeName;
        this.keelTypeCreatedAt = keelTypeCreatedAt;
        this.keelTypeUpdatedAt = keelTypeUpdatedAt;

        this.fuelTypeId = fuelTypeId;
        this.fuelTypeName = fuelTypeName;
        this.fuelTypeCreatedAt = fuelTypeCreatedAt;
        this.fuelTypeUpdatedAt = fuelTypeUpdatedAt;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public YachtModelProjection() {
    }
}
