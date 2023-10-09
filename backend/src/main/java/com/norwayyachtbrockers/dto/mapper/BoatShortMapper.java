package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.BoatShortResponseDto;
import com.norwayyachtbrockers.model.Boat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoatShortMapper {
    public BoatShortResponseDto toBoatShortResponseDto(Boat boat) {
        BoatShortResponseDto dto = new BoatShortResponseDto();
        dto.setId(boat.getId());
        dto.setName(boat.getBoatBrand());
        dto.setPlace(boat.getBoatPlace());
        dto.setPrice(boat.getBoatPrice());
        dto.setYear(boat.getBoatYear());
        dto.setImageUrl(boat.getImageKey());
        return dto;

    }
}
