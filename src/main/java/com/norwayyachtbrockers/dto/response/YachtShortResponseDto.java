package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@JsonPropertyOrder({"yacht_id", "yacht_featured", "yacht_top", "yacht_hot_price", "yacht_vat", "yacht_price",
        "yacht_price_old", "yacht_main_image_key"})
public class YachtShortResponseDto {

    @JsonProperty("yacht_id")
    private Long id;

    @JsonProperty("yacht_top")
    private boolean yachtTop;

    @JsonProperty("yacht_hot_price")
    private boolean hotPrice;

    @JsonProperty("yacht_vat")
    private boolean vatIncluded;

    @JsonProperty("yacht_price")
    private String price;

    @JsonProperty("yacht_price_old")
    private String priceOld;

    @JsonProperty("yacht_main_image_key")
    private String mainImageKey;

    @JsonProperty("yacht_make")
    private String make;

    @JsonProperty("yacht_model")
    private String model;

    @JsonProperty("yacht_year")
    private Integer year;

    @JsonProperty("yacht_country")
    private String country;

    @JsonProperty("yacht_town")
    private String town;

    @JsonProperty("yacht_created_at")
    private LocalDateTime createdAt;

    @JsonProperty("yacht_favourites")
    private Set<Long> favourites;

    @JsonProperty("yacht_favourites_count")
    private Integer favouritesCount;
}
