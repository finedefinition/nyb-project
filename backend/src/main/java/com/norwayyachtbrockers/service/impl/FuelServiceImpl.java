package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.FuelMapper;
import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.repository.FuelRepository;
import com.norwayyachtbrockers.service.FuelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
public class FuelServiceImpl implements FuelService {

    private final FuelRepository fuelRepository;

    private final FuelMapper fuelMapper;

    public FuelServiceImpl(FuelRepository fuelRepository,
                           FuelMapper fuelMapper) {
        this.fuelRepository = fuelRepository;
        this.fuelMapper = fuelMapper;
    }

    @Override
    @Transactional
    public Fuel saveFuel(FuelRequestDto dto) {

        Fuel fuel = new Fuel();
        fuelMapper.updateFuelFromDto(fuel, dto);

        return fuelRepository.save(fuel);
    }

    @Override
    public Fuel findId(Long id) {
        return fuelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                .format("Fuel with ID: %d not found", id)));
    }

    @Override
    public List<Fuel> findAll() {
        List<Fuel> fuels = fuelRepository.findAll();
        fuels.sort(Comparator.comparing(Fuel::getId));
        return fuels;
    }

    @Override
    @Transactional
    public Fuel updateFuel(FuelRequestDto dto, Long id) {

        Fuel fuel = fuelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                .format("Cannot update. The Fuel with ID: %d not found", id)));

        fuelMapper.updateFuelFromDto(fuel, dto);

        return fuelRepository.save(fuel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Fuel fuel = fuelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot delete. The Fuel with ID: %d not found", id)));

        fuelRepository.delete(fuel);

    }
}
