package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.BoatModelRequestDto;
import com.norwayyachtbrockers.model.BoatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoatModelMapper {
    public void updateBoatModelFromDto(BoatModel existingModel, BoatModelRequestDto boatModelRequestDto) {
        existingModel.setModel(boatModelRequestDto.getModel());
        existingModel.setModel(boatModelRequestDto.getModel());
        existingModel.setYear(boatModelRequestDto.getYear());
        existingModel.setLengthOverall(boatModelRequestDto.getLengthOverall());
        existingModel.setBeamWidth(boatModelRequestDto.getBeamWidth());
        existingModel.setDraftDepth(boatModelRequestDto.getDraftDepth());
        existingModel.setKeelType(boatModelRequestDto.getKeelType());
        existingModel.setFuelType(boatModelRequestDto.getFuelType());
    }
}
