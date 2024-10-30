package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.repository.projections.CountryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("SELECT c.id FROM Country c WHERE c.name = :name")
    Long findIdByName(@Param("name") String name);

    @Query("""
            SELECT new com.norwayyachtbrockers.repository.projections.CountryProjection(
            c.id,
            c.name,
            c.createdAt,
            c.updatedAt
            )
            FROM Country c
            ORDER BY c.id ASC
            """)
    List<CountryProjection> findAllProjections();
}
