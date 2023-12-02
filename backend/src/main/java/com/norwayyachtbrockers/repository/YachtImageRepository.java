package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface YachtImageRepository extends JpaRepository<YachtImage, Long> {
}
