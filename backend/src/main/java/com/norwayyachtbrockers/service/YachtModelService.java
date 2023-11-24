package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.YachtModel;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface YachtModelService {

    YachtModel findById(Long theId);

    List<YachtModel> findAll();

    public YachtModel createYachtModel(@RequestBody YachtModelRequestDto yachtModelRequestDto);

    public YachtModel update(Long theId, YachtModelRequestDto yachtModelRequestDto);
    void deleteById(Long theId);

    List<YachtModel> findByFuelTypeId(@Param("fuelType") Long keelTypeId);

    List<YachtModel> findByKeelTypeId(@Param("keelType") Long keelTypeId);
}
