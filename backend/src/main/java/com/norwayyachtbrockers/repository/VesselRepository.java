package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, Long> {

}
