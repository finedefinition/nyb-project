package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Yacht;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YachtRepository extends JpaRepository<Yacht, Long>, JpaSpecificationExecutor<Yacht> {

    @Override
    @EntityGraph(attributePaths = {
            "yachtModel",
            "country",
            "town",
            "yachtDetail",
            "ownerInfo",
            "yachtImages"
    })
    Page<Yacht> findAll(Specification<Yacht> spec, Pageable pageable);

    @EntityGraph(attributePaths = {
            "yachtModel",
            "country",
            "town",
            "yachtDetail",
            "ownerInfo",
            "yachtImages"
    })
    Optional<Yacht> findById(Long id);

    @Query(value = """
        SELECT y.id, y.featured, y.vat_included, y.price, y.main_image_key,
               ym.make, ym.model, ym.year,
               c.name AS country_name,
               t.name AS town_name,
               y.created_at,
               y.favourites_count
        FROM yachts y
        JOIN yacht_models ym ON y.yacht_model_id = ym.id
        JOIN countries c ON y.country_id = c.id
        JOIN towns t ON y.town_id = t.id
        WHERE y.featured = true
        LIMIT 3
        """, nativeQuery = true)
    List<Object[]> findOptimizedFeaturedYachts();

}
