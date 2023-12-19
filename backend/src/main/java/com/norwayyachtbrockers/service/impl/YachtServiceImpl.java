package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtMapper;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.repository.YachtImageRepository;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.service.YachtService;
import com.norwayyachtbrockers.util.S3ImageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    private final YachtImageRepository yachtImageRepository;
    private final YachtMapper yachtMapper;
    private final S3ImageService s3ImageService;

    public YachtServiceImpl(YachtRepository yachtRepository, YachtImageRepository yachtImageRepository,
                            YachtMapper yachtMapper, S3ImageService s3ImageService) {
        this.yachtRepository = yachtRepository;
        this.yachtImageRepository = yachtImageRepository;
        this.yachtMapper = yachtMapper;
        this.s3ImageService = s3ImageService;
    }

    @Override
    @Transactional
    public YachtResponseDto save(YachtRequestDto dto, MultipartFile mainImageFile,
                                 List<MultipartFile> additionalImageFiles) {
        // Convert DTO to Yacht entity
        Yacht yacht = yachtMapper.convertToYacht(dto);
        yacht.setCreatedAt(LocalDateTime.now());

        // Upload and set the main image key if it's provided
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            String mainImageKey = s3ImageService.uploadImageToS3(mainImageFile);
            yacht.setMainImageKey(mainImageKey);
        }

        // Save the Yacht entity to get a generated ID
        Yacht savedYacht = yachtRepository.save(yacht);

        // Save additional images if provided
        if (additionalImageFiles != null && !additionalImageFiles.isEmpty()) {
            for (MultipartFile file : additionalImageFiles) {
                if (file != null && !file.isEmpty()) {
                    YachtImage yachtImage = new YachtImage();
                    String imageKey = s3ImageService.uploadImageToS3(file);
                    yachtImage.setImageKey(imageKey);
                    yachtImage.setYacht(savedYacht); // Set the yacht to the yachtImage
                    savedYacht.getYachtImages().add(yachtImage); // Add the yachtImage to the yacht's images collection
                    yachtImageRepository.save(yachtImage); // Save each yachtImage
                }
            }
        }

        return yachtMapper.convertToDto(savedYacht);
    }

    @Override
    @Transactional
    public YachtResponseDto save(YachtRequestDto dto, MultipartFile imageFile) {

        Yacht yacht = yachtMapper.convertToYacht(dto);
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
    @Transactional
    public YachtResponseDto update(YachtRequestDto dto, Long id, MultipartFile imageFile) {
        Yacht yacht = yachtRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Yacht with ID: %d not found", id)));
        yachtMapper.updateYachtFromDto(yacht,dto);
        setImageKeyForVessel(yacht, imageFile);
        yachtRepository.save(yacht);

        return yachtMapper.convertToDto(yacht);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Yacht yacht = yachtRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot delete. The Yacht with ID: %d not found", id)));
        if (yacht.getYachtModel() != null) {
            yacht.getYachtModel().getYachts().remove(yacht);
        }

        if (yacht.getCountry() != null) {
            yacht.getCountry().getYachts().remove(yacht);
        }

        if (yacht.getTown() != null) {
            yacht.getTown().getYachts().remove(yacht);
        }
        yachtRepository.delete(yacht);
    }

    private void setImageKeyForVessel(Yacht yacht, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageKey = s3ImageService.uploadImageToS3(imageFile);
            yacht.setMainImageKey(imageKey);
        }
    }
}
