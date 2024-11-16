package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.constants.ApplicationConstants;
import com.norwayyachtbrockers.dto.mapper.FieldMapper;
import com.norwayyachtbrockers.dto.mapper.YachtMapper;
import com.norwayyachtbrockers.dto.mapper.YachtShortMapper;
import com.norwayyachtbrockers.dto.request.FullYachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.dto.response.PaginatedYachtCrmResponse;
import com.norwayyachtbrockers.dto.response.PaginatedYachtResponse;
import com.norwayyachtbrockers.dto.response.PaginationAndSortingParametersDto;
import com.norwayyachtbrockers.dto.response.YachtCrmFrontendResponseDto;
import com.norwayyachtbrockers.dto.response.YachtCrmResponseDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.dto.response.YachtShortResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.repository.projections.YachtShortProjection;
import com.norwayyachtbrockers.repository.specification.yacht.YachtSpecificationBuilder;
import com.norwayyachtbrockers.service.YachtImageService;
import com.norwayyachtbrockers.service.YachtService;
import com.norwayyachtbrockers.util.EntityUtils;
import com.norwayyachtbrockers.util.S3ImageService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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


    @Override
    public List<YachtResponseDto> search(YachtSearchParametersDto searchParametersDto) {
        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto);
        return yachtRepository.findAll(yachtSpecification).stream()
                .map(yachtMapper::convertToDto)
                .sorted(Comparator.comparing(YachtResponseDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<YachtCrmResponseDto> searchForCrm(YachtSearchParametersDto searchParametersDto) {
        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto);
        return yachtRepository.findAll(yachtSpecification).stream()
                .map(yachtMapper::convertToCrmDto)
                .sorted(Comparator.comparing(YachtCrmResponseDto::getId))
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

    @Transactional
    @Override
    public PaginatedYachtCrmResponse getYachtsWithPaginationAndSearch(PaginationAndSortingParametersDto paginationAndSortingParametersDto, YachtSearchParametersDto searchParametersDto) {
        // Get the page, sortBy, and orderBy from the DTO
        int page = paginationAndSortingParametersDto.getPage() - 1; // Spring Data JPA pages are 0-indexed
        String sortBy = paginationAndSortingParametersDto.getSortBy();
        String orderBy = paginationAndSortingParametersDto.getOrderBy();

        // Translate "descend" to "desc" and "ascend" to "asc"
        if ("descend".equalsIgnoreCase(orderBy)) {
            orderBy = "desc";
        } else if ("ascend".equalsIgnoreCase(orderBy)) {
            orderBy = "asc";
        }

        // Default to ascending sort if the direction is invalid or null
        Sort.Direction direction = Sort.Direction.fromOptionalString(orderBy).orElse(Sort.Direction.ASC);

        // Map DTO sort fields to entity fields
        sortBy = FieldMapper.getEntityField(sortBy);

        Sort sort = Sort.by(direction, sortBy);

        // Create a PageRequest with the sort parameter
        PageRequest pageRequest = PageRequest.of(page, ApplicationConstants.PAGE_CRM_SIZE, sort);

        // Build the Specification based on search parameters
        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto);

        // Query the repository with pagination, sorting, and search criteria
        Page<Yacht> yachtPage = yachtRepository.findAll(yachtSpecification, pageRequest);

        List<YachtCrmResponseDto> yachtDtos = yachtPage.stream()
                .map(yachtMapper::convertToCrmDto)
                .collect(Collectors.toList());

        PaginatedYachtCrmResponse response = new PaginatedYachtCrmResponse();
        response.setCurrentPage(page + 1); // Adjust back to 1-indexed page
        response.setTotalPages(yachtPage.getTotalPages());
        response.setTotalItems(yachtPage.getTotalElements());
        response.setYachts(yachtDtos);

        return response;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public PaginatedYachtResponse getAllYachtsWithPaginationAndSearch(
//            PaginationAndSortingParametersDto paginationAndSortingParametersDto,
//            YachtSearchParametersDto searchParametersDto) {
//
//        // Преобразуем параметры пагинации и сортировки
//        int page = paginationAndSortingParametersDto.getPage() - 1;
//        int size = ApplicationConstants.PAGE_GALLERY_SIZE;
//
//        String sortBy = paginationAndSortingParametersDto.getSortBy();
//        String orderBy = paginationAndSortingParametersDto.getOrderBy();
//
//        // Преобразуем "descend" в "desc" и "ascend" в "asc"
//        if ("descend".equalsIgnoreCase(orderBy)) {
//            orderBy = "desc";
//        } else if ("ascend".equalsIgnoreCase(orderBy)) {
//            orderBy = "asc";
//        }
//
//        Sort.Direction direction = Sort.Direction.fromOptionalString(orderBy).orElse(Sort.Direction.ASC);
//
//        // Map DTO sort fields to entity fields
//        sortBy = FieldMapper.getEntityField(sortBy);
//
//        Pageable pageable = PageRequest.of(page, ApplicationConstants.PAGE_GALLERY_SIZE, Sort.by(direction, sortBy));
//
//        // Поскольку мы не можем использовать Specification с кастомным запросом, придётся от него отказаться
//        // или реализовать кастомный репозиторий (см. предыдущие шаги)
//
//        // Получаем данные из репозитория
//        Page<YachtShortProjection> yachtPage = yachtRepository.findAllProjected(pageable);
//
//        // Используем ваш маппер для преобразования проекций в DTO
//        List<YachtShortResponseDto> yachts = yachtPage.stream()
//                .map(yachtShortMapper::convertProjectionToDto)
//                .collect(Collectors.toList());
//
//        // Создаем объект ответа и заполняем данные
//        PaginatedYachtResponse response = new PaginatedYachtResponse();
//
//        // Создаем и заполняем объект Pagination
//        PaginatedYachtResponse.Pagination pagination = new PaginatedYachtResponse.Pagination();
//        pagination.setCurrentPage(page + 1);
//        pagination.setTotalPages(yachtPage.getTotalPages());
//        pagination.setTotalItems(yachtPage.getTotalElements());
//
//        // Устанавливаем pagination и список яхт в ответ
//        response.setPagination(pagination);
//        response.setYachts(yachts);
//
//        return response;
//    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedYachtResponse getAllYachtsWithPaginationAndSearch(
            PaginationAndSortingParametersDto paginationAndSortingParametersDto,
            YachtSearchParametersDto searchParametersDto) {

        int page = paginationAndSortingParametersDto.getPage() - 1;
        int size = ApplicationConstants.PAGE_GALLERY_SIZE;

        String sortBy = paginationAndSortingParametersDto.getSortBy();
        String orderBy = paginationAndSortingParametersDto.getOrderBy();

        Sort.Direction direction = Sort.Direction.fromOptionalString(orderBy).orElse(Sort.Direction.ASC);
        sortBy = FieldMapper.getEntityField(sortBy);

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Создаем спецификацию на основе параметров поиска
        Specification<Yacht> yachtSpecification = yachtSpecificationBuilder.build(searchParametersDto);

        Page<Yacht> yachtPage = yachtRepository.findAll(yachtSpecification, pageable);

        // Используем ваш маппер для преобразования сущностей в DTO
        List<YachtShortResponseDto> yachts = yachtPage.stream()
                .map(yachtShortMapper::convertToDto)
                .collect(Collectors.toList());

        // Создаем объект ответа и заполняем данные
        PaginatedYachtResponse response = new PaginatedYachtResponse();

        // Создаем и заполняем объект Pagination
        PaginatedYachtResponse.Pagination pagination = new PaginatedYachtResponse.Pagination();
        pagination.setCurrentPage(page + 1);
        pagination.setTotalPages(yachtPage.getTotalPages());
        // Если вам не нужен totalItems, вы можете не устанавливать его или установить в null
        // pagination.setTotalItems(yachtPage.getTotalElements());

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
