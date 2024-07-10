package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"yacht_id", "yacht_main_image_key", "yacht_make", "yacht_model", "yacht_country", "yacht_town",
"yacht_owner_first_name", "yacht_owner_last_name", "yacht_owner_telephone", "yacht_owner_email", "yacht_created_at"})
public class YachtCrmResponseDto {

    @JsonProperty("yacht_id")
    private Long id;

    @JsonProperty("yacht_main_image_key")
    private String mainImageKey;

    @JsonProperty("yacht_make")
    private String make;

    @JsonProperty("yacht_model")
    private String model;

    @JsonProperty("yacht_country")
    private String country;

    @JsonProperty("yacht_town")
    private String town;

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
}
