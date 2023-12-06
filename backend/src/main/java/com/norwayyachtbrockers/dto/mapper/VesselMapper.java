package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.model.Vessel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VesselMapper {

    public void updateVesselFromDto(Vessel existingVessel, VesselRequestDto vesselRequestDto) {
        existingVessel.setFeaturedVessel(vesselRequestDto.isFeaturedVessel());
        existingVessel.setVesselMake(vesselRequestDto.getVesselMake().trim());
        existingVessel.setVesselModel(vesselRequestDto.getVesselModel().trim());
        existingVessel.setVesselPrice(vesselRequestDto.getVesselPrice());
        existingVessel.setVesselYear(vesselRequestDto.getVesselYear());
        existingVessel.setVesselLocationCountry(vesselRequestDto.getVesselLocationCountry().trim());
        existingVessel.setVesselLocationState(vesselRequestDto.getVesselLocationState().trim());
        existingVessel.setVesselLengthOverall(vesselRequestDto.getVesselLengthOverall());
        existingVessel.setVesselBeam(vesselRequestDto.getVesselBeam());
        existingVessel.setVesselDraft(vesselRequestDto.getVesselDraft());
        existingVessel.setVesselCabin(vesselRequestDto.getVesselCabin());
        existingVessel.setVesselBerth(vesselRequestDto.getVesselBerth());
        existingVessel.setFuelType(vesselRequestDto.getFuelType());
        existingVessel.setKeelType(vesselRequestDto.getKeelType());
        existingVessel.setEngineQuantity(vesselRequestDto.getEngineQuantity());
        existingVessel.setVesselDescription(vesselRequestDto.getVesselDescription());
    }
}
