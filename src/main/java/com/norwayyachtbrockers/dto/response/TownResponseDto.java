package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TownResponseDto {

    @JsonProperty("town_id")
    private Long townId;

    @JsonProperty("town_name")
    private String townName;

    @JsonProperty("town_country_name")
    private String countryName;
}
