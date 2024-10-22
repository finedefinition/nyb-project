package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.model.Country;

public class CountryMapper {

    public static Country createCountryFromDto(CountryRequestDto dto) {
        Country country = new Country();
        country.setName(dto.getName().trim());
        return country;
    }

    public static Country updateCountryFromDto(Country country, CountryRequestDto dto) {
        country.setName(dto.getName().trim());
        return country;
    }
}
