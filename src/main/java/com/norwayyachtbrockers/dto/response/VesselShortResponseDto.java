package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VesselShortResponseDto {

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

    @JsonProperty("vessel_image_key")
    private String imageUrl;

}
