package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import java.util.List;

public interface FuelService {
    Fuel saveFuel(FuelRequestDto dto);

    Fuel findId(Long id);

    List<Fuel> findAll();

    Fuel updateFuel(FuelRequestDto dto, Long id);

    void deleteById(Long id);
}
