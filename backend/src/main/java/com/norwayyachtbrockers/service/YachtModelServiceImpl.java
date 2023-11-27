package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.dto.mapper.YachtModelMapper;
import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.FuelRepository;
import com.norwayyachtbrockers.repository.KeelRepository;
import com.norwayyachtbrockers.repository.yachtmodel.YachtModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

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
                        .format("Cannot update the Yacht Model with Keel ID: %d", dto.getKeelTypeId())));

        Fuel fuel = fuelRepository.findById(dto.getFuelTypeId())
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot update the Yacht Model with Fuel ID: %d", dto.getFuelTypeId())));
        YachtModel yachtModel = new YachtModel();
        yachtModelMapper.updateYachtModelFromDto(yachtModel, dto);

        yachtModel.setKeelType(keel);
        yachtModel.setFuelType(fuel);

        return yachtModelRepository.save(yachtModel);
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
    public Optional<YachtModel> findId(Long id) {
        // Additional business logic (if any)
        return yachtModelRepository.findById(id);
    }

    @Override
    public List<YachtModel> findAll() {
        // Additional business logic (if any)
        return yachtModelRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long theId) {
        YachtModel yachtModel = yachtModelRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String.format("Cannot delete the yacht model with ID: %d", theId)));

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
        return yachtModelRepository.findByKeelType_Id(keelTypeId);
    }

    @Override
    public List<YachtModel> findByFuelType_Id(Long fuelTypeId) {
        return yachtModelRepository.findByFuelType_Id(fuelTypeId);
    }

}
