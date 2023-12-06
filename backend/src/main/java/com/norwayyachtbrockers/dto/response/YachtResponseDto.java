package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norwayyachtbrockers.model.YachtImage;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class YachtResponseDto {

    @JsonProperty("yacht_id")
    private Long id;

    private boolean featured;

    private BigDecimal price;

    private String mainImageKey;

    private String make;

    private String model;

    private Integer year;

    private BigDecimal lengthOverall;

    private BigDecimal beamWidth;

    private BigDecimal draftDepth;

    private String keelType;

    private String fuelType;

    private String country;

    private String town;

//    private Set<YachtImage> yachtImages;

    private Integer cabin;

    private Integer berth;

    private Integer heads;

    private Integer shower;

    private String description;

    private String firstName;

    private String lastName;

    private String telephone;

    private String email;

    private LocalDateTime createdAt;
}
