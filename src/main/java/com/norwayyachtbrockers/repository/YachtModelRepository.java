package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.projections.YachtModelProjection;
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

    @Query("""
            SELECT new com.norwayyachtbrockers.repository.projections.YachtModelProjection(
            ym.id,
            ym.make,
            ym.model,
            ym.year,
            ym.lengthOverall,
            ym.beamWidth,
            ym.draftDepth,
            kt.id,
            kt.name,
            kt.createdAt,
            kt.updatedAt,
            ft.id,
            ft.name,
            ft.createdAt,
            ft.updatedAt,
            ym.createdAt,
            ym.updatedAt
        )
        FROM YachtModel ym
        LEFT JOIN ym.keelType kt
        LEFT JOIN ym.fuelType ft
        ORDER BY ym.id ASC
        """)
    List<YachtModelProjection> findAllProjections();
}
