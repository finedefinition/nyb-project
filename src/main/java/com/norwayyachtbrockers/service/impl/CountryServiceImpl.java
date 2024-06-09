package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.CountryMapper;
import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.repository.CountryRepository;
import com.norwayyachtbrockers.service.CountryService;
import com.norwayyachtbrockers.util.EntityUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private  final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository countryRepository,
                              CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Override
    @Transactional
    public Country saveCountry(CountryRequestDto dto) {
        return countryRepository.save(countryMapper.createCountryFromDto(dto));
    }

    @Override
    @Transactional
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country findId(Long id) {
        return EntityUtils.findEntityOrThrow(id, countryRepository, "Country");
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    @Transactional
    public Country updateCountry(CountryRequestDto dto, Long id) {
        Country country = EntityUtils
                .findEntityOrThrow(id, countryRepository, "Country");
        countryMapper.updateCountryFromDto(country, dto);
        return countryRepository.save(country);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Country country = EntityUtils
                .findEntityOrThrow(id, countryRepository, "Country");
        country.getTowns().forEach(town -> town.setCountry(null));
        country.getYachts().forEach(yacht -> yacht.setCountry(null));
        countryRepository.delete(country);
    }

    @Override
    public Long getCountryIdByName(String name) {
        return countryRepository.findIdByName(name);
    }
}
