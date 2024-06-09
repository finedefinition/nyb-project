package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.KeelMapper;
import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.repository.KeelRepository;
import com.norwayyachtbrockers.service.KeelService;
import com.norwayyachtbrockers.util.EntityUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return keelRepository.save(keelMapper.createKeelFromDto(dto));
    }

    @Override
    public Keel saveKeel(Keel keel) {
        return keelRepository.save(keel);
    }

    @Override
    public Keel findId(Long id) {
        return EntityUtils.findEntityOrThrow(id, keelRepository, "Keel");
    }

    @Override
    public List<Keel> findAll() {
        return keelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    @Transactional
    public Keel updateKeel(KeelRequestDto dto, Long id) {
        Keel keel = EntityUtils.findEntityOrThrow(id, keelRepository, "Keel");
        keelMapper.updateKeelFromDto(keel, dto);
        return keelRepository.save(keel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Keel keel = EntityUtils.findEntityOrThrow(id, keelRepository, "Keel");
        keelRepository.delete(keel);
    }

    @Override
    public Long getKeelTypeIdByName(String name) {
        return keelRepository.findIdByName(name);
    }
}
