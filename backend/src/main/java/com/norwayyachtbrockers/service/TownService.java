package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.model.Town;
import java.util.List;

public interface TownService {
    Town saveTown(TownRequestDto dto);

    Town findId(Long id);

    List<Town> findAll();

    Town updateTown(TownRequestDto dto, Long id);

    void deleteById(Long id);
}
