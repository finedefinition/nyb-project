package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norwayyachtbrockers.constants.ApplicationConstants;
import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vessels")
@EntityListeners(AuditingEntityListener.class)
public class Vessel extends BaseEntity {

    @Id
    @JsonProperty("vessel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("featured")
    @Column(name = "featured", nullable = false)
    private boolean featuredVessel;

    @JsonProperty("vessel_make")
    @NotNull(message = "Make is required.")
    @Size(min = 3,max = 30, message = "Make must be at least 3 characters long and less than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Make must start with a capital letter and can include" +
            " letters, spaces, and hyphens.")
    @Column(name = "make", nullable = false)
    private String vesselMake;

    @JsonProperty("vessel_model")
    @NotNull(message = "Model is required.")
    @Size(min=1, max = 30, message="Model must be at least 1 characters long and less than 30 characters.")
    @Column(name = "model", nullable = false)
    private String vesselModel;

    @JsonProperty("vessel_price")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive value.")
    @DecimalMax(value = "5000000", message = "Price must not exceed 5 000 000 euro.")
    @Column(name = "price", nullable = false)
    private BigDecimal vesselPrice;

    @JsonProperty("vessel_year")
    @NotNull(message = "Year is required")
    @Min(value = 1930, message = "Year must be no earlier than 1930.")
    @Max(value = ApplicationConstants.NEXT_YEAR,
            message = "Year must be no later than " + ApplicationConstants.NEXT_YEAR)
    @Column(name = "year", nullable = false)
    private int vesselYear;

    @JsonProperty("vessel_country")
    @NotNull(message = "Country is required.")
    @Size(min = 3, max = 20, message = "Country must be at least 3 characters long and less than 20 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Country must start with a capital letter and only contain letters.")
    @Column(name = "location_country", nullable = false)
    private String vesselLocationCountry;

    @JsonProperty("vessel_town")
    @NotNull(message = "Town is required.")
    @Size(min = 3, max = 30, message = "Town must be at least 3 characters long and less than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Town must start with a capital letter and only contain letters.")
    @Column(name = "location_state", nullable = false)
    private String vesselLocationState;

    @JsonProperty("vessel_loa")
    @NotNull(message = "Length overall is required.")
    @DecimalMin(value = "2.5", message = "Length overall must be at least 2.5 meters.")
    @DecimalMax(value = "300", message = "Length overall must not exceed 300 meters.")
    @Column(name = "loa", precision = 6, scale = 2, nullable = false)
    private BigDecimal vesselLengthOverall;

    @JsonProperty("vessel_beam")
    @NotNull(message="Beam width is required.")
    @DecimalMin(value = "1", message = "Beam width must be at least 1 meter.")
    @DecimalMax(value = "25", message = "Beam width must not exceed 25 meters.")
    @Column(name = "beam", precision = 6, scale = 2, nullable = false)
    private BigDecimal vesselBeam;

    @JsonProperty("vessel_draft")
    @NotNull(message = "Draft is required.")
    @DecimalMin(value = "0.3", message = "Draft depth must be at least 0.3 meter.")
    @DecimalMax(value = "16", message = "Draft depth must not exceed 16 meters.")
    @Column(name = "draft", precision = 6, scale = 2, nullable = false)
    private BigDecimal vesselDraft;

    @JsonProperty("vessel_cabin")
    @Min(value = 0, message = "Cabin count must be a non-negative number.")
    @Max(value = 10, message = "Cabin count must not exceed 10. Please provide a valid number of cabins.")
    @Column(name = "cabin", nullable = false)
    private int vesselCabin;

    @JsonProperty("vessel_berth")
    @Min(value = 0, message = "Berth count must be a non-negative number.")
    @Max(value = 20, message = "Berth count must not exceed 20. Please provide a valid number of berths.")
    @Column(name = "berth", nullable = false)
    private int vesselBerth;

    @JsonProperty("vessel_fuel_type")
    @Column(name = "fuel")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @JsonProperty("vessel_keel_type")
    @Column(name = "keel")
    @Enumerated(EnumType.STRING)
    private KeelType keelType;

    @JsonProperty("vessel_engine")
    @Min(value = 0, message = "Engine count must be zero or positive.")
    @Column(name = "engines", nullable = false)
    private int engineQuantity;

    @JsonProperty("vessel_description")
    @NotNull(message = "Description is required")
    @Size(min = 4, max = 5000, message = "Description must be between 4 and 5000 characters.")
    @Column(name = "description", nullable = false)
    private String vesselDescription;

    @JsonProperty("vessel_image_key")
    @Column(name = "image_key", nullable = false)
    private String imageKey;

    public Vessel() {
    }

    public Vessel(boolean featuredVessel, String vesselMake, String vesselModel, BigDecimal vesselPrice,
                  int vesselYear, String vesselLocationCountry, String vesselLocationState,
                  BigDecimal vesselLengthOverall, BigDecimal vesselBeam, BigDecimal vesselDraft, int vesselCabin,
                  int vesselBerth, KeelType keelType, FuelType fuelType,
                  int engineQuantity, String vesselDescription, String imageKey) {
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

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
