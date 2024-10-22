package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;

public class OwnerInfoMapper {

    public static OwnerInfo createOwnerInfoFromDto(OwnerInfoRequestDto dto) {
        OwnerInfo ownerInfo = new OwnerInfo();
        ownerInfo.setFirstName(dto.getFirstName().trim());
        ownerInfo.setLastName(dto.getLastName().trim());
        ownerInfo.setTelephone(dto.getPhoneNumber().trim());
        ownerInfo.setEmail(dto.getEmail());
        return ownerInfo;
    }

    public static OwnerInfo updateOwnerInfoFromDto(OwnerInfo ownerInfo, OwnerInfoRequestDto dto) {
        ownerInfo.setFirstName(dto.getFirstName().trim());
        ownerInfo.setLastName(dto.getLastName().trim());
        ownerInfo.setTelephone(dto.getPhoneNumber().trim());
        ownerInfo.setEmail(dto.getEmail());
        return ownerInfo;
    }
}
