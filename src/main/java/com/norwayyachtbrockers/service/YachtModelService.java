package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.YachtModel;
import java.util.List;

public interface YachtModelService {
    YachtModel saveYachtModel(YachtModelRequestDto dto);

    YachtModel findId(Long id);

    List<YachtModel> findAll();

    YachtModel updateYachtModel(YachtModelRequestDto dto, Long id);

    void deleteById(Long id);

    // Find YachtModels by Keel Type ID
    List<YachtModel> findByKeelType_Id(Long keelTypeId);

    // Find YachtModels by Fuel Type ID
    List<YachtModel> findByFuelType_Id(Long fuelTypeId);
}
