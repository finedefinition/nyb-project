package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@RequiredArgsConstructor
public class KeelMapper {

    public Keel createKeelFromDto(@Valid KeelRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Failed to create Keel: DTO cannot be null");
        }
        Keel keel = new Keel();
        keel.setName(dto.getName().trim());
        return keel;
    }

    public Keel updateKeelFromDto(Keel keel, @Valid KeelRequestDto dto) {
        if (dto == null || keel == null) {
            throw new IllegalArgumentException("Failed to update Keel: Neither Keel object nor DTO can be null.");
        }
        keel.setName(dto.getName().trim());
        return keel;
    }
}
