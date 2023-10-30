package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

public interface VesselService {
    Vessel findById(Long theId);

    List<Vessel> findAll();

    Vessel save(Vessel vessel, MultipartFile imageFile);

    public Vessel update(Long theId, VesselRequestDto vesselData, MultipartFile imageFile);

    void deleteById(Long theId);

    void deleteAll();
}
