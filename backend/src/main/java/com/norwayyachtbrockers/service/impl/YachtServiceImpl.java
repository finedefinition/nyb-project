package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtMapper;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.service.YachtService;
import com.norwayyachtbrockers.util.S3ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YachtServiceImpl implements YachtService {

    private final YachtRepository yachtRepository;
    private final YachtMapper yachtMapper;
    private final S3ImageService s3ImageService;

    public YachtServiceImpl(YachtRepository yachtRepository,
                            YachtMapper yachtMapper, S3ImageService s3ImageService) {
        this.yachtRepository = yachtRepository;
        this.yachtMapper = yachtMapper;
        this.s3ImageService = s3ImageService;
    }

    @Override
    @Transactional
    public YachtResponseDto save(YachtRequestDto dto, MultipartFile imageFile) {
        Yacht yacht = new Yacht();
        yachtMapper.updateFromDto(yacht, dto);
        yacht.setCreatedAt(LocalDateTime.now());
        setImageKeyForVessel(yacht, imageFile);
        yachtRepository.save(yacht);

        return yachtMapper.convertToDto(yacht);
    }

    @Override
    public YachtResponseDto findId(Long id) {
        Yacht yacht = yachtRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Yacht with ID: %d not found", id)));
        return yachtMapper.convertToDto(yacht);
    }

    @Override
    public List<YachtResponseDto> findAll() {
        return yachtRepository.findAll().stream()
                .sorted(Comparator.comparing(Yacht::getId))
                .map(yachtMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public YachtResponseDto update(YachtRequestDto dto, Long id, MultipartFile imageFile) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Yacht yacht = yachtRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot delete. The Yacht with ID: %d not found", id)));

        yachtRepository.delete(yacht);
    }

    private void setImageKeyForVessel(Yacht yacht, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageKey = s3ImageService.uploadImageToS3(imageFile);
            yacht.setMainImageKey(imageKey);
        }
    }
}
