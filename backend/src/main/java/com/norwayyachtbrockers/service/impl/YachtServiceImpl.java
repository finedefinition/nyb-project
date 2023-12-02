package com.norwayyachtbrockers.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.norwayyachtbrockers.dto.mapper.YachtMapper;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.repository.YachtRepository;
import com.norwayyachtbrockers.service.YachtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class YachtServiceImpl implements YachtService {

    private final YachtRepository yachtRepository;
    private final YachtMapper yachtMapper;

    private final AmazonS3 amazonS3;
    private final String s3BucketName;

    public YachtServiceImpl(YachtRepository yachtRepository, YachtMapper yachtMapper,
                            AmazonS3 amazonS3, @Value("${s3.bucket.name}") String s3BucketName) {
        this.yachtRepository = yachtRepository;
        this.yachtMapper = yachtMapper;
        this.amazonS3 = amazonS3;
        this.s3BucketName = s3BucketName;
    }

    @Override
    public YachtResponseDto save(YachtRequestDto dto) {
        return null;
    }

    @Override
    public YachtResponseDto findId(Long id) {
        return null;
    }

    @Override
    public List<YachtResponseDto> findAll() {
        return null;
    }

    @Override
    public YachtResponseDto update(YachtRequestDto dto, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
