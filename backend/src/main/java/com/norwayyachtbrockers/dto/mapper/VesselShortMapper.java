package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.VesselShortResponseDto;
import com.norwayyachtbrockers.model.Vessel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VesselShortMapper {
    public VesselShortResponseDto toVesselShortResponseDto(Vessel vessel) {
        VesselShortResponseDto dto = new VesselShortResponseDto();
        dto.setId(vessel.getId());
        dto.setFeatured(vessel.isFeaturedVessel());
        dto.setMake(vessel.getVesselMake());
        dto.setModel(vessel.getVesselModel());
        dto.setPrice(vessel.getVesselPrice());
        dto.setYear(vessel.getVesselYear());
        dto.setCountry(vessel.getVesselLocationCountry());
        dto.setState(vessel.getVesselLocationState());
        dto.setImageUrl(vessel.getImageKey());

        return dto;
    }
}
