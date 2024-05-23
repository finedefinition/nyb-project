package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Keel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeelRepository extends JpaRepository<Keel, Long> {
}
