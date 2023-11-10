package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.BoatModel;
import com.norwayyachtbrockers.repository.BoatModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoatModelServiceImpl implements BoatModelService {
    private final BoatModelRepository boatModelRepository;

    public BoatModelServiceImpl(BoatModelRepository boatModelRepository) {
        this.boatModelRepository = boatModelRepository;
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
}
