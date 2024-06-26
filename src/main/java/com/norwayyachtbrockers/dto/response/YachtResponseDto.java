package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.norwayyachtbrockers.model.YachtImage;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@JsonPropertyOrder({"yacht_id", "yacht_featured", "yacht_top", "yacht_hot_price", "yacht_vat", "yacht_price",
        "yacht_price_old", "yacht_main_image_key"})
public class YachtResponseDto {

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

    @JsonProperty("yacht_loa")
    private BigDecimal lengthOverall;

    @JsonProperty("yacht_beam")
    private BigDecimal beamWidth;

    @JsonProperty("yacht_draft")
    private BigDecimal draftDepth;

    @JsonProperty("yacht_keel_type")
    private String keelType;

    @JsonProperty("yacht_fuel_type")
    private String fuelType;

    @JsonProperty("yacht_country")
    private String country;

    @JsonProperty("yacht_town")
    private String town;

    @JsonProperty("yacht_images")
    private Set<YachtImage> yachtImages;

    @JsonProperty("yacht_cabin")
    private Integer cabin;

    @JsonProperty("yacht_berth")
    private Integer berth;

    @JsonProperty("yacht_heads")
    private Integer heads;

    @JsonProperty("yacht_shower")
    private Integer shower;

    @JsonProperty("yacht_description")
    private String description;

    @JsonProperty("yacht_owner_first_name")
    private String firstName;

    @JsonProperty("yacht_owner_last_name")
    private String lastName;

    @JsonProperty("yacht_owner_telephone")
    private String telephone;

    @JsonProperty("yacht_owner_email")
    private String email;

    @JsonProperty("yacht_created_at")
    private LocalDateTime createdAt;

    @JsonProperty("yacht_favourites")
    private Set<Long> favourites;

    @JsonProperty("yacht_favourites_count")
    private Integer favouritesCount;
}
