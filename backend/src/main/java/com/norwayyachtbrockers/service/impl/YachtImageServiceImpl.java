package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtImageMapper;
import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.repository.YachtImageRepository;
import com.norwayyachtbrockers.service.YachtImageService;
import com.norwayyachtbrockers.util.S3ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class YachtImageServiceImpl implements YachtImageService {

    private final YachtImageRepository yachtImageRepository;

    private final YachtImageMapper yachtImageMapper;

    private final S3ImageService s3ImageService;

    public YachtImageServiceImpl(YachtImageRepository yachtImageRepository,
                                 YachtImageMapper yachtImageMapper, S3ImageService s3ImageService) {
        this.yachtImageRepository = yachtImageRepository;
        this.yachtImageMapper = yachtImageMapper;
        this.s3ImageService = s3ImageService;
    }

    @Override
    @Transactional
    public YachtImage save(YachtImageRequestDto dto, MultipartFile imageFile) {
        YachtImage yachtImage = new YachtImage();
        yachtImageMapper.updateFromDto(yachtImage, dto);
        setImageKey(yachtImage, imageFile);
        return yachtImageRepository.save(yachtImage);
    }

    @Override
    public YachtImage findById(Long id) {
        return yachtImageRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot find the Yacht Image with ID: %d", id)));
    }

    @Override
    public List<YachtImage> findAll() {
        return yachtImageRepository.findAll();
    }

    @Override
    @Transactional
    public YachtImage update(Long id, YachtImageRequestDto dto, MultipartFile imageFile) {
        YachtImage yachtImage = yachtImageRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot find the Yacht Image with ID: %d", id)));
        yachtImageMapper.updateFromDto(yachtImage, dto);
        setImageKey(yachtImage, imageFile);
        return yachtImageRepository.save(yachtImage);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        YachtImage yachtImage = yachtImageRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot find the Yacht Image with ID: %d", id)));
        yachtImageRepository.delete(yachtImage);

    }

    private void setImageKey(YachtImage yachtImage, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageKey = s3ImageService.uploadImageToS3(imageFile);
            yachtImage.setImageKey(imageKey);
        }
    }
}
