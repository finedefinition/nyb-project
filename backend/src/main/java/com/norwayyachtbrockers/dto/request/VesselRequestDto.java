package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VesselRequestDto {

    @JsonProperty("featured")
    private boolean featuredVessel;

    @JsonProperty("vessel_make")
    private String vesselMake;

    @JsonProperty("vessel_model")
    private String vesselModel;

    @JsonProperty("vessel_price")
    private BigDecimal vesselPrice;

    @JsonProperty("vessel_year")
    private int vesselYear;

    @JsonProperty("vessel_country")
    private String vesselLocationCountry;

    @JsonProperty("vessel_town")
    private String vesselLocationState;

    @JsonProperty("vessel_loa")
    private BigDecimal vesselLengthOverall;

    @JsonProperty("vessel_beam")
    private BigDecimal vesselBeam;

    @JsonProperty("vessel_draft")
    private BigDecimal vesselDraft;

    @JsonProperty("vessel_cabin")
    private int vesselCabin;

    @JsonProperty("vessel_berth")
    private int vesselBerth;

    @JsonProperty("vessel_fuel_type")
    private FuelType fuelType;

    @JsonProperty("vessel_keel_type")
    private KeelType keelType;

    @JsonProperty("vessel_engine")
    private int engineQuantity;

    @JsonProperty("vessel_description")
    private String vesselDescription;

}
