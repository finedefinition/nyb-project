package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.YachtModel;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface YachtModelService {

    YachtModel findById(Long theId);

    List<YachtModel> findAll();

    YachtModel save(YachtModel yachtModel);

    public YachtModel update(Long theId, YachtModelRequestDto yachtModelRequestDto);
    void deleteById(Long theId);

    List<YachtModel> findByFuelTypeId(@Param("fuelType") Long keelTypeId);

    List<YachtModel> findByKeelTypeId(@Param("keelType") Long keelTypeId);
}
