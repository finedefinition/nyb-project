package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.model.Town;

import java.util.List;

public interface TownService {
    TownResponseDto saveTown(TownRequestDto dto);

    Town saveTown(Town town);

    TownResponseDto findId(Long id);

    Town findTownById(Long id);

    List<TownResponseDto> findAll();

    TownResponseDto updateTown(TownRequestDto dto, Long id);

    void deleteById(Long id);

    Long getTownIdByName(String name);
}
