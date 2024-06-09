package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.OwnerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerInfoRepository extends JpaRepository<OwnerInfo, Long> {
    @Query("SELECT o.id FROM OwnerInfo o WHERE o.email = :email AND o.telephone = :telephone")
    Long findIdByEmailAndTelephone(@Param("email") String email, @Param("telephone") String telephone);
}
