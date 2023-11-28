package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.KeelMapper;
import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.repository.KeelRepository;
import com.norwayyachtbrockers.service.KeelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
public class KeelServiceImpl implements KeelService {

    private final KeelRepository keelRepository;
    private final KeelMapper keelMapper;

    public KeelServiceImpl(KeelRepository keelRepository,
                           KeelMapper keelMapper) {
        this.keelRepository = keelRepository;
        this.keelMapper = keelMapper;
    }

    @Override
    @Transactional
    public Keel saveKeel(KeelRequestDto dto) {

        Keel keel = new Keel();
        keelMapper.updateKeelFromDto(keel, dto);

        return keelRepository.save(keel);
    }

    @Override
    public Keel findId(Long id) {
        return keelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Keel with ID: %d not found", id)));
    }

    @Override
    public List<Keel> findAll() {
        List<Keel> keels = keelRepository.findAll();
        keels.sort(Comparator.comparing(Keel::getId));
        return keels;
    }

    @Override
    @Transactional
    public Keel updateKeel(KeelRequestDto dto, Long id) {

        Keel keel = keelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot update. The Keel with ID: %d not found", id)));

        keelMapper.updateKeelFromDto(keel, dto);

        return keelRepository.save(keel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Keel keel = keelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot delete. The Keel with ID: %d not found", id)));

        keelRepository.delete(keel);
    }
}
