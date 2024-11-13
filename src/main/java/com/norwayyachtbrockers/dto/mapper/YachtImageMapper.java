package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.repository.projections.YachtImageProjection;

public class YachtImageMapper {

    public static YachtImageResponseDto convertToResponseDto(YachtImage yachtImage) {
        YachtImageResponseDto dto = new YachtImageResponseDto();
        dto.setId(yachtImage.getId());
        dto.setImageKey(yachtImage.getImageKey());
        dto.setImageIndex(yachtImage.getImageIndex());
        dto.setYachtId(yachtImage.getYacht().getId());
        return dto;
    }

    public static YachtImageResponseDto convertToResponseDto(YachtImageProjection yachtImage) {
        YachtImageResponseDto dto = new YachtImageResponseDto();
        dto.setId(yachtImage.getId());
        dto.setImageKey(yachtImage.getImageKey());
        dto.setImageIndex(yachtImage.getImageIndex());
        dto.setYachtId(yachtImage.getYachtId());
        return dto;
    }
}
