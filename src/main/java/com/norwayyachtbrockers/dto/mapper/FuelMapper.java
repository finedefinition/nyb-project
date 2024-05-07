package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@RequiredArgsConstructor
public class FuelMapper {

    public Fuel createFuelFromDto(@Valid FuelRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Failed to create Fuel: DTO cannot be null");
        }
        Fuel fuel = new Fuel();
        fuel.setName(dto.getName().trim());
        return fuel;
    }

    public Fuel updateFuelFromDto(Fuel fuel, @Valid FuelRequestDto dto) {
        if (dto == null || fuel == null) {
            throw new IllegalArgumentException("Failed to update Fuel: Neither Fuel object nor DTO can be null.");
        }
        fuel.setName(dto.getName());
        return fuel;
    }
}