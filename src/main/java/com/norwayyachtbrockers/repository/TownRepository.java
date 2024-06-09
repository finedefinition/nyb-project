package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    @Query("SELECT t.id FROM Town t WHERE t.name = :name")
    Long findIdByName(@Param("name") String name);
}
