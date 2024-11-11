package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.repository.projections.YachtDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YachtDetailRepository extends JpaRepository<YachtDetail, Long> {

    @Query("""
            SELECT new com.norwayyachtbrockers.repository.projections.YachtDetailProjection(
            yd.id,
            yd.cabin,
            yd.berth,
            yd.heads,
            yd.shower,
            yd.description
            )
            FROM YachtDetail yd
            ORDER BY yd.id ASC
            """)
    List<YachtDetailProjection> findAllProjections();
}
