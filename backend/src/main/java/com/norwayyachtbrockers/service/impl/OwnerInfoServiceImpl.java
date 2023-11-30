package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.OwnerInfoMapper;
import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.repository.OwnerInfoRepository;
import com.norwayyachtbrockers.service.OwnerInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerInfoServiceImpl implements OwnerInfoService {

    private final OwnerInfoRepository ownerInfoRepository;

    private final OwnerInfoMapper ownerInfoMapper;

    public OwnerInfoServiceImpl(OwnerInfoRepository ownerInfoRepository,
                                OwnerInfoMapper ownerInfoMapper) {
        this.ownerInfoRepository = ownerInfoRepository;
        this.ownerInfoMapper = ownerInfoMapper;
    }

    @Override
    @Transactional
    public OwnerInfo save(OwnerInfoRequestDto dto) {
        OwnerInfo ownerInfo = new OwnerInfo();
        ownerInfoMapper.updateOwnerInfoFromDto(ownerInfo, dto);
        return ownerInfoRepository.save(ownerInfo);
    }

    @Override
    public OwnerInfo findId(Long id) {
        return ownerInfoRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Owner info with ID: %d not found", id)));
    }

    @Override
    public List<OwnerInfo> findAll() {
        return ownerInfoRepository.findAll().stream()
                .sorted(Comparator.comparing(OwnerInfo::getId))
                .collect(Collectors.toList());
    }

    @Override
    public OwnerInfo update(OwnerInfoRequestDto dto, Long id) {
        OwnerInfo ownerInfo = ownerInfoRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Owner info with ID: %d not found", id)));
        ownerInfoMapper.updateOwnerInfoFromDto(ownerInfo, dto);
        return ownerInfo;
    }

    @Override
    public void deleteById(Long id) {
        OwnerInfo ownerInfo = ownerInfoRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Owner info with ID: %d not found", id)));
        ownerInfoRepository.delete(ownerInfo);
    }
}
