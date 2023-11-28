package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.TownMapper;
import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.repository.CountryRepository;
import com.norwayyachtbrockers.repository.TownRepository;
import com.norwayyachtbrockers.service.TownService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final TownMapper townMapper;
    private final CountryRepository countryRepository;

    public TownServiceImpl(TownRepository townRepository,
                           TownMapper townMapper, CountryRepository countryRepository) {
        this.townRepository = townRepository;
        this.townMapper = townMapper;
        this.countryRepository = countryRepository;
    }

    @Override
    @Transactional
    public Town saveTown(TownRequestDto dto) {
        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot save the Town. Country with ID: %d not found", dto.getCountryId())));
        Town town = new Town();
        townMapper.updateTownFromDto(town, dto);

        town.setCountry(country);

        return townRepository.save(town);
    }

    @Override
    public Town findId(Long id) {
        return townRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Town with ID: %d not found", id)));
    }

    @Override
    public List<Town> findAll() {
        List<Town> towns = townRepository.findAll();
        towns.sort(Comparator.comparing(Town::getId));
        return towns;
    }

    @Override
    @Transactional
    public Town updateTown(TownRequestDto dto, Long id) {

        Town existingTown = townRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Town with ID: %d not found", id)));

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Country with ID: %d not found", dto.getCountryId())));

        townMapper.updateTownFromDto(existingTown, dto);

        existingTown.setCountry(country);

        return townRepository.save(existingTown);
    }

    @Override
    public void deleteById(Long id) {
        Town town = townRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot delete. Town with ID: %d not found", id)));
        // Detach related entities
        if (town.getCountry() != null) {
            town.getCountry().getTowns().remove(town);
        }

        townRepository.delete(town);
    }
}
