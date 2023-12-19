package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class YachtRequestDto {

    private Long id;

    @JsonProperty("yacht_featured")
    private boolean featured;

    @JsonProperty("yacht_vat")
    private boolean vatIncluded;

    @JsonProperty("yacht_price")
    private BigDecimal price;

    @JsonProperty("yacht_model_id")
    private Long yachtModelId;

    @JsonProperty("yacht_country_id")
    private Long countryId;

    @JsonProperty("yacht_town_id")
    private Long townId;

    @JsonProperty("yacht_detail_id")
    private Long yachtDetailId;

    @JsonProperty("yacht_owner_info_id")
    private Long ownerInfoId;
}
