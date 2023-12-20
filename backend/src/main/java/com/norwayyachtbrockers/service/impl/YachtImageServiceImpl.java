package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.repository.YachtImageRepository;
import com.norwayyachtbrockers.service.YachtImageService;
import com.norwayyachtbrockers.util.EntityUtils;
import com.norwayyachtbrockers.util.S3ImageService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class YachtImageServiceImpl implements YachtImageService {

    private final YachtImageRepository yachtImageRepository;

    private final S3ImageService s3ImageService;

    public YachtImageServiceImpl(YachtImageRepository yachtImageRepository, S3ImageService s3ImageService) {
        this.yachtImageRepository = yachtImageRepository;
        this.s3ImageService = s3ImageService;
    }

    @Override
    @Transactional
    public List<YachtImage> saveMultipleImages(List<MultipartFile> imageFiles) {
        List<YachtImage> savedImages = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            if (file != null && !file.isEmpty()) {
                YachtImage yachtImage = new YachtImage();
                String imageKey = s3ImageService.uploadImageToS3(file);
                yachtImage.setImageKey(imageKey);
                savedImages.add(yachtImageRepository.save(yachtImage));
            }
        }
        return savedImages;
    }

    @Override
    public YachtImage findById(Long id) {
        return EntityUtils.findEntityOrThrow(id, yachtImageRepository, "YachtImage");
    }

    @Override
    public List<YachtImage> findAll() {
        return yachtImageRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    @Transactional
    public YachtImage update(Long id,  MultipartFile imageFile) {
        YachtImage yachtImage = EntityUtils.findEntityOrThrow(id, yachtImageRepository, "YachtImage");
        setImageKey(yachtImage, imageFile);
        return yachtImageRepository.save(yachtImage);
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
