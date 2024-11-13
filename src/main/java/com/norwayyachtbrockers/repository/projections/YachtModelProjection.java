package com.norwayyachtbrockers.repository.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class YachtModelProjection {
    private Long id;
    private String make;
    private String model;
    private Integer year;
    private BigDecimal lengthOverall;
    private BigDecimal beamWidth;
    private BigDecimal draftDepth;
    private KeelProjection keelType;
    private FuelProjection fuelType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public YachtModelProjection(Long id, String make, String model,
                                Integer year, BigDecimal lengthOverall,
                                BigDecimal beamWidth, BigDecimal draftDepth,
                                KeelProjection keelType, FuelProjection fuelType,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.lengthOverall = lengthOverall;
        this.beamWidth = beamWidth;
        this.draftDepth = draftDepth;
        this.keelType = keelType;
        this.fuelType = fuelType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public YachtModelProjection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getLengthOverall() {
        return lengthOverall;
    }

    public void setLengthOverall(BigDecimal lengthOverall) {
        this.lengthOverall = lengthOverall;
    }

    public BigDecimal getBeamWidth() {
        return beamWidth;
    }

    public void setBeamWidth(BigDecimal beamWidth) {
        this.beamWidth = beamWidth;
    }

    public BigDecimal getDraftDepth() {
        return draftDepth;
    }

    public void setDraftDepth(BigDecimal draftDepth) {
        this.draftDepth = draftDepth;
    }

    public KeelProjection getKeelType() {
        return keelType;
    }

    public void setKeelType(KeelProjection keelType) {
        this.keelType = keelType;
    }

    public FuelProjection getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelProjection fuelType) {
        this.fuelType = fuelType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
