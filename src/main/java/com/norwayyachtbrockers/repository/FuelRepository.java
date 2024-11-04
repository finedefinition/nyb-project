package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.repository.projections.FuelProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {

    @Query("SELECT f.id FROM Fuel f WHERE f.name = :name")
    Long findIdByName(@Param("name") String name);

    @Query("""
            SELECT new com.norwayyachtbrockers.repository.projections.FuelProjection(
            f.id,
            f.name,
            f.createdAt,
            f.updatedAt
            )
            FROM Fuel f
            ORDER BY f.id ASC
            """)
    List<FuelProjection> findAllProjections();
}
