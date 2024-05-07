package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.service.FuelService;
import com.norwayyachtbrockers.service.KeelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@AllArgsConstructor
public class YachtModelMapper {
    private final KeelService keelService;
    private final FuelService fuelService;

    public YachtModel createYachtModelFromDto(YachtModelRequestDto dto) {
        YachtModel yachtModel = new YachtModel();
        return setFields(yachtModel, dto);
    }

    public YachtModel updateYachtModelFromDto(YachtModel yachtModel, YachtModelRequestDto dto) {
        return setFields(yachtModel, dto);
    }

    private YachtModel setFields(YachtModel yachtModel, YachtModelRequestDto dto) {
        yachtModel.setMake(dto.getMake());
        yachtModel.setModel(dto.getModel());
        yachtModel.setYear(dto.getYear());
        yachtModel.setLengthOverall(dto.getLengthOverall());
        yachtModel.setBeamWidth(dto.getBeamWidth());
        yachtModel.setDraftDepth(dto.getDraftDepth());
        yachtModel.setFuelType(fuelService.findId(dto.getFuelTypeId()));
        yachtModel.setKeelType(keelService.findId(dto.getKeelTypeId()));
        return yachtModel;
    }
}
