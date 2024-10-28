package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;

public class YachtDetailMapper {
    public static YachtDetail createYachtDetailFromDto(YachtDetailRequestDto dto) {
        YachtDetail yachtDetail = new YachtDetail();
        yachtDetail.setCabin(dto.getCabin());
        yachtDetail.setBerth(dto.getBerth());
        yachtDetail.setHeads(dto.getHeads());
        yachtDetail.setShower(dto.getShower());
        yachtDetail.setDescription(dto.getDescription());
        return yachtDetail;
    }

    public static YachtDetail updateYachtDetailFromDto(YachtDetail yachtDetail, YachtDetailRequestDto dto) {
        yachtDetail.setCabin(dto.getCabin());
        yachtDetail.setBerth(dto.getBerth());
        yachtDetail.setHeads(dto.getHeads());
        yachtDetail.setShower(dto.getShower());
        yachtDetail.setDescription(dto.getDescription());
        return yachtDetail;
    }
}
