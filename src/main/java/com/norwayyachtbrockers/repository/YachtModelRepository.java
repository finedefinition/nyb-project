package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.YachtModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface YachtModelRepository extends JpaRepository<YachtModel, Long> {
    List<YachtModel> findByKeelType_Id(Long keelTypeId);

    List<YachtModel> findByFuelType_Id(Long fuelTypeId);

    List<YachtModel> findByMake(@Param("make") String make);

    @Query("SELECT ym.id FROM YachtModel ym WHERE ym.make = :make AND ym.model = :model AND ym.year = :year")
    Long findIdByMakeModelYear(@Param("make") String make, @Param("model") String model, @Param("year") Integer year);
}
