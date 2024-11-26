package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.FullYachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.dto.response.PaginatedYachtCrmResponse;
import com.norwayyachtbrockers.dto.response.PaginatedYachtResponse;
import com.norwayyachtbrockers.dto.response.PaginationAndSortingParametersDto;
import com.norwayyachtbrockers.dto.response.YachtCrmFrontendResponseDto;
import com.norwayyachtbrockers.dto.response.YachtCrmResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.dto.response.YachtShortResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface YachtService {

    YachtResponseDto save(FullYachtRequestDto dto, MultipartFile mainImageFile,
                          List<MultipartFile> additionalImageFiles);

//    YachtResponseDto save(YachtRequestDto dto, MultipartFile mainImageFile,
//                          List<MultipartFile> additionalImageFiles);

    YachtResponseDto findId(Long id);

    List<YachtResponseDto> findAll();

    List<YachtShortResponseDto> findAllYachts();

    YachtResponseDto update(YachtRequestDto dto, Long id, MultipartFile mainImageFile,
                            List<MultipartFile> additionalImageFiles);

    void deleteById(Long id);

    PaginatedYachtResponse getAllYachtsWithPaginationAndSearch(
            PaginationAndSortingParametersDto paginationAndSortingParametersDto,
            YachtSearchParametersDto searchParametersDto);

    YachtCrmFrontendResponseDto getCombinedYachtData();
}
