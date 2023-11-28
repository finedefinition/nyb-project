package com.norwayyachtbrockers.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.norwayyachtbrockers.dto.mapper.VesselMapper;
import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.repository.VesselRepository;
import com.norwayyachtbrockers.service.VesselService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VesselServiceImpl implements VesselService {

    private final VesselRepository vesselRepository;

    private final VesselMapper vesselMapper;

    private final AmazonS3 amazonS3;
    private final String s3BucketName;

    public VesselServiceImpl(VesselRepository vesselRepository, VesselMapper vesselMapper,
                             AmazonS3 amazonS3, @Value("${s3.bucket.name}") String s3BucketName) {
        this.vesselRepository = vesselRepository;
        this.vesselMapper = vesselMapper;
        this.amazonS3 = amazonS3;
        this.s3BucketName = s3BucketName;
    }

    @Transactional
    @Override
    public Vessel findById(Long vesselId) {
        return vesselRepository.findById(vesselId)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot find the vessel with ID: %d", vesselId)));
    }

    @Override
    public List<Vessel> findAll() {
        return vesselRepository.findAll();
    }

    @Transactional
    @Override
    public Vessel save(Vessel vessel, MultipartFile imageFile) {
        vessel.setCreatedAt(LocalDateTime.now());
        setImageKeyForVessel(vessel, imageFile);
        return vesselRepository.save(vessel);
    }

    @Transactional
    @Override
    public Vessel update(Long theId, VesselRequestDto vesselData, MultipartFile imageFile) {
        Vessel existingVessel = vesselRepository.findById(theId)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                        .format("Cannot update the vessel with ID: %d", theId)));

        vesselMapper.updateVesselFromDto(existingVessel, vesselData);

        setImageKeyForVessel(existingVessel, imageFile);

        return vesselRepository.save(existingVessel);
    }

    private void setImageKeyForVessel(Vessel vessel, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageKey = uploadImageToS3(imageFile);
            vessel.setImageKey(imageKey);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long theId) {
        vesselRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String.format("Cannot delete the vessel with ID: %d", theId)));
        vesselRepository.deleteById(theId);
    }

    @Transactional
    @Override
    public void deleteAll() {
        vesselRepository.deleteAll();
    }

    private String uploadImageToS3(MultipartFile imageFile) {
        String imageKey = UUID.randomUUID().toString();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imageFile.getContentType());
            amazonS3.putObject(s3BucketName, imageKey, imageFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to S3: " + e.getMessage());
        }
        return imageKey;
    }
}
