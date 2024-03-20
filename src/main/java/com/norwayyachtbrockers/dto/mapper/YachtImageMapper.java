package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.model.YachtImage;
import org.springframework.stereotype.Component;

@Component
public class YachtImageMapper {

    public YachtImageResponseDto convertToResponseDto(YachtImage yachtImage) {
        YachtImageResponseDto dto = new YachtImageResponseDto();
        dto.setId(yachtImage.getId());
        dto.setImageKey(yachtImage.getImageKey());
        dto.setYachtId(yachtImage.getYacht().getId());
        return dto;
    }
}
