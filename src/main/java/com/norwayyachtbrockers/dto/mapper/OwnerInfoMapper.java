package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class OwnerInfoMapper {

    public OwnerInfo createOwnerInfoFromDto(@Valid OwnerInfoRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Failed to create OwnerInfo: DTO cannot be null");
        }
        OwnerInfo ownerInfo = new OwnerInfo();
        ownerInfo.setFirstName(dto.getFirstName().trim());
        ownerInfo.setLastName(dto.getLastName().trim());
        ownerInfo.setTelephone(dto.getPhoneNumber().trim());
        ownerInfo.setEmail(dto.getEmail());
        return ownerInfo;
    }

    public OwnerInfo updateOwnerInfoFromDto(OwnerInfo ownerInfo, @Valid OwnerInfoRequestDto dto) {
        if (dto == null || ownerInfo == null) {
            throw new IllegalArgumentException("Failed to update OwnerInfo: Neither OwnerInfo object nor DTO can be null.");
        }
        ownerInfo.setFirstName(dto.getFirstName().trim());
        ownerInfo.setLastName(dto.getLastName().trim());
        ownerInfo.setTelephone(dto.getPhoneNumber().trim());
        ownerInfo.setEmail(dto.getEmail());
        return ownerInfo;
    }
}
