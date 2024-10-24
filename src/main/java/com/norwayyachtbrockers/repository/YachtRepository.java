package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.projections.YachtShortProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YachtRepository extends JpaRepository<Yacht, Long>, JpaSpecificationExecutor<Yacht> {

    @Query("""
        SELECT new com.norwayyachtbrockers.repository.projections.YachtShortProjection(
            y.id,
            y.vatIncluded,
            y.price,
            y.priceOld,
            y.mainImageKey,
            ym.make,
            ym.model,
            ym.year,
            c.name,
            t.name,
            y.createdAt,
            COUNT(fu.id)
        )
        FROM Yacht y
        LEFT JOIN y.yachtModel ym
        LEFT JOIN y.country c
        LEFT JOIN y.town t
        LEFT JOIN y.favouritedByUsers fu
        GROUP BY y.id, y.vatIncluded, y.price, y.priceOld, y.mainImageKey,
                 ym.make, ym.model, ym.year, c.name, t.name, y.createdAt
    """)
    Page<YachtShortProjection> findAllProjected(Pageable pageable);

    @EntityGraph(attributePaths = {
            "yachtModel",
            "country",
            "town",
            "yachtDetail",
            "ownerInfo",
            "yachtImages",
            "favouritedByUsers" // Если нужно
    })
    Optional<Yacht> findById(Long id);
}
