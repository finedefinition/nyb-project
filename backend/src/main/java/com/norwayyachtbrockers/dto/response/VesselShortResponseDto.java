package com.norwayyachtbrockers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VesselShortResponseDto {
    private Long id;
    private boolean featured;
    private String make;
    private String model;
    private BigDecimal price;
    private int year;
    private String country;
    private String state;
    private String imageUrl;

}
