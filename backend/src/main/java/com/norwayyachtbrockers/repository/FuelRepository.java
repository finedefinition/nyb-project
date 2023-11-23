package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FuelRepository extends JpaRepository<Fuel, Long> {
}
