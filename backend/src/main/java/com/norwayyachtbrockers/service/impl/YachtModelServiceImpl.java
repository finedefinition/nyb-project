package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtModelMapper;
import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.FuelRepository;
import com.norwayyachtbrockers.repository.KeelRepository;
import com.norwayyachtbrockers.repository.yachtmodel.YachtModelRepository;
import com.norwayyachtbrockers.service.YachtModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
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
        Keel keel = keelRepository.findById(dto.getKeelTypeId())
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot save the Yacht Model. Keel ID: %d not found", dto.getKeelTypeId())));

        Fuel fuel = fuelRepository.findById(dto.getFuelTypeId())
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot save the Yacht Model. Fuel ID: %d not found", dto.getFuelTypeId())));
        YachtModel yachtModel = new YachtModel();
        yachtModelMapper.updateYachtModelFromDto(yachtModel, dto);

        yachtModel.setKeelType(keel);
        yachtModel.setFuelType(fuel);

        return yachtModelRepository.save(yachtModel);
    }

    @Override
    public YachtModel findId(Long id) {
        return yachtModelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Yacht Model with ID: %d not found", id)));
    }

    @Override
    public List<YachtModel> findAll() {
        List<YachtModel> yachtModels = yachtModelRepository.findAll();
        yachtModels.sort(Comparator.comparing(YachtModel::getId));
        return yachtModels;
    }

    @Override
    @Transactional
    public YachtModel updateYachtModel(YachtModelRequestDto dto, Long id) {
        YachtModel existingModel = yachtModelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Yacht Model with ID: %d not found", id)));

        Keel keel = keelRepository.findById(dto.getKeelTypeId())
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Keel with ID: %d not found", dto.getKeelTypeId())));

        Fuel fuel = fuelRepository.findById(dto.getFuelTypeId())
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Fuel with ID: %d not found", dto.getFuelTypeId())));

        yachtModelMapper.updateYachtModelFromDto(existingModel, dto);

        existingModel.setKeelType(keel);
        existingModel.setFuelType(fuel);

        return yachtModelRepository.save(existingModel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        YachtModel yachtModel = yachtModelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot delete. The Yacht Model with ID: %d not found", id)));

        // Detach related entities
        if (yachtModel.getKeelType() != null) {
            yachtModel.getKeelType().getYachtModels().remove(yachtModel);
        }
        if (yachtModel.getFuelType() != null) {
            yachtModel.getFuelType().getYachtModels().remove(yachtModel);
        }

        yachtModelRepository.delete(yachtModel);
    }

    @Override
    public List<YachtModel> findByKeelType_Id(Long keelTypeId) {
        keelRepository.findById(keelTypeId)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Keel with ID: %d not found", keelTypeId)));
        return yachtModelRepository.findByKeelType_Id(keelTypeId);
    }

    @Override
    public List<YachtModel> findByFuelType_Id(Long fuelTypeId) {
        fuelRepository.findById(fuelTypeId)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Fuel with ID: %d not found", fuelTypeId)));
        return yachtModelRepository.findByFuelType_Id(fuelTypeId);
    }

}
