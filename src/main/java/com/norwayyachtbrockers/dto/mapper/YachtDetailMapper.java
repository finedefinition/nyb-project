package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class YachtDetailMapper {
    public YachtDetail createYachtDetailFromDto(@Valid YachtDetailRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Failed to create YachtDetail: DTO cannot be null");
        }
        YachtDetail yachtDetail = new YachtDetail();
        yachtDetail.setCabin(dto.getCabin());
        yachtDetail.setBerth(dto.getBerth());
        yachtDetail.setHeads(dto.getHeads());
        yachtDetail.setShower(dto.getShower());
        yachtDetail.setDescription(dto.getDescription());
        return yachtDetail;
    }
    public YachtDetail updateYachtDetailFromDto(YachtDetail yachtDetail, @Valid YachtDetailRequestDto dto) {
        if (dto == null || yachtDetail == null) {
            throw new IllegalArgumentException("Failed to update YachtDetail:" +
                    " Neither YachtDetail object nor DTO can be null.");
        }
        yachtDetail.setCabin(dto.getCabin());
        yachtDetail.setBerth(dto.getBerth());
        yachtDetail.setHeads(dto.getHeads());
        yachtDetail.setShower(dto.getShower());
        yachtDetail.setDescription(dto.getDescription());
        return yachtDetail;
    }
}
