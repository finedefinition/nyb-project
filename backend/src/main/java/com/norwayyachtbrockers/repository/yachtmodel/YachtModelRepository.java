package com.norwayyachtbrockers.repository.yachtmodel;

import com.norwayyachtbrockers.model.YachtModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface YachtModelRepository extends JpaRepository<YachtModel, Long> {
    // Find YachtModels by Keel Type ID
    List<YachtModel> findByKeelType_Id(Long keelTypeId);

    // Find YachtModels by Fuel Type ID
    List<YachtModel> findByFuelType_Id(Long fuelTypeId);
}
