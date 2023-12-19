package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.model.YachtImage;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface YachtImageService {

    YachtImage findById(Long id);

    List<YachtImage> findAll();

    List<YachtImage> saveMultipleImages(List<MultipartFile> imageFiles);

    YachtImage update(Long id, MultipartFile imageFile);

    void delete(Long id);
}
