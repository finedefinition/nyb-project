package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vessels")
public class Vessel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonProperty("vessel_id")
        private Long id;

        @Column(name = "featured", nullable = false)
        @JsonProperty("featured")
        private boolean featuredVessel;

        @NotNull(message="Make is required")
        @Size(min=3, message="Make must be at least 3 characters long")
        @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Make must start with a capital "
                + "letter and can consist of letters, spaces, and hyphens")
        @Column(name = "make", nullable = false)
        @JsonProperty("vessel_make")
        private String vesselMake;

        @NotNull(message="Model is required")
        @Size(min=1, message="Model cannot be empty")
        @Column(name = "model", nullable = false)
        @JsonProperty("vessel_model")
        private String vesselModel;

        @NotNull(message="Price is required")
        @DecimalMin(value="0.0", inclusive=false, message="Price must be greater than 0")
        @Column(name = "price", nullable = false)
        @JsonProperty("vessel_price")
        private BigDecimal vesselPrice;

        @Min(value=1900, message="Year must be later than 1900")
        @Max(value=2100, message="Year must be earlier than 2100")
        @Column(name = "year", nullable = false)
        @JsonProperty("vessel_year")
        private int vesselYear;

        @NotNull(message="Location country is required")
        @Size(min=1, message="Location country cannot be empty")
        @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Location country start with a capital"
                + " letter and consist of only letters")
        @Column(name = "location_country", nullable = false)
        @JsonProperty("vessel_country")
        private String vesselLocationCountry;

        @NotNull(message="Location state is required")
        @Size(min=1, message="Location state cannot be empty")
        @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Location state must start with a capital"
                + " letter and consist of only letters")
        @Column(name = "location_state", nullable = false)
        @JsonProperty("vessel_town")
        private String vesselLocationState;

        @NotNull(message="Length overall is required")
        @DecimalMin(value="0.0", inclusive=false, message="Length overall must be greater than 0")
        @Column(name = "loa", precision = 6, scale = 2, nullable = false)
        @JsonProperty("vessel_loa")
        private BigDecimal vesselLengthOverall;

        @NotNull(message="Beam is required")
        @DecimalMin(value="0.0", inclusive=false, message="Beam must be greater than 0")
        @Column(name = "beam", precision = 6, scale = 2, nullable = false)
        @JsonProperty("vessel_beam")
        private BigDecimal vesselBeam;

        @NotNull(message="Draft is required")
        @DecimalMin(value="0.0", inclusive=false, message="Draft must be greater than 0")
        @Column(name = "draft", precision = 6, scale = 2, nullable = false)
        @JsonProperty("vessel_draft")
        private BigDecimal vesselDraft;

        @Min(value=0, message="Number of cabins must be non-negative")
        @Column(name = "cabin", nullable = false)
        @JsonProperty("vessel_cabin")
        private int vesselCabin;

        @Min(value=0, message="Number of berths must be non-negative")
        @Column(name = "berth", nullable = false)
        @JsonProperty("vessel_berth")
        private int vesselBerth;

        @Column(name = "fuel", nullable = true)
        @Enumerated(EnumType.STRING)
        @JsonProperty("vessel_fuel_type")
        private FuelType fuelType;

        @Column(name = "keel", nullable = true)
        @Enumerated(EnumType.STRING)
        @JsonProperty("vessel_keel_type")
        private KeelType keelType;

        @Min(value=0, message="Number of engines must be non-negative")
        @Column(name = "engines", nullable = false)
        @JsonProperty("vessel_engine")
        private int engineQuantity;

        @NotNull(message="Description is required")
        @Size(min=1, message="Description cannot be empty")
        @Column(name = "description", nullable = false)
        @JsonProperty("vessel_description")
        private String vesselDescription;

        @Column(name = "created_at", nullable = false)
        @JsonProperty("vessel_created_at")
        private LocalDateTime createdAt;

        @Column(name = "image_key", nullable = false)
        @JsonProperty("vessel_image_key")
        private String imageKey;

    public Vessel() {
    }

    public Vessel(boolean featuredVessel, String vesselMake, String vesselModel, BigDecimal vesselPrice,
                  int vesselYear, String vesselLocationCountry, String vesselLocationState,
                  BigDecimal vesselLengthOverall, BigDecimal vesselBeam, BigDecimal vesselDraft, int vesselCabin,
                  int vesselBerth, KeelType keelType, FuelType fuelType,
                  int engineQuantity, String vesselDescription, LocalDateTime createdAt, String imageKey) {
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
        this.fuelType = fuelType;
        this.keelType = keelType;
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

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public KeelType getKeelType() {
        return keelType;
    }

    public void setKeelType(KeelType keelType) {
        this.keelType = keelType;
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
