package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtModelMapper;
import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.FuelRepository;
import com.norwayyachtbrockers.repository.KeelRepository;
import com.norwayyachtbrockers.repository.YachtModelRepository;
import com.norwayyachtbrockers.service.YachtModelService;
import com.norwayyachtbrockers.util.EntityUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class YachtModelServiceImpl implements YachtModelService {

    private final YachtModelRepository yachtModelRepository;
    private final YachtModelMapper yachtModelMapper;
    private final KeelRepository keelRepository;
    private final FuelRepository fuelRepository;

    public YachtModelServiceImpl(YachtModelRepository yachtModelRepository, YachtModelMapper yachtModelMapper,
                                 KeelRepository keelRepository, FuelRepository fuelRepository) {
        this.yachtModelRepository = yachtModelRepository;
        this.yachtModelMapper = yachtModelMapper;
        this.keelRepository = keelRepository;
        this.fuelRepository = fuelRepository;
    }

    @Override
    @Transactional
    public YachtModel saveYachtModel(YachtModelRequestDto dto) {
        Keel keel = EntityUtils.findEntityOrThrow(dto.getKeelTypeId(), keelRepository, "Keel");
        Fuel fuel = EntityUtils.findEntityOrThrow(dto.getFuelTypeId(), fuelRepository, "Fuel");

        YachtModel yachtModel = yachtModelMapper.createYachtModelFromDto( dto);

        // Assuming Keel and Fuel entities have convenience methods to add a yacht model
        keel.addYachtModel(yachtModel);
        fuel.addYachtModel(yachtModel);

        // No need to set Keel and Fuel on the yachtModel directly if the addYachtModel methods set the inverse relationship
        return yachtModelRepository.save(yachtModel);
    }

    @Override
    public YachtModel saveYachtModel(YachtModel yachtModel) {
        return yachtModelRepository.save(yachtModel);
    }

    @Override
    public YachtModel findId(Long id) {
        return EntityUtils.findEntityOrThrow(id, yachtModelRepository, "YachtModel");
    }

    @Override
    public List<YachtModel> findAll() {
        return yachtModelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    @Transactional
    public YachtModel updateYachtModel(YachtModelRequestDto dto, Long id) {
        YachtModel existingModel = EntityUtils.findEntityOrThrow(id, yachtModelRepository, "YachtModel");
        Keel newKeel = EntityUtils.findEntityOrThrow(dto.getKeelTypeId(), keelRepository, "Keel");
        Fuel newFuel = EntityUtils.findEntityOrThrow(dto.getFuelTypeId(), fuelRepository, "Fuel");

        // If Keel has changed, update it
        if (!newKeel.equals(existingModel.getKeelType())) {
            if (existingModel.getKeelType() != null) {
                existingModel.getKeelType().removeYachtModel(existingModel);
            }
            newKeel.addYachtModel(existingModel);
        }

        // If Fuel has changed, update it
        if (!newFuel.equals(existingModel.getFuelType())) {
            if (existingModel.getFuelType() != null) {
                existingModel.getFuelType().removeYachtModel(existingModel);
            }
            newFuel.addYachtModel(existingModel);
        }
        // Update other fields
        yachtModelMapper.updateYachtModelFromDto(existingModel, dto);

        return yachtModelRepository.save(existingModel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        YachtModel yachtModel = EntityUtils.findEntityOrThrow(id, yachtModelRepository, "YachtModel");

        // Detach the yacht model from Keel and Fuel
        if (yachtModel.getKeelType() != null) {
            yachtModel.getKeelType().removeYachtModel(yachtModel);
        }
        if (yachtModel.getFuelType() != null) {
            yachtModel.getFuelType().removeYachtModel(yachtModel);
        }

        yachtModelRepository.delete(yachtModel);
    }

    @Override
    public List<YachtModel> findByKeelType_Id(Long keelTypeId) {
        EntityUtils.findEntityOrThrow(keelTypeId, keelRepository, "Keel");
        return yachtModelRepository.findByKeelType_Id(keelTypeId);
    }

    @Override
    public List<YachtModel> findByFuelType_Id(Long fuelTypeId) {
        EntityUtils.findEntityOrThrow(fuelTypeId, fuelRepository, "Fuel");
        return yachtModelRepository.findByFuelType_Id(fuelTypeId);
    }

    @Override
    public Long getYachtModelId(String make, String model, Integer year) {
        return yachtModelRepository.findIdByMakeModelYear(make, model, year);
    }
}
