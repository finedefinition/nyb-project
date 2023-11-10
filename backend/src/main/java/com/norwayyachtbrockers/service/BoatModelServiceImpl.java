package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.mapper.BoatModelMapper;
import com.norwayyachtbrockers.dto.request.BoatModelRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.BoatModel;
import com.norwayyachtbrockers.repository.BoatModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BoatModelServiceImpl implements BoatModelService {
    private final BoatModelRepository boatModelRepository;

    private final BoatModelMapper boatModelMapper;

    public BoatModelServiceImpl(BoatModelRepository boatModelRepository, BoatModelMapper boatModelMapper) {
        this.boatModelRepository = boatModelRepository;
        this.boatModelMapper = boatModelMapper;
    }

    @Override
    public BoatModel findById(Long theId) {
        return boatModelRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String
                        .format("Cannot find the boat model with ID: %d", theId)));
    }

    @Override
    public List<BoatModel> findAll() {
        return boatModelRepository.findAll();
    }

    @Transactional
    @Override
    public BoatModel update(Long theId, BoatModelRequestDto boatModelRequestDto) {
        BoatModel existingBoatModel = boatModelRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String
                        .format("Cannot update the boat model with ID: %d", theId)));
        boatModelMapper.updateBoatModelFromDto(existingBoatModel, boatModelRequestDto);
        // Save the updated boat model
        return boatModelRepository.save(existingBoatModel);
    }

    @Transactional
    @Override
    public BoatModel save(BoatModel boatModel) {
        return boatModelRepository.save(boatModel);
    }

    @Transactional
    @Override
    public void deleteById(Long theId) {
        BoatModel boatModel = boatModelRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String.format("Cannot delete the Boat Model with ID: %d", theId)));
        boatModel.setKeelType(null);
        boatModel.setFuelType(null);
        boatModelRepository.save(boatModel);
        boatModelRepository.delete(boatModel);
    }

    @Override
    public List<BoatModel> findByFuelTypeId(Long fuelTypeId) {
        List<BoatModel> boatModels = boatModelRepository.findByFuelTypeId(fuelTypeId);
        if (boatModels.isEmpty()) {
            throw new AppEntityNotFoundException(
                    String.format("No boat models found with fuel type ID: %d", fuelTypeId));
        }
        return boatModels;
    }

    @Override
    public List<BoatModel> findByKeelTypeId(Long keelTypeId) {
        List<BoatModel> boatModels = boatModelRepository.findByKeelTypeId(keelTypeId);
        if (boatModels.isEmpty()) {
            throw new AppEntityNotFoundException(
                    String.format("No boat models found with keel type ID: %d", keelTypeId));
        }
        return boatModels;
    }
}
