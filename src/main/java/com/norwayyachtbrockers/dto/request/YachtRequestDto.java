package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class YachtRequestDto {

    private Long id;

    @JsonProperty(value = "yacht_vat", required = true)
    private boolean vatIncluded;

    @JsonProperty("yacht_price")
    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive value.")
    @DecimalMax(value = "5000000", message = "Price must not exceed 5 000 000 euro.")
    private BigDecimal price;

    @JsonProperty("yacht_model_id")
    @NotNull(message = "Yacht Model ID is required.")
    @Positive(message = "Yacht Model ID must be a positive value.")
    private Long yachtModelId;

    @JsonProperty("yacht_country_id")
    @NotNull(message = "Country ID is required.")
    @Positive(message = "Country ID must be a positive value.")
    private Long countryId;

    @JsonProperty("yacht_town_id")
    @NotNull(message = "Town ID is required.")
    @Positive(message = "Town ID must be a positive value.")
    private Long townId;

    @JsonProperty("yacht_detail_id")
    @NotNull(message = "Yacht Detail ID is required.")
    @Positive(message = "Yacht Detail ID must be a positive value.")
    private Long yachtDetailId;

    @JsonProperty("yacht_owner_info_id")
    @NotNull(message = "Owner Info ID is required.")
    @Positive(message = "Owner Info ID must be a positive value.")
    private Long ownerInfoId;
}
