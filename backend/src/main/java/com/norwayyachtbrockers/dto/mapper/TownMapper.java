package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.service.CountryService;
import org.springframework.stereotype.Component;

@Component
public class TownMapper {
    private final CountryService countryService;

    public TownMapper(CountryService countryService) {
        this.countryService = countryService;
    }

    public void updateTownFromDto(Town existingTown, TownRequestDto dto) {
        existingTown.setName(dto.getName());
        existingTown.setCountry(countryService.findId(dto.getCountryId()));
    }
}
