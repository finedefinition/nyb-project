package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.repository.projections.OwnerInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerInfoRepository extends JpaRepository<OwnerInfo, Long> {
    @Query("SELECT o.id FROM OwnerInfo o WHERE o.email = :email AND o.telephone = :telephone")
    Long findIdByEmailAndTelephone(@Param("email") String email, @Param("telephone") String telephone);

    @Query("""
            SELECT new com.norwayyachtbrockers.repository.projections.OwnerInfoProjection(
            o.id,
            o.firstName,
            o.lastName,
            o.telephone,
            o.email,
            o.createdAt,
            o.updatedAt
            )
            FROM OwnerInfo o
            ORDER BY o.id ASC
            """)
    List<OwnerInfoProjection> findAllProjections();
}
