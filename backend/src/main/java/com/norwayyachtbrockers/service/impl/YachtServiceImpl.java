package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtMapper;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YahctSearchParametersDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.repository.specification.yacht.YachtSpecificationBuilder;
import com.norwayyachtbrockers.service.YachtService;
import com.norwayyachtbrockers.util.EntityUtils;
import com.norwayyachtbrockers.util.S3ImageService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class YachtServiceImpl implements YachtService {

    private final YachtRepository yachtRepository;
    private final YachtMapper yachtMapper;
    private final YachtSpecificationBuilder yachtSpecificationBuilder;
    private final S3ImageService s3ImageService;

    public YachtServiceImpl(YachtRepository yachtRepository, YachtMapper yachtMapper,
                            YachtSpecificationBuilder yachtSpecificationBuilder, S3ImageService s3ImageService) {
        this.yachtRepository = yachtRepository;
        this.yachtMapper = yachtMapper;
        this.yachtSpecificationBuilder = yachtSpecificationBuilder;
        this.s3ImageService = s3ImageService;
    }

    @Override
    @Transactional
    public YachtResponseDto save(YachtRequestDto dto, MultipartFile mainImageFile,
                                 List<MultipartFile> additionalImageFiles) {
        // Convert DTO to Yacht entity
        Yacht yacht = yachtMapper.convertToYacht(dto);

        // Upload and set the main image key if it's provided
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            String mainImageKey = s3ImageService.uploadImageToS3(mainImageFile);
            yacht.setMainImageKey(mainImageKey);
        }

        // Save the Yacht entity to get a generated ID
        Yacht savedYacht = yachtRepository.save(yacht);

        // Save additional images if provided
        saveAdditionalImages(additionalImageFiles, savedYacht);

        return yachtMapper.convertToDto(savedYacht);
    }

    @Override
    @Transactional
    public YachtResponseDto save(YachtRequestDto dto, MultipartFile imageFile) {

        Yacht yacht = yachtMapper.convertToYacht(dto);
        setImageKeyForVessel(yacht, imageFile);
        yachtRepository.save(yacht);

        return yachtMapper.convertToDto(yacht);
    }

    @Override
    public YachtResponseDto findId(Long id) {
        Yacht yacht = EntityUtils.findEntityOrThrow(id, yachtRepository, "Yacht");
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
    public List<YachtResponseDto> search(YahctSearchParametersDto searchParametersDto) {
        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto);
        return yachtRepository.findAll(yachtSpecification).stream()
                .map(yachtMapper::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public YachtResponseDto update(YachtRequestDto dto, Long id, MultipartFile mainImageFile,
                                   List<MultipartFile> additionalImageFiles) {
        Yacht yacht = EntityUtils.findEntityOrThrow(id, yachtRepository, "Yacht");
        yachtMapper.updateYachtFromDto(yacht, dto);

        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            String mainImageKey = s3ImageService.uploadImageToS3(mainImageFile);
            yacht.setMainImageKey(mainImageKey);
        }

        // Optional: Handle removal of existing images
        // yacht.getYachtImages().clear();

        saveAdditionalImages(additionalImageFiles, yacht);

        yachtRepository.save(yacht);

        return yachtMapper.convertToDto(yacht);
    }

    private void saveAdditionalImages(List<MultipartFile> additionalImageFiles, Yacht yacht) {
        if (additionalImageFiles != null && !additionalImageFiles.isEmpty()) {
            for (MultipartFile file : additionalImageFiles) {
                if (file != null && !file.isEmpty()) {
                    YachtImage yachtImage = new YachtImage();
                    String imageKey = s3ImageService.uploadImageToS3(file);
                    yachtImage.setImageKey(imageKey);
                    yacht.addYachtImage(yachtImage); // Utilizing addYachtImage
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Yacht yacht = EntityUtils.findEntityOrThrow(id, yachtRepository, "Yacht");
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
