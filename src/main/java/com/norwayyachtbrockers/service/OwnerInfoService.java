package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;
import java.util.List;

public interface OwnerInfoService {

    OwnerInfo save(OwnerInfoRequestDto dto);

    OwnerInfo save(OwnerInfo ownerInfo);

    OwnerInfo findId(Long id);

    List<OwnerInfo> findAll();

    OwnerInfo update(OwnerInfoRequestDto dto, Long id);

    void deleteById(Long id);

    Long getOwnerInfoIdByEmailAndTelephone(String email, String telephone);
}
