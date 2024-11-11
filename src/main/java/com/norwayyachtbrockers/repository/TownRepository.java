package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.repository.projections.TownProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    @Query("SELECT t.id FROM Town t WHERE t.name = :name")
    Long findIdByName(@Param("name") String name);

    @Query("""
            SELECT new com.norwayyachtbrockers.repository.projections.TownProjection(
            t.id,
            t.name,
            c.name
            )
            FROM Town t
            LEFT JOIN t.country c
            ORDER BY t.id ASC
            """)
    List<TownProjection> findAllProjections();
}
