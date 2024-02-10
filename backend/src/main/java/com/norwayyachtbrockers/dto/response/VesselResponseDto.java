package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VesselResponseDto {

    @JsonProperty("vessel_id")
    private Long id;

    @JsonProperty("featured")
    private boolean featured;

    @JsonProperty("vessel_make")
    private String make;

    @JsonProperty("vessel_model")
    private String model;

    @JsonProperty("vessel_price_EUR")
    private String price;

    @JsonProperty("vessel_price_USD")
    private String priceUSD;

    @JsonProperty("vessel_price_GBP")
    private String priceGBP;

    @JsonProperty("vessel_price_NOK")
    private String priceNOK;

    @JsonProperty("vessel_year")
    private int year;

    @JsonProperty("vessel_country")
    private String country;

    @JsonProperty("vessel_town")
    private String state;

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
    private String fuelType;

    @JsonProperty("vessel_keel_type")
    private String keelType;

    @JsonProperty("vessel_engine")
    private int engineQuantity;

    @JsonProperty("vessel_description")
    private String vesselDescription;

    @JsonProperty("vessel_created_at")
    private LocalDateTime createdAt;

    @JsonProperty("vessel_image_key")
    private String imageUrl;

    @JsonProperty("vessel_images")
    private List<VesselImageDto> vesselImages;

}
