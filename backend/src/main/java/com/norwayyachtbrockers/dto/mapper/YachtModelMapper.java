package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.service.FuelService;
import com.norwayyachtbrockers.service.KeelService;
import org.springframework.stereotype.Component;

@Component
public class YachtModelMapper {
    private final KeelService keelService;
    private final FuelService fuelService;

    public YachtModelMapper(KeelService keelService, FuelService fuelService) {
        this.keelService = keelService;
        this.fuelService = fuelService;
    }

    public void updateYachtModelFromDto(YachtModel existingModel, YachtModelRequestDto dto) {
        existingModel.setMake(dto.getMake());
        existingModel.setModel(dto.getModel());
        existingModel.setYear(dto.getYear());
        existingModel.setLengthOverall(dto.getLengthOverall());
        existingModel.setBeamWidth(dto.getBeamWidth());
        existingModel.setDraftDepth(dto.getDraftDepth());
        existingModel.setFuelType(fuelService.findId(dto.getFuelTypeId()));
        existingModel.setKeelType(keelService.findId(dto.getKeelTypeId()));
    }
}
