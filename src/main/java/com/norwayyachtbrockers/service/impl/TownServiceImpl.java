package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.TownMapper;
import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.repository.CountryRepository;
import com.norwayyachtbrockers.repository.TownRepository;
import com.norwayyachtbrockers.service.TownService;
import com.norwayyachtbrockers.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public TownResponseDto saveTown(TownRequestDto dto) {
        Country country = EntityUtils
                .findEntityOrThrow(dto.getCountryId(), countryRepository, "Country");
        Town town = townMapper.createTownFromDto(dto);
        town.setCountry(country);
        townRepository.save(town);
        return townMapper.convertToDto(town);
    }

    @Override
    public TownResponseDto findId(Long id) {
        Town town = EntityUtils.findEntityOrThrow(id, townRepository, "Town");
        return townMapper.convertToDto(town);
    }

    @Override
    public Town findTownById(Long id) {
        return EntityUtils.findEntityOrThrow(id, townRepository, "Town");
    }

    @Override
    public List<TownResponseDto> findAll() {
        return townRepository.findAll().stream()
                .sorted(Comparator.comparing(Town::getId))
                .map(townMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TownResponseDto updateTown(TownRequestDto dto, Long id) {
        Town existingTown = EntityUtils.findEntityOrThrow(id, townRepository, "Town");
        Country country = EntityUtils
                .findEntityOrThrow(dto.getCountryId(), countryRepository, "Country");
        townMapper.updateTownFromDto(existingTown, dto);
        existingTown.setCountry(country);
        return townMapper.convertToDto(existingTown);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Town town = EntityUtils.findEntityOrThrow(id, townRepository, "Town");

        // Detach the town from its country
        if (town.getCountry() != null) {
            town.getCountry().getTowns().remove(town);
        }

        // Remove yachts associated with the town
        if (town.getYachts() != null) {
            town.getYachts().forEach(yacht -> yacht.setTown(null));
        }

        // Delete the town
        townRepository.delete(town);
    }


    @Override
    public Long getTownIdByName(String name) {
        return townRepository.findIdByName(name);
    }
}
