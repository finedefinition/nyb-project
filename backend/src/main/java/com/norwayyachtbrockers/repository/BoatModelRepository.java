package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.BoatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "boatmodels", path = "boatmodels")
public interface BoatModelRepository extends JpaRepository<BoatModel, Long> {

    void deleteById(Long theId);
    List<BoatModel> findByKeelTypeId(@Param("keelType") Long keelTypeId);

    List<BoatModel> findByFuelTypeId(@Param("fuelType") Long keelTypeId);

}
