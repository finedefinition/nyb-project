package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.dto.response.VesselResponseDto;
import com.norwayyachtbrockers.model.Vessel;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface VesselService {
    VesselResponseDto findById(Long theId);

    List<VesselResponseDto> findAll();

    Vessel save(Vessel vessel, MultipartFile imageFile);

    Vessel update(Long theId, VesselRequestDto vesselData, MultipartFile imageFile);

    void deleteById(Long theId);

    void deleteAll();
}
