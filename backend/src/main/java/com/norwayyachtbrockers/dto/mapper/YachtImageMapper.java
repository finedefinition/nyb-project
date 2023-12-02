package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
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

    public void updateFromDto(YachtImage yachtImage, YachtImageRequestDto dto) {
        yachtImage.setImageKey(dto.getImageKey());
//        YachtResponseDto yachtResponseDto = yachtService.findId(dto.getYachtId());
//        Yacht yacht = new Yacht();
//        yachtMapper.updateFromDto(yachtResponseDto, yacht);
//        yachtImage.setYacht(yachtMapper.updateFromDto(yachtResponseDto);
    }
}
