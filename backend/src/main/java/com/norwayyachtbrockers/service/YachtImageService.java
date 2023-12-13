package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.model.YachtImage;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface YachtImageService {

    YachtImage findById(Long id);

    List<YachtImage> findAll();

    YachtImage save(MultipartFile imageFile);

    YachtImage update(Long id, MultipartFile imageFile);

    void delete(Long id);
}
