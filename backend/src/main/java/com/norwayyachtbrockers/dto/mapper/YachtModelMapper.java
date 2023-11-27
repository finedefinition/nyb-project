package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.FuelRepository;
import com.norwayyachtbrockers.repository.KeelRepository;
import org.springframework.stereotype.Component;

@Component
public class YachtModelMapper {
    private final KeelRepository keelRepository;
    private final FuelRepository fuelRepository;

    public YachtModelMapper(KeelRepository keelRepository, FuelRepository fuelRepository) {
        this.keelRepository = keelRepository;
        this.fuelRepository = fuelRepository;
    }

    public void updateYachtModelFromDto(YachtModel existingModel, YachtModelRequestDto yachtModelRequestDto) {
        existingModel.setMake(yachtModelRequestDto.getMake());
        existingModel.setModel(yachtModelRequestDto.getModel());
        existingModel.setYear(yachtModelRequestDto.getYear());
        existingModel.setLengthOverall(yachtModelRequestDto.getLengthOverall());
        existingModel.setBeamWidth(yachtModelRequestDto.getBeamWidth());
        existingModel.setDraftDepth(yachtModelRequestDto.getDraftDepth());
        existingModel.setFuelType(fuelRepository.getReferenceById(yachtModelRequestDto.getFuelTypeId()));
        existingModel.setKeelType(keelRepository.getReferenceById(yachtModelRequestDto.getKeelTypeId()));
    }
}
