package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.YachtModel;
import java.util.List;

public interface TownService {
    Town saveTown(TownRequestDto dto);

    YachtModel findId(Long id);

    List<YachtModel> findAll();

    YachtModel updateYachtModel(YachtModelRequestDto dto, Long id);

    void deleteById(Long id);
}
