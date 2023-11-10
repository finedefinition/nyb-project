package com.norwayyachtbrockers.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BoatCreationDto {

    private String boatName;

    private BigDecimal boatPrice;

    private String boatBrand;

    private int boatYear;

    private String boatPlace;

}
