package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.mapper.YachtModelMapper;
import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.YachtModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class YachtModelServiceImpl implements YachtModelService {
    private final YachtModelRepository yachtModelRepository;

    private final YachtModelMapper yachtModelMapper;

    public YachtModelServiceImpl(YachtModelRepository yachtModelRepository, YachtModelMapper yachtModelMapper) {
        this.yachtModelRepository = yachtModelRepository;
        this.yachtModelMapper = yachtModelMapper;
    }

    @Override
    public YachtModel findById(Long theId) {
        return yachtModelRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String
                        .format("Cannot find the yacht model with ID: %d", theId)));
    }

    @Override
    public List<YachtModel> findAll() {
        return yachtModelRepository.findAll();
    }

    @Transactional
    @Override
    public YachtModel update(Long theId, YachtModelRequestDto yachtModelRequestDto) {
        YachtModel existingYachtModel = yachtModelRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String
                        .format("Cannot update the yacht model with ID: %d", theId)));
        yachtModelMapper.updateYachtModelFromDto(existingYachtModel, yachtModelRequestDto);

        return yachtModelRepository.save(existingYachtModel);
    }

    @Transactional
    @Override
    public YachtModel save(YachtModel yachtModel) {
        return yachtModelRepository.save(yachtModel);
    }

    @Transactional
    @Override
    public void deleteById(Long theId) {
        YachtModel yachtModel = yachtModelRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String.format("Cannot delete the yacht model with ID: %d", theId)));
        yachtModel.setKeelType(null);
        yachtModel.setFuelType(null);
        yachtModelRepository.save(yachtModel);
        yachtModelRepository.delete(yachtModel);
    }

    @Override
    public List<YachtModel> findByFuelTypeId(Long fuelTypeId) {
        List<YachtModel> yachtModels = yachtModelRepository.findByFuelTypeId(fuelTypeId);
        if (yachtModels.isEmpty()) {
            throw new AppEntityNotFoundException(
                    String.format("No yacht models found with fuel type ID: %d", fuelTypeId));
        }
        return yachtModels;
    }

    @Override
    public List<YachtModel> findByKeelTypeId(Long keelTypeId) {
        List<YachtModel> yachtModels = yachtModelRepository.findByKeelTypeId(keelTypeId);
        if (yachtModels.isEmpty()) {
            throw new AppEntityNotFoundException(
                    String.format("No yacht models found with keel type ID: %d", keelTypeId));
        }
        return yachtModels;
    }
}
