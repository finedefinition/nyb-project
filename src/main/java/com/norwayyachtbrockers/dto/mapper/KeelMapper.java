package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;

public class KeelMapper {

    public static Keel createKeelFromDto(KeelRequestDto dto) {
        Keel keel = new Keel();
        keel.setName(dto.getName().trim());
        return keel;
    }

    public static Keel updateKeelFromDto(Keel keel, KeelRequestDto dto) {
        keel.setName(dto.getName().trim());
        return keel;
    }
}
