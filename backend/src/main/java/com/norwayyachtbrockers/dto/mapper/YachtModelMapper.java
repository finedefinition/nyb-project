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

    public void updateYachtModelFromDto(YachtModel existingModel, YachtModelRequestDto yachtModelRequestDto) {
        existingModel.setMake(yachtModelRequestDto.getMake());
        existingModel.setModel(yachtModelRequestDto.getModel());
        existingModel.setYear(yachtModelRequestDto.getYear());
        existingModel.setLengthOverall(yachtModelRequestDto.getLengthOverall());
        existingModel.setBeamWidth(yachtModelRequestDto.getBeamWidth());
        existingModel.setDraftDepth(yachtModelRequestDto.getDraftDepth());
        existingModel.setFuelType(fuelService.findId(yachtModelRequestDto.getFuelTypeId()));
        existingModel.setKeelType(keelService.findId(yachtModelRequestDto.getKeelTypeId()));
    }
}
