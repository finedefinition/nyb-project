package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.model.Vessel;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface VesselService {
    Vessel findById(Long theId);

    List<Vessel> findAll();

    Vessel save(Vessel vessel, MultipartFile imageFile);

    Vessel update(Long theId, VesselRequestDto vesselData, MultipartFile imageFile);

    void deleteById(Long theId);

    void deleteAll();
}
