package com.norwayyachtbrockers.repository.yachtmodel;

import com.norwayyachtbrockers.model.YachtModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface YachtModelRepository extends JpaRepository<YachtModel, Long> {
    List<YachtModel> findByKeelType_Id(Long keelTypeId);

    List<YachtModel> findByFuelType_Id(Long fuelTypeId);

    List<YachtModel> findByMake(@Param("make") String make);
}
