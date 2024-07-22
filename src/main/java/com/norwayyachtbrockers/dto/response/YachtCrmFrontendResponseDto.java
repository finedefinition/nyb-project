package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class YachtCrmFrontendResponseDto {

    @JsonProperty("yacht_model_make")
    private Map<String, List<String>> makeToModel;

    @JsonProperty("yacht_model_keel_type")
    private List<String> keelType;

    @JsonProperty("yacht_model_fuel_type")
    private List<String> fuelType;

    @JsonProperty("yacht_country")
    private Map<String, List<String>> countryToTown;

    @JsonProperty("first_name")
    private List<String> firstName;

    @JsonProperty("last_name")
    private List<String> lastName;
}

