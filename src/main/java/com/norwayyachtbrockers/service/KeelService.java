package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.repository.projections.KeelProjection;

import java.util.List;

public interface KeelService {

    Keel saveKeel(KeelRequestDto dto);

    Keel saveKeel(Keel keel);

    Keel findId(Long id);

    List<KeelProjection> findAll();

    Keel updateKeel(KeelRequestDto dto, Long id);

    void deleteById(Long id);

    Long getKeelTypeIdByName(String name);
}
