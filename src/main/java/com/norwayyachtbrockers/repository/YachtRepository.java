package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Yacht;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

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
            "yachtImages",
            "favouritedByUsers"
    })
    Page<Yacht> findAll(Specification<Yacht> spec, Pageable pageable);

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
