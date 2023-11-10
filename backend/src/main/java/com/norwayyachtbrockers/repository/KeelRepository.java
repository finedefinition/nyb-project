package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Keel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "keels", path = "keels")
public interface KeelRepository extends JpaRepository<Keel, Long> {
}
