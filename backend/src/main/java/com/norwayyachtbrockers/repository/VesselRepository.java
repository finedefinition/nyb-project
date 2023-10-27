package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;


@RepositoryRestResource
public interface VesselRepository extends JpaRepository<Vessel, Long> {
    List<Vessel> findAll();
}
