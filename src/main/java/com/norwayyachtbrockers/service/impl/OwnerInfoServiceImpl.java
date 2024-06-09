package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.OwnerInfoMapper;
import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.repository.OwnerInfoRepository;
import com.norwayyachtbrockers.service.OwnerInfoService;
import com.norwayyachtbrockers.util.EntityUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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
        return ownerInfoRepository.save(ownerInfoMapper.createOwnerInfoFromDto(dto));
    }

    @Override
    public OwnerInfo save(OwnerInfo ownerInfo) {
        return ownerInfoRepository.save(ownerInfo);
    }

    @Override
    public OwnerInfo findId(Long id) {
        return EntityUtils.findEntityOrThrow(id, ownerInfoRepository, "OwnerInfo");
    }

    @Override
    public List<OwnerInfo> findAll() {
        return ownerInfoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public OwnerInfo update(OwnerInfoRequestDto dto, Long id) {
        OwnerInfo ownerInfo = EntityUtils.findEntityOrThrow(id, ownerInfoRepository, "OwnerInfo");
        ownerInfoMapper.updateOwnerInfoFromDto(ownerInfo, dto);
        return ownerInfo;
    }

    @Override
    public void deleteById(Long id) {
        OwnerInfo ownerInfo = EntityUtils.findEntityOrThrow(id, ownerInfoRepository, "OwnerInfo");
        ownerInfoRepository.delete(ownerInfo);
    }

    @Override
    public Long getOwnerInfoIdByEmailAndTelephone(String email, String telephone) {
        return ownerInfoRepository.findIdByEmailAndTelephone(email, telephone);
    }
}
