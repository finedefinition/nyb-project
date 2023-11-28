package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import java.util.List;

public interface KeelService {

    Keel saveKeel(KeelRequestDto dto);

    Keel findId(Long id);

    List<Keel> findAll();

    Keel updateKeel(KeelRequestDto dto, Long id);

    void deleteById(Long id);
}
