package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeelMapper {
    public void updateKeelFromDto(Keel existingKeel, KeelRequestDto dto) {
        existingKeel.setName(dto.getName());
    }
}
