package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.service.YachtService;
import org.springframework.stereotype.Component;

@Component
public class YachtImageMapper {

    private final YachtService yachtService;
    private final YachtMapper yachtMapper;

    public YachtImageMapper(YachtService yachtService, YachtMapper yachtMapper) {
        this.yachtService = yachtService;
        this.yachtMapper = yachtMapper;
    }

    public YachtImageResponseDto convertToResponseDto(YachtImage yachtImage) {
        YachtImageResponseDto dto = new YachtImageResponseDto();
        dto.setId(yachtImage.getId());
        dto.setImageKey(yachtImage.getImageKey());
        dto.setYachtId(yachtImage.getYacht().getId());
        return dto;
    }
}
