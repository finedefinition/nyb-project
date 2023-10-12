package com.norwayyachtbrockers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vessels")
public class Vessel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "featured", nullable = false)
    private boolean featuredVessel;

    @Column(name = "make", nullable = false)
    private String vesselMake;

    @Column(name = "model", nullable = false)
    private String vesselModel;

    @Column(name = "price", nullable = false)
    private BigDecimal vesselPrice;

    @Column(name = "year", nullable = false)
    private int vesselYear;

    @Column(name = "location_country", nullable = false)
    private String vesselLocationCountry;

    @Column(name = "location_state", nullable = false)
    private String vesselLocationState;

    @Column(name = "loa", precision = 6, scale = 2, nullable = false)
    private BigDecimal vesselLengthOverall;

    @Column(name = "beam", precision = 6, scale = 2, nullable = false)
    private BigDecimal vesselBeam;

    @Column(name = "draft", precision = 6, scale = 2, nullable = false)
    private BigDecimal vesselDraft;

    @Column(name = "cabin", nullable = false)
    private int vesselCabin;

    @Column(name = "berth", nullable = false)
    private int vesselBerth;

    @Column(name = "keel_type", nullable = false)
    private String vesselKeelType;

    @Column(name = "fuel_type", nullable = false)
    private String vesselFuelType;

    @Column(name = "engines", nullable = false)
    private int engineQuantity;

    @Column(name = "description", nullable = false)
    private String vesselDescription;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "image_key", nullable = false)
    private String imageKey;

    public Vessel() {
    }

    public Vessel(boolean featuredVessel, String vesselMake, String vesselModel,
                  BigDecimal vesselPrice, int vesselYear, String vesselLocationCountry,
                  String vesselLocationState, BigDecimal vesselLengthOverall, BigDecimal vesselBeam,
                  BigDecimal vesselDraft, int vesselCabin, int vesselBerth, String vesselKeelType,
                  String vesselFuelType, int engineQuantity, String vesselDescription,
                  LocalDateTime createdAt, String imageKey) {
        this.featuredVessel = featuredVessel;
        this.vesselMake = vesselMake;
        this.vesselModel = vesselModel;
        this.vesselPrice = vesselPrice;
        this.vesselYear = vesselYear;
        this.vesselLocationCountry = vesselLocationCountry;
        this.vesselLocationState = vesselLocationState;
        this.vesselLengthOverall = vesselLengthOverall;
        this.vesselBeam = vesselBeam;
        this.vesselDraft = vesselDraft;
        this.vesselCabin = vesselCabin;
        this.vesselBerth = vesselBerth;
        this.vesselKeelType = vesselKeelType;
        this.vesselFuelType = vesselFuelType;
        this.engineQuantity = engineQuantity;
        this.vesselDescription = vesselDescription;
        this.createdAt = createdAt;
        this.imageKey = imageKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFeaturedVessel() {
        return featuredVessel;
    }

    public void setFeaturedVessel(boolean featuredVessel) {
        this.featuredVessel = featuredVessel;
    }

    public String getVesselMake() {
        return vesselMake;
    }

    public void setVesselMake(String vesselMake) {
        this.vesselMake = vesselMake;
    }

    public String getVesselModel() {
        return vesselModel;
    }

    public void setVesselModel(String vesselModel) {
        this.vesselModel = vesselModel;
    }

    public BigDecimal getVesselPrice() {
        return vesselPrice;
    }

    public void setVesselPrice(BigDecimal vesselPrice) {
        this.vesselPrice = vesselPrice;
    }

    public int getVesselYear() {
        return vesselYear;
    }

    public void setVesselYear(int vesselYear) {
        this.vesselYear = vesselYear;
    }

    public String getVesselLocationCountry() {
        return vesselLocationCountry;
    }

    public void setVesselLocationCountry(String vesselLocationCountry) {
        this.vesselLocationCountry = vesselLocationCountry;
    }

    public String getVesselLocationState() {
        return vesselLocationState;
    }

    public void setVesselLocationState(String vesselLocationState) {
        this.vesselLocationState = vesselLocationState;
    }

    public BigDecimal getVesselLengthOverall() {
        return vesselLengthOverall;
    }

    public void setVesselLengthOverall(BigDecimal vesselLengthOverall) {
        this.vesselLengthOverall = vesselLengthOverall;
    }

    public BigDecimal getVesselBeam() {
        return vesselBeam;
    }

    public void setVesselBeam(BigDecimal vesselBeam) {
        this.vesselBeam = vesselBeam;
    }

    public BigDecimal getVesselDraft() {
        return vesselDraft;
    }

    public void setVesselDraft(BigDecimal vesselDraft) {
        this.vesselDraft = vesselDraft;
    }

    public int getVesselCabin() {
        return vesselCabin;
    }

    public void setVesselCabin(int vesselCabin) {
        this.vesselCabin = vesselCabin;
    }

    public int getVesselBerth() {
        return vesselBerth;
    }

    public void setVesselBerth(int vesselBerth) {
        this.vesselBerth = vesselBerth;
    }

    public String getVesselKeelType() {
        return vesselKeelType;
    }

    public void setVesselKeelType(String vesselKeelType) {
        this.vesselKeelType = vesselKeelType;
    }

    public String getVesselFuelType() {
        return vesselFuelType;
    }

    public void setVesselFuelType(String vesselFuelType) {
        this.vesselFuelType = vesselFuelType;
    }

    public int getEngineQuantity() {
        return engineQuantity;
    }

    public void setEngineQuantity(int engineQuantity) {
        this.engineQuantity = engineQuantity;
    }

    public String getVesselDescription() {
        return vesselDescription;
    }

    public void setVesselDescription(String vesselDescription) {
        this.vesselDescription = vesselDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
