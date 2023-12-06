package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.BoatCreationDto;
import com.norwayyachtbrockers.model.Boat;
import org.springframework.stereotype.Component;

@Component
public class BoatCreationMapper {
    public void mapDtoToEntity(BoatCreationDto dto, Boat boat) {
        // Map fields from DTO to Boat entity
        boat.setBoatName(dto.getBoatName());
        boat.setBoatPrice(dto.getBoatPrice());
        boat.setBoatBrand(dto.getBoatBrand());
        boat.setBoatYear(dto.getBoatYear());
        boat.setBoatPlace(dto.getBoatPlace());
    }
}
