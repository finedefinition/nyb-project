package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface YachtService {
    YachtResponseDto save(YachtRequestDto dto, MultipartFile imageFile);

    YachtResponseDto findId(Long id);

    List<YachtResponseDto> findAll();

    YachtResponseDto update(YachtRequestDto dto, Long id, MultipartFile imageFile);

    void deleteById(Long id);
}
