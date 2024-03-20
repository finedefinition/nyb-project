package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;
import org.springframework.stereotype.Component;

@Component
public class OwnerInfoMapper {
    public void updateOwnerInfoFromDto(OwnerInfo ownerInfo, OwnerInfoRequestDto dto) {
        ownerInfo.setFirstName(dto.getFirstName());
        ownerInfo.setLastName(dto.getLastName());
        ownerInfo.setTelephone(dto.getTelephone());
        ownerInfo.setEmail(dto.getEmail());
    }
}
