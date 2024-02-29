package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.YachtDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface YachtDetailRepository extends JpaRepository<YachtDetail, Long> {
}
