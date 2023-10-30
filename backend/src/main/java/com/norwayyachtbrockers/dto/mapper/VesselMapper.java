package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class VesselMapper {
    public VesselRequestDto toVesselRequestDto(boolean featuredVessel, String vesselMake,
                                               String vesselModel, BigDecimal vesselPrice,
                                               int vesselYear, String vesselLocationCountry, String vesselLocationState,
                                               BigDecimal vesselLengthOverall, BigDecimal vesselBeam,
                                               BigDecimal vesselDraft,
                                               int vesselCabin, int vesselBerth, KeelType keelType, FuelType fuelType,
                                               int engineQuantity, String vesselDescription) {
        VesselRequestDto vesselRequestDto = new VesselRequestDto();
        vesselRequestDto.setFeaturedVessel(featuredVessel);
        vesselRequestDto.setVesselMake(vesselMake);
        vesselRequestDto.setVesselModel(vesselModel);
        vesselRequestDto.setVesselPrice(vesselPrice);
        vesselRequestDto.setVesselYear(vesselYear);
        vesselRequestDto.setVesselLocationCountry(vesselLocationCountry);
        vesselRequestDto.setVesselLocationState(vesselLocationState);
        vesselRequestDto.setVesselLengthOverall(vesselLengthOverall);
        vesselRequestDto.setVesselBeam(vesselBeam);
        vesselRequestDto.setVesselDraft(vesselDraft);
        vesselRequestDto.setVesselCabin(vesselCabin);
        vesselRequestDto.setVesselBerth(vesselBerth);
        vesselRequestDto.setKeelType(keelType);
        vesselRequestDto.setFuelType(fuelType);
        vesselRequestDto.setEngineQuantity(engineQuantity);
        vesselRequestDto.setVesselDescription(vesselDescription);
        return vesselRequestDto;
    }

    public Vessel toVessel(VesselRequestDto vesselRequestDto){
        Vessel vessel = new Vessel();
        vessel.setFeaturedVessel(vesselRequestDto.isFeaturedVessel());
        vessel.setVesselMake(vesselRequestDto.getVesselMake());
        vessel.setVesselModel(vesselRequestDto.getVesselModel());
        vessel.setVesselPrice(vesselRequestDto.getVesselPrice());
        vessel.setVesselYear(vesselRequestDto.getVesselYear());
        vessel.setVesselLocationCountry(vesselRequestDto.getVesselLocationCountry());
        vessel.setVesselLocationState(vesselRequestDto.getVesselLocationState());
        vessel.setVesselLengthOverall(vesselRequestDto.getVesselLengthOverall());
        vessel.setVesselBeam(vesselRequestDto.getVesselBeam());
        vessel.setVesselDraft(vesselRequestDto.getVesselDraft());
        vessel.setVesselCabin(vesselRequestDto.getVesselCabin());
        vessel.setVesselBerth(vesselRequestDto.getVesselBerth());
        vessel.setFuelType(vesselRequestDto.getFuelType());
        vessel.setKeelType(vesselRequestDto.getKeelType());
        vessel.setEngineQuantity(vesselRequestDto.getEngineQuantity());
        vessel.setVesselDescription(vesselRequestDto.getVesselDescription());
        return vessel;
    }
    public void updateVesselFromDto(Vessel existingVessel, VesselRequestDto vesselRequestDto) {
        existingVessel.setFeaturedVessel(vesselRequestDto.isFeaturedVessel());
        existingVessel.setVesselMake(vesselRequestDto.getVesselMake());
        existingVessel.setVesselModel(vesselRequestDto.getVesselModel());
        existingVessel.setVesselPrice(vesselRequestDto.getVesselPrice());
        existingVessel.setVesselYear(vesselRequestDto.getVesselYear());
        existingVessel.setVesselLocationCountry(vesselRequestDto.getVesselLocationCountry());
        existingVessel.setVesselLocationState(vesselRequestDto.getVesselLocationState());
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
