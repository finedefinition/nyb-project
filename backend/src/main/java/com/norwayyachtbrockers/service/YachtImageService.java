package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface YachtImageService {

    YachtImageResponseDto findById(Long id);

    List<YachtImageResponseDto> findAll();

    List<YachtImageResponseDto> saveMultipleImages(YachtImageRequestDto dto, List<MultipartFile> imageFiles);

    YachtImageResponseDto update(Long id, YachtImageRequestDto dto, MultipartFile imageFile);

    void delete(Long id);
}
