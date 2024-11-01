package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.repository.projections.FuelProjection;

import java.util.List;

public interface FuelService {
    Fuel saveFuel(FuelRequestDto dto);

    Fuel saveFuel(Fuel fuel);

    Fuel findId(Long id);

    List<FuelProjection> findAll();

    Fuel updateFuel(FuelRequestDto dto, Long id);

    void deleteById(Long id);

    Long getFuelTypeIdByName(String name);
}
