package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.YachtDetailMapper;
import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.repository.YachtDetailRepository;
import com.norwayyachtbrockers.service.YachtDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
public class YachtDetailServiceImpl implements YachtDetailService {

    private final YachtDetailRepository yachtDetailRepository;

    private final YachtDetailMapper yachtDetailMapper;

    public YachtDetailServiceImpl(YachtDetailRepository yachtDetailRepository,
                                  YachtDetailMapper yachtDetailMapper) {
        this.yachtDetailRepository = yachtDetailRepository;
        this.yachtDetailMapper = yachtDetailMapper;
    }

    @Override
    @Transactional
    public YachtDetail save(YachtDetailRequestDto dto) {
        YachtDetail yachtDetail = new YachtDetail();
        yachtDetailMapper.updateFromDto(yachtDetail, dto);
        return yachtDetailRepository.save(yachtDetail);
    }

    @Override
    public YachtDetail findId(Long id) {
        return yachtDetailRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                .format("Yacht detail with ID: %d not found", id)));
    }

    @Override
    public List<YachtDetail> findAll() {
        List<YachtDetail> yachtDetails = yachtDetailRepository.findAll();
        yachtDetails.sort(Comparator.comparing(YachtDetail::getId));
        return yachtDetails;
    }

    @Override
    @Transactional
    public YachtDetail update(YachtDetailRequestDto dto, Long id) {
        YachtDetail yachtDetail = yachtDetailRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Yacht detail with ID: %d not found", id)));
        yachtDetailMapper.updateFromDto(yachtDetail, dto);

        return yachtDetailRepository.save(yachtDetail);
    }

    @Override
    public void deleteById(Long id) {
        YachtDetail yachtDetail = yachtDetailRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Yacht detail with ID: %d not found", id)));
        yachtDetailRepository.delete(yachtDetail);
    }
}
