package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface YachtRepository extends JpaRepository<Yacht, Long> {
}
