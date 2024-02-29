package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TownResponseDto {

    @JsonProperty("town_id")
    private Long townId;

    @JsonProperty("town_name")
    private String townName;

    @JsonProperty("town_country_name")
    private String countryName;

}
