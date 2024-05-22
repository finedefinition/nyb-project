package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtMapper;
import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.YachtImageRepository;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.repository.specification.yacht.YachtSpecificationBuilder;
import com.norwayyachtbrockers.service.YachtImageService;
import com.norwayyachtbrockers.service.YachtService;
import com.norwayyachtbrockers.util.EntityUtils;
import com.norwayyachtbrockers.util.S3ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class YachtServiceImpl implements YachtService {

    private final YachtRepository yachtRepository;
    private final YachtImageRepository yachtImageRepository;
    private final YachtImageService yachtImageService;
    private final YachtMapper yachtMapper;
    private final YachtSpecificationBuilder yachtSpecificationBuilder;
    private final S3ImageService s3ImageService;

    @Override
    @Transactional
    public YachtResponseDto save(YachtRequestDto dto, MultipartFile mainImageFile,
                                 List<MultipartFile> additionalImageFiles) {
        // Convert DTO to Yacht entity
        Yacht yacht = yachtMapper.createYachtFromDto(dto);

        // Set the old price from the DTO
        yacht.setPriceOld(yacht.getPrice());

        // Initially assume no main image is set
        boolean mainImageSet = false;

        // Upload and set the main image key if it's provided
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            String mainImageKey = s3ImageService.uploadImageToS3(mainImageFile);
            yacht.setMainImageKey(mainImageKey);
            mainImageSet = true;
        }

        // Save the Yacht entity to get a generated ID
        Yacht savedYacht = yachtRepository.save(yacht);

        // Check if we need to upload additional images and potentially set the main image from these
        if (!additionalImageFiles.isEmpty()) {
            // Prepare a DTO for saving multiple images
            YachtImageRequestDto imageRequestDto = new YachtImageRequestDto();
            imageRequestDto.setYachtId(savedYacht.getId());

            // Delegate to saveMultipleImages method from YachtImageService
            List<YachtImageResponseDto> savedImageDtos = yachtImageService.saveMultipleImages(imageRequestDto, additionalImageFiles);

            // If no main image was explicitly provided and additional images were uploaded
            if (!mainImageSet && !savedImageDtos.isEmpty()) {
                savedYacht.setMainImageKey(savedImageDtos.get(0).getImageKey());
            }
        }

        // Return the saved yacht as a DTO
        return yachtMapper.convertToDto(savedYacht);
    }

    @Override
    public YachtResponseDto findId(Long id) {
        Yacht yacht = EntityUtils.findEntityOrThrow(id, yachtRepository, "Yacht");
        return yachtMapper.convertToDto(yacht);
    }

    @Override
    public List<YachtResponseDto> findAll() {
        // Fetch all yachts, convert to DTOs
        List<YachtResponseDto> dtos = yachtRepository.findAll().stream()
                .map(yachtMapper::convertToDto)
                .collect(Collectors.toList());

        // Exclude yachts with 0 favourites, then sort the rest by favouritesCount in descending order
        List<YachtResponseDto> filteredAndSortedDtos = dtos.stream()
                .filter(dto -> dto.getFavouritesCount() != null && dto.getFavouritesCount() > 0)
                .sorted(Comparator.comparing(YachtResponseDto::getFavouritesCount, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Identify the top 10 yachts among those filtered and set the 'top10' flag
        filteredAndSortedDtos.stream().limit(10).forEach(dto -> dto.setYachtTop(true));

        // Merge back the filtered and unfiltered lists, if necessary, to include yachts with 0 favourites in the response
        // This step is needed if you want yachts with 0 favourites to still be part of the final list but not marked as top 10
        // Yachts already marked as top 10 are updated in the original DTOs list
        return dtos.stream()
                .sorted(Comparator.comparing(YachtResponseDto::getId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<YachtResponseDto> search(YachtSearchParametersDto searchParametersDto) {
        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto);
        return yachtRepository.findAll(yachtSpecification).stream()
                .map(yachtMapper::convertToDto)
                .sorted(Comparator.comparing(YachtResponseDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public YachtResponseDto update(YachtRequestDto dto, Long id, MultipartFile mainImageFile,
                                   List<MultipartFile> additionalImageFiles) {
        Yacht yacht = EntityUtils.findEntityOrThrow(id, yachtRepository, "Yacht");
        yacht.setPriceOld(yacht.getPrice());

        if (dto != null) {
            yachtMapper.updateYachtFromDto(yacht, dto);
        }

        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            String mainImageKey = s3ImageService.uploadImageToS3(mainImageFile);
            yacht.setMainImageKey(mainImageKey);
        }

        // Optional: Handle removal of existing images
        // yacht.getYachtImages().clear();
        YachtImageRequestDto yachtImageRequestDto = new YachtImageRequestDto();
        yachtImageRequestDto.setYachtId(id);
        yachtImageService.saveMultipleImages(yachtImageRequestDto, additionalImageFiles);
//        saveAdditionalImages(additionalImageFiles, yacht);

        yachtRepository.save(yacht);

        return yachtMapper.convertToDto(yacht);
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
}
