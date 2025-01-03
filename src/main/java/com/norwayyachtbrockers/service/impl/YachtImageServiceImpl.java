package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtImageMapper;
import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.repository.YachtImageRepository;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.repository.projections.YachtImageProjection;
import com.norwayyachtbrockers.service.YachtImageService;
import com.norwayyachtbrockers.util.EntityUtils;
import com.norwayyachtbrockers.util.S3ImageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class YachtImageServiceImpl implements YachtImageService {

    private final YachtImageRepository yachtImageRepository;

    private final YachtRepository yachtRepository;

    private final S3ImageService s3ImageService;

    public YachtImageServiceImpl(YachtImageRepository yachtImageRepository, YachtRepository yachtRepository,
                                 S3ImageService s3ImageService) {
        this.yachtImageRepository = yachtImageRepository;
        this.yachtRepository = yachtRepository;
        this.s3ImageService = s3ImageService;
    }

    @Override
    @Transactional
    public List<YachtImageResponseDto> saveMultipleImages(YachtImageRequestDto dto, List<MultipartFile> imageFiles) {
        List<YachtImage> savedImages = new ArrayList<>();

        Yacht yacht = EntityUtils.findEntityOrThrow(dto.getYachtId(), yachtRepository, "Yacht");

        // Get current indexes and sort them
        List<Integer> existingIndexes = yacht.getYachtImages().stream()
                .map(YachtImage::getImageIndex)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        int nextIndex = 1; // Start with the first index

        for (MultipartFile file : imageFiles) {
            if (file != null && !file.isEmpty()) {
                YachtImage yachtImage = new YachtImage();
                String imageKey = s3ImageService.uploadImageToS3(file);
                yachtImage.setImageKey(imageKey);
                yachtImage.setYacht(yacht);

                // Find the next available index
                while (existingIndexes.contains(nextIndex)) {
                    nextIndex++;
                }

                yachtImage.setImageIndex(nextIndex);
                // Ensure the next iteration considers this index as taken
                existingIndexes.add(nextIndex);
                savedImages.add(yachtImageRepository.save(yachtImage));
            }
        }

        return savedImages.stream()
                .map(YachtImageMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public YachtImageResponseDto findById(Long id) {
        YachtImage yachtImage = EntityUtils.findEntityOrThrow(id, yachtImageRepository, "YachtImage");
        return YachtImageMapper.convertToResponseDto(yachtImage);
    }

    @Override
    public List<YachtImageResponseDto> findAll() {
        List<YachtImageProjection> yachtImages = yachtImageRepository.findAllProjections();
        return yachtImages.stream()
                .map(YachtImageMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public YachtImageResponseDto update(Long id, YachtImageRequestDto dto, MultipartFile imageFile) {
        YachtImage yachtImage = EntityUtils.findEntityOrThrow(id, yachtImageRepository, "YachtImage");

        // Update the YachtImage with details from the DTO
        if (dto.getYachtId() != null) {
            Yacht yacht = EntityUtils.findEntityOrThrow(dto.getYachtId(), yachtRepository, "Yacht");
            yachtImage.setYacht(yacht);
        }
        setImageKey(yachtImage, imageFile);

        // Save the updated YachtImage
        YachtImage savedYachtImage = yachtImageRepository.save(yachtImage);

        // Convert the saved YachtImage to YachtImageResponseDto
        return YachtImageMapper.convertToResponseDto(savedYachtImage);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        YachtImage yachtImage = EntityUtils.findEntityOrThrow(id, yachtImageRepository, "YachtImage");
        yachtImageRepository.delete(yachtImage);

    }

    private void setImageKey(YachtImage yachtImage, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageKey = s3ImageService.uploadImageToS3(imageFile);
            yachtImage.setImageKey(imageKey);
        }
    }
}
