package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.FuelMapper;
import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.repository.FuelRepository;
import com.norwayyachtbrockers.service.FuelService;
import com.norwayyachtbrockers.util.EntityUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FuelServiceImpl implements FuelService {

    private final FuelRepository fuelRepository;

    public FuelServiceImpl(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    @Override
    @Transactional
    public Fuel saveFuel(FuelRequestDto dto) {
        return fuelRepository.save(FuelMapper.createFuelFromDto(dto));
    }

    @Override
    @Transactional
    public Fuel saveFuel(Fuel fuel) {
        return fuelRepository.save(fuel);
    }

    @Override
    public Fuel findId(Long id) {
        return EntityUtils.findEntityOrThrow(id, fuelRepository, "Fuel");
    }

    @Override
    public List<Fuel> findAll() {
        return fuelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    @Transactional
    public Fuel updateFuel(FuelRequestDto dto, Long id) {
        Fuel fuel = EntityUtils.findEntityOrThrow(id, fuelRepository, "Fuel");
        return fuelRepository.save(FuelMapper.updateFuelFromDto(fuel, dto));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Fuel fuel = EntityUtils.findEntityOrThrow(id, fuelRepository, "Fuel");
        fuelRepository.delete(fuel);
    }

    @Override
    public Long getFuelTypeIdByName(String name) {
        return fuelRepository.findIdByName(name);
    }
}
