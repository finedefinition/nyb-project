package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VesselShortResponseDto {

    @JsonProperty("vessel_id")
    private Long id;

    @JsonProperty("featured")
    private boolean featured;

    @JsonProperty("vessel_make")
    private String make;

    @JsonProperty("vessel_model")
    private String model;

    @JsonProperty("vessel_price")
    private BigDecimal price;

    @JsonProperty("vessel_year")
    private int year;

    @JsonProperty("vessel_country")
    private String country;

    @JsonProperty("vessel_town")
    private String state;

    @JsonProperty("vessel_image_key")
    private String imageUrl;

}
