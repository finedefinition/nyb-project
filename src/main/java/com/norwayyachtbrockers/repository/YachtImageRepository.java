package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.repository.projections.YachtImageProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YachtImageRepository extends JpaRepository<YachtImage, Long> {

    @Query("""
            SELECT new com.norwayyachtbrockers.repository.projections.YachtImageProjection(
            yi.id,
            yi.imageKey,
            yi.imageIndex,
            y.id
            )
            FROM YachtImage yi
            LEFT JOIN yi.yacht y
            ORDER BY yi.id ASC
            """)
    List<YachtImageProjection> findAllProjections();
}
