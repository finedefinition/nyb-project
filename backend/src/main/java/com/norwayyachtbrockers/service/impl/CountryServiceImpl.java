package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.CountryMapper;
import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.repository.CountryRepository;
import com.norwayyachtbrockers.service.CountryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
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

        Country country = new Country();
        countryMapper.updateCountryFromDto(country, dto);

        return countryRepository.save(country);
    }

    @Override
    public Country findId(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Country with ID: %d not found", id)));
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = countryRepository.findAll();
        countries.sort(Comparator.comparing(Country::getId));
        return countries;
    }

    @Override
    @Transactional
    public Country updateCountry(CountryRequestDto dto, Long id) {

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot update. The Country with ID: %d not found", id)));

        countryMapper.updateCountryFromDto(country, dto);

        return countryRepository.save(country);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot delete. The Country with ID: %d not found", id)));

       countryRepository.delete(country);
    }
}
