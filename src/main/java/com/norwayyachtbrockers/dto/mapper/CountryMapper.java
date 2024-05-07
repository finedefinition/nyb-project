package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.model.Country;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@RequiredArgsConstructor
public class CountryMapper {

    public Country createCountryFromDto(@Valid CountryRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Failed to create Country: DTO cannot be null");
        }
        Country country = new Country();
        country.setName(dto.getName().trim());
        return country;
    }

    public Country updateCountryFromDto(Country country, @Valid CountryRequestDto dto) {
        if (country == null || dto == null) {
            throw new IllegalArgumentException("Failed to update Country: Neither Country object nor DTO can be null.");
        }
        country.setName(dto.getName().trim());
        return country;
    }
}
