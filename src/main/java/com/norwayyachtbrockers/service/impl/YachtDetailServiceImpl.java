package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtDetailMapper;
import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.repository.YachtDetailRepository;
import com.norwayyachtbrockers.service.YachtDetailService;
import com.norwayyachtbrockers.util.EntityUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class YachtDetailServiceImpl implements YachtDetailService {

    private final YachtDetailRepository yachtDetailRepository;

    public YachtDetailServiceImpl(YachtDetailRepository yachtDetailRepository) {
        this.yachtDetailRepository = yachtDetailRepository;
    }

    @Override
    @Transactional
    public YachtDetail save(YachtDetailRequestDto dto) {
        return yachtDetailRepository.save(YachtDetailMapper.createYachtDetailFromDto(dto));
    }

    @Override
    public YachtDetail findId(Long id) {
        return EntityUtils.findEntityOrThrow(id, yachtDetailRepository, "YachtDetail");
    }

    @Override
    public List<YachtDetail> findAll() {
        return yachtDetailRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    @Transactional
    public YachtDetail update(YachtDetailRequestDto dto, Long id) {
        YachtDetail yachtDetail = EntityUtils.findEntityOrThrow(id, yachtDetailRepository, "YachtDetail");
        YachtDetailMapper.updateYachtDetailFromDto(yachtDetail, dto);
        return yachtDetailRepository.save(yachtDetail);
    }

    @Override
    public void deleteById(Long id) {
        YachtDetail yachtDetail = EntityUtils.findEntityOrThrow(id, yachtDetailRepository, "YachtDetail");
        yachtDetailRepository.delete(yachtDetail);
    }
}
