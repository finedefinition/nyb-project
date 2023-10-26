package com.norwayyachtbrockers.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import com.norwayyachtbrockers.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VesselServiceImpl implements VesselService {

    private final VesselRepository vesselRepository;
    private final AmazonS3 amazonS3;
    private final String s3BucketName;

    public VesselServiceImpl(VesselRepository vesselRepository, AmazonS3 amazonS3,
                             @Value("${s3.bucket.name}") String s3BucketName) {
        this.vesselRepository = vesselRepository;
        this.amazonS3 = amazonS3;
        this.s3BucketName = s3BucketName;
    }

    @Override
    public Vessel save(Vessel vessel, MultipartFile imageFile) {
        vessel.setCreatedAt(LocalDateTime.now());
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageKey = uploadImageToS3(imageFile);
            vessel.setImageKey(imageKey);
        }
        return vesselRepository.save(vessel);
    }

    @Override
    public Vessel updateVessel(Long vesselId, boolean featuredVessel, String vesselMake, String vesselModel,
                               BigDecimal vesselPrice, int vesselYear, String vesselLocationCountry,
                               String vesselLocationState, BigDecimal vesselLengthOverall, BigDecimal vesselBeam,
                               BigDecimal vesselDraft, int vesselCabin, int vesselBerth, KeelType keelType,
                               FuelType fuelType, int engineQuantity, String vesselDescription,
                               MultipartFile imageFile) {
        Vessel existingVessel = vesselRepository.findById(vesselId)
                .orElseThrow(() ->
                        new AppEntityNotFoundException(String.format("Vessel not found with ID: %d", vesselId)));


        if (imageFile != null && !imageFile.isEmpty()) {
            String newImageKey = uploadImageToS3(imageFile);
            existingVessel.setImageKey(newImageKey);
        }

        // Update the vessel attributes (You could also use BeanUtils.copyProperties() for simplicity)
        existingVessel.setFeaturedVessel(featuredVessel);
        existingVessel.setVesselMake(vesselMake);
        existingVessel.setVesselModel(vesselModel);
        existingVessel.setVesselPrice(vesselPrice);
        existingVessel.setVesselYear(vesselYear);
        existingVessel.setVesselLocationCountry(vesselLocationCountry);
        existingVessel.setVesselLocationState(vesselLocationState);
        existingVessel.setVesselLengthOverall(vesselLengthOverall);
        existingVessel.setVesselBeam(vesselBeam);
        existingVessel.setVesselDraft(vesselDraft);
        existingVessel.setVesselCabin(vesselCabin);
        existingVessel.setVesselBerth(vesselBerth);
        existingVessel.setFuelType(fuelType);
        existingVessel.setKeelType(keelType);
        existingVessel.setEngineQuantity(engineQuantity);
        existingVessel.setVesselDescription(vesselDescription);

        return vesselRepository.save(existingVessel);
    }

    @Override
    public Vessel findById(Long theId) {
        return vesselRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String.format("Vessel not found with ID: %d", theId)));
    }

    @Override
    public List<Vessel> findAll() {
        return vesselRepository.findAll();
    }

    @Override
    public void deleteById(Long theId) {
        vesselRepository.findById(theId).orElseThrow(
                () -> new AppEntityNotFoundException(String.format("Vessel not found with ID: %d", theId)));
        vesselRepository.deleteById(theId);
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
