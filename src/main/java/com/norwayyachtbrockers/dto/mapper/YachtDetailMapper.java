package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import org.springframework.stereotype.Component;

@Component
public class YachtDetailMapper {
    public void updateFromDto(YachtDetail yachtDetail, YachtDetailRequestDto dto) {
        yachtDetail.setCabin(dto.getCabin());
        yachtDetail.setBerth(dto.getBerth());
        yachtDetail.setHeads(dto.getHeads());
        yachtDetail.setShower(dto.getShower());
        yachtDetail.setDescription(dto.getDescription());
    }
}
