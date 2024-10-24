package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;

public class FuelMapper {

    public static Fuel createFuelFromDto(FuelRequestDto dto) {
        Fuel fuel = new Fuel();
        fuel.setName(dto.getName().trim());
        return fuel;
    }

    public static Fuel updateFuelFromDto(Fuel fuel, FuelRequestDto dto) {
        fuel.setName(dto.getName());
        return fuel;
    }
}
