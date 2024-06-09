package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.model.Country;
import java.util.List;

public interface CountryService {
    Country saveCountry(CountryRequestDto dto);

    Country saveCountry(Country country);

    Country findId(Long id);

    List<Country> findAll();

    Country updateCountry(CountryRequestDto dto, Long id);

    void deleteById(Long id);

    Long getCountryIdByName(String name);
}
