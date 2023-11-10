package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.BoatModelRequestDto;
import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.model.BoatModel;
import com.norwayyachtbrockers.model.Vessel;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoatModelService {

    BoatModel findById(Long theId);

    List<BoatModel> findAll();

    BoatModel save(BoatModel boatModel);

    public BoatModel update(Long theId, BoatModelRequestDto boatModelRequestDto);
    void deleteById(Long theId);

    List<BoatModel> findByFuelTypeId(@Param("fuelType") Long keelTypeId);

    List<BoatModel> findByKeelTypeId(@Param("keelType") Long keelTypeId);
}
