package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Keel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeelRepository extends JpaRepository<Keel, Long> {

    @Query("SELECT k.id FROM Keel k WHERE k.name = :name")
    Long findIdByName(@Param("name") String name);
}
