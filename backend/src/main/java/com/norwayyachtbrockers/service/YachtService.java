package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import java.util.List;

public interface YachtService {
    YachtResponseDto save(YachtRequestDto dto);

    YachtResponseDto findId(Long id);

    List<YachtResponseDto> findAll();

    YachtResponseDto update(YachtRequestDto dto, Long id);

    void deleteById(Long id);
}
