package com.norwayyachtbrockers.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class YachtRequestDto {

    private Long id;

    private boolean featured;

    private BigDecimal price;

    private Long yachtModelId;

    private Long countryId;

    private Long townId;

    private Long yachtImageId;

    private Long yachtDetailId;

    private Long ownerInfoId;

}
