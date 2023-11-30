package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.OwnerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OwnerInfoRepository extends JpaRepository<OwnerInfo, Long> {
}
