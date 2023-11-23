package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.YachtModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface YachtModelRepository extends JpaRepository<YachtModel, Long> {

    void deleteById(Long theId);
    List<YachtModel> findByKeelTypeId(@Param("keelType") Long keelTypeId);

    List<YachtModel> findByFuelTypeId(@Param("fuelType") Long keelTypeId);

}
