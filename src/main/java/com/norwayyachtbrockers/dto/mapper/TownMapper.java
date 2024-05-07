package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class TownMapper {

    private final CountryService countryService;

    public TownMapper(CountryService countryService) {
        this.countryService = countryService;
    }

    public Town createTownFromDto(@Valid TownRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Failed to create: DTO  Town cannot be null");
        }
        Town town = new Town();
        town.setName(dto.getName());
        town.setCountry(countryService.findId(dto.getCountryId()));
        return town;
    }

    public Town updateTownFromDto(Town town, @Valid TownRequestDto dto) {
        if (dto == null || town == null) {
            throw new IllegalArgumentException("Failed to update Town: Neither Town object nor DTO can be null.");
        }
        town.setName(dto.getName());
        town.setCountry(countryService.findId(dto.getCountryId()));
        return town;
    }

    public TownResponseDto convertToDto(Town town) {
        TownResponseDto dto = new TownResponseDto();
        dto.setTownId(town.getId());
        dto.setTownName(town.getName());
        dto.setCountryName(town.getCountry().getName());
        return dto;
    }
}
