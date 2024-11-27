package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.constants.ApplicationConstants;
import com.norwayyachtbrockers.dto.mapper.YachtMapper;
import com.norwayyachtbrockers.dto.mapper.YachtShortMapper;
import com.norwayyachtbrockers.dto.request.FullYachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.dto.request.YachtUpdateRequestDto;
import com.norwayyachtbrockers.dto.response.PaginatedYachtResponse;
import com.norwayyachtbrockers.dto.response.PaginationAndSortingParametersDto;
import com.norwayyachtbrockers.dto.response.YachtCrmFrontendResponseDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.dto.response.YachtShortResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.repository.specification.yacht.YachtSpecificationBuilder;
import com.norwayyachtbrockers.service.YachtImageService;
import com.norwayyachtbrockers.service.YachtService;
import com.norwayyachtbrockers.util.EntityUtils;
import com.norwayyachtbrockers.util.S3ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final YachtImageService yachtImageService;
    private final YachtMapper yachtMapper;
    private final YachtShortMapper yachtShortMapper;
    private final YachtSpecificationBuilder yachtSpecificationBuilder;
    private final S3ImageService s3ImageService;

    @Override
    @Transactional
    public YachtResponseDto save(FullYachtRequestDto dto, MultipartFile mainImageFile,
                                 List<MultipartFile> additionalImageFiles) {
        // Convert DTO to Yacht entity
        Yacht yacht = yachtMapper.createYachtFromFullYachtRequestDto(dto);

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


//    @Override
//    @Transactional
//    public YachtResponseDto save(YachtRequestDto dto, MultipartFile mainImageFile,
//                                 List<MultipartFile> additionalImageFiles) {
//        // Convert DTO to Yacht entity
//        Yacht yacht = yachtMapper.createYachtFromDto(dto);
//
//        // Set the old price from the DTO
//        yacht.setPriceOld(yacht.getPrice());
//
//        // Initially assume no main image is set
//        boolean mainImageSet = false;
//
//        // Upload and set the main image key if it's provided
//        if (mainImageFile != null && !mainImageFile.isEmpty()) {
//            String mainImageKey = s3ImageService.uploadImageToS3(mainImageFile);
//            yacht.setMainImageKey(mainImageKey);
//            mainImageSet = true;
//        }
//
//        // Save the Yacht entity to get a generated ID
//        Yacht savedYacht = yachtRepository.save(yacht);
//
//        // Check if we need to upload additional images and potentially set the main image from these
//        if (!additionalImageFiles.isEmpty()) {
//            // Prepare a DTO for saving multiple images
//            YachtImageRequestDto imageRequestDto = new YachtImageRequestDto();
//            imageRequestDto.setYachtId(savedYacht.getId());
//
//            // Delegate to saveMultipleImages method from YachtImageService
//            List<YachtImageResponseDto> savedImageDtos = yachtImageService.saveMultipleImages(imageRequestDto, additionalImageFiles);
//
//            // If no main image was explicitly provided and additional images were uploaded
//            if (!mainImageSet && !savedImageDtos.isEmpty()) {
//                savedYacht.setMainImageKey(savedImageDtos.get(0).getImageKey());
//            }
//        }
//
//        // Return the saved yacht as a DTO
//        return yachtMapper.convertToDto(savedYacht);
//    }

    @Override
    @Transactional(readOnly = true)
    public YachtResponseDto findId(Long id) {
        Yacht yacht = EntityUtils.findEntityOrThrow(id, yachtRepository, "Yacht");
        return yachtMapper.convertToDto(yacht);
    }

    @Override
    public List<YachtResponseDto> findAll() {
        // Fetch all yachts, convert to DTOs
        List<YachtResponseDto> dtos = yachtRepository.findAll().stream()
                .map(yachtMapper::convertToDto)
                .toList();

        // Exclude yachts with 0 favourites, then sort the rest by favouritesCount in descending order
        List<YachtResponseDto> filteredAndSortedDtos = dtos.stream()
                .filter(dto -> dto.getFavouritesCount() != null && dto.getFavouritesCount() > 0)
                .sorted(Comparator.comparing(YachtResponseDto::getFavouritesCount, Comparator.reverseOrder()))
                .toList();

        // Identify the top 10 yachts among those filtered and set the 'top10' flag
        filteredAndSortedDtos.stream().limit(10).forEach(dto -> dto.setYachtTop(true));

        // Merge back the filtered and unfiltered lists, if necessary, to include yachts with 0 favourites in the response
        // This step is needed if you want yachts with 0 favourites to still be part of the final list but not marked as top 10
        // Yachts already marked as top 10 are updated in the original DTOs list
        return dtos.stream()
                .sorted(Comparator.comparing(YachtResponseDto::getId).reversed())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<YachtShortResponseDto> findAllYachts() {
        // Fetch all yachts, convert to DTOs
        List<YachtShortResponseDto> dtos = yachtRepository.findAll().stream()
                .map(yachtShortMapper::convertToDto)
                .toList();

        // Exclude yachts with 0 favourites, then sort the rest by favouritesCount in descending order
        List<YachtShortResponseDto> filteredAndSortedDtos = dtos.stream()
                .filter(dto -> dto.getFavouritesCount() != null && dto.getFavouritesCount() > 0)
                .sorted(Comparator.comparing(YachtShortResponseDto::getFavouritesCount, Comparator.reverseOrder()))
                .toList();

        // Identify the top 10 yachts among those filtered and set the 'top10' flag
        filteredAndSortedDtos.stream().limit(10).forEach(dto -> dto.setYachtTop(true));

        // Merge back the filtered and unfiltered lists, if necessary, to include yachts with 0 favourites in the response
        // This step is needed if you want yachts with 0 favourites to still be part of the final list but not marked as top 10
        // Yachts already marked as top 10 are updated in the original DTOs list
        return dtos.stream()
                .sorted(Comparator.comparing(YachtShortResponseDto::getId).reversed())
                .collect(Collectors.toList());
    }


//    @Override
//    public List<YachtResponseDto> search(YachtSearchParametersDto searchParametersDto) {
//        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto);
//        return yachtRepository.findAll(yachtSpecification).stream()
//                .map(yachtMapper::convertToDto)
//                .sorted(Comparator.comparing(YachtResponseDto::getId))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<YachtCrmResponseDto> searchForCrm(YachtSearchParametersDto searchParametersDto) {
//        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto);
//        return yachtRepository.findAll(yachtSpecification).stream()
//                .map(yachtMapper::convertToCrmDto)
//                .sorted(Comparator.comparing(YachtCrmResponseDto::getId))
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public YachtResponseDto update(YachtUpdateRequestDto dto, Long id, MultipartFile mainImageFile,
                                   List<MultipartFile> additionalImageFiles) {
        Yacht yacht = EntityUtils.findEntityOrThrow(id, yachtRepository, "Yacht");

        yacht.setPriceOld(yacht.getPrice());

        yachtMapper.updateYachtFromDto(yacht, dto);

        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            String mainImageKey = s3ImageService.uploadImageToS3(mainImageFile);
            yacht.setMainImageKey(mainImageKey);
        }

        if (yacht.getPriceOld() != null && yacht.getPriceOld().compareTo(yacht.getPrice()) > 0) {
            yacht.setFeatured(true);
        } else {
            yacht.setFeatured(false);
        }

        if (additionalImageFiles != null && !additionalImageFiles.isEmpty()) {
            YachtImageRequestDto yachtImageRequestDto = new YachtImageRequestDto();
            yachtImageRequestDto.setYachtId(id);
            yachtImageService.saveMultipleImages(yachtImageRequestDto, additionalImageFiles);
        }

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

    @Override
    @Transactional(readOnly = true)
    public PaginatedYachtResponse getAllYachtsWithPaginationAndSearch(
            PaginationAndSortingParametersDto paginationAndSortingParametersDto,
            YachtSearchParametersDto searchParametersDto) {

        int page = paginationAndSortingParametersDto.getPage() - 1;
        int size = ApplicationConstants.PAGE_GALLERY_SIZE;

        // Получаем параметры сортировки
        String sortBy = paginationAndSortingParametersDto.getSortBy();
        String orderBy = paginationAndSortingParametersDto.getOrderBy();

        // Преобразуем направление сортировки (asc/desc)
        Sort.Direction direction = Sort.Direction.fromOptionalString(orderBy).orElse(Sort.Direction.ASC);

        // Создаем спецификацию с учетом сортировки
        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto, sortBy, direction);

        // Настраиваем пагинацию без сортировки, так как сортировка обрабатывается в спецификации
        Pageable pageable = PageRequest.of(page, size);

        // Проверяем, нужно ли сортировать по количеству избранного
        Page<Object[]> yachtPage;
        if ("yacht_favourites_count".equalsIgnoreCase(sortBy)) {
            // Если сортировка по количеству избранного, делаем запрос с подсчетом избранного
            yachtPage = yachtRepository.findAllWithFavoritesCount(yachtSpecification, pageable);
        } else {
            // Обычная сортировка
            yachtPage = yachtRepository.findAll(yachtSpecification, pageable)
                    .map(yacht -> new Object[]{yacht, null});
        }

        // Преобразуем сущности в DTO
        List<YachtShortResponseDto> yachts = yachtPage.stream()
                .map(objects -> {
                    Yacht yacht = (Yacht) objects[0];
                    Integer favouritesCount = objects[1] != null ? ((Long) objects[1]).intValue() : null;

                    YachtShortResponseDto dto = yachtShortMapper.convertToDto(yacht);
                    dto.setFavouritesCount(favouritesCount);
                    return dto;
                })
                .collect(Collectors.toList());

        // Формируем ответ с пагинацией
        PaginatedYachtResponse response = new PaginatedYachtResponse();

        // Создаем и заполняем объект Pagination
        PaginatedYachtResponse.Pagination pagination = new PaginatedYachtResponse.Pagination();
        pagination.setCurrentPage(page + 1);
        pagination.setTotalPages(yachtPage.getTotalPages());

        // Устанавливаем pagination и список яхт в ответ
        response.setPagination(pagination);
        response.setYachts(yachts);

        return response;
    }


    @Override
    public YachtCrmFrontendResponseDto getCombinedYachtData() {
        List<Yacht> yachts = yachtRepository.findAll();
        return yachtMapper.combineYachtData(yachts);
    }
}
