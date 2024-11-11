package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.repository.projections.YachtDetailProjection;

import java.util.List;

public interface YachtDetailService {
    YachtDetail save(YachtDetailRequestDto dto);

    YachtDetail findId(Long id);

    List<YachtDetailProjection> findAll();

    YachtDetail update(YachtDetailRequestDto dto, Long id);

    void deleteById(Long id);
}
