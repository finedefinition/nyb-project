package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FuelMapper {
    public void updateFuelFromDto(Fuel existingFuel, FuelRequestDto dto) {
        existingFuel.setName(dto.getName());
    }
}