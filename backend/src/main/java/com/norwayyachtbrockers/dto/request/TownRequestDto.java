package com.norwayyachtbrockers.dto.request;

import com.norwayyachtbrockers.model.Country;
import lombok.Data;

@Data
public class TownRequestDto {

    private Long id;

    private String name;

    private Long countryId;
}
