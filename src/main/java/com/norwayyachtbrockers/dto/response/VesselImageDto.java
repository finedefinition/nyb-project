package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VesselImageDto {

    @JsonProperty("yacht_image_id")
    private Long id;

    @JsonProperty("yacht_image_key")
    private String imageKey;

    public VesselImageDto(Long id, String imageKey) {
        this.id = id;
        this.imageKey = imageKey;
    }
}
