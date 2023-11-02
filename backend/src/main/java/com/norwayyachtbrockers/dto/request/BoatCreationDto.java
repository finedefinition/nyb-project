package com.norwayyachtbrockers.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BoatCreationDto {

    private String boatName;

    private BigDecimal boatPrice;

    private String boatBrand;

    private int boatYear;

    private String boatPlace;

    private String firstName;

    private String lastName;

    private String telephone;

    private String email;

}
