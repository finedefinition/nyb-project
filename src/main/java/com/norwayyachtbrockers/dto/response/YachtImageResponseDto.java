package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"yacht_image_id", "yacht_image_key", "yacht_id"})
public class YachtImageResponseDto {

    @JsonProperty("yacht_image_id")
    private Long id;

    @JsonProperty("yacht_image_key")
    private String imageKey;

    @JsonProperty("yacht_image_index")
    private Integer imageIndex;

    @JsonProperty("yacht_id")
    private Long yachtId;
}
