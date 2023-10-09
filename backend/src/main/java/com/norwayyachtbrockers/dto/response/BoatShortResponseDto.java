package com.norwayyachtbrockers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoatShortResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String place;
    private int year;
    private String imageUrl;
}
