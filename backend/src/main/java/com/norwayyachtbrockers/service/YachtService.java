package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YahctSearchParametersDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface YachtService {

    YachtResponseDto save(YachtRequestDto dto, MultipartFile mainImageFile,
                          List<MultipartFile> additionalImageFiles);
    YachtResponseDto save(YachtRequestDto dto, MultipartFile imageFile);

    YachtResponseDto findId(Long id);

    List<YachtResponseDto> findAll();

    List<YachtResponseDto> search(YahctSearchParametersDto searchParametersDto);

//    YachtResponseDto update(YachtRequestDto dto, Long id, MultipartFile imageFile);

    YachtResponseDto update(YachtRequestDto dto, Long id, MultipartFile mainImageFile,
                            List<MultipartFile> additionalImageFiles);

    void deleteById(Long id);
}
