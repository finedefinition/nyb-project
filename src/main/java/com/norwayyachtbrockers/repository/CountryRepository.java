package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("SELECT c.id FROM Country c WHERE c.name = :name")
    Long findIdByName(@Param("name") String name);
}
