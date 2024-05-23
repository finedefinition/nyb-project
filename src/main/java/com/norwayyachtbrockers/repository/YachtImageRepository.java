package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.YachtImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YachtImageRepository extends JpaRepository<YachtImage, Long> {
}
