package com.norwayyachtbrockers.repository;

import com.norwayyachtbrockers.model.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Long> {

    List<Boat> findByBoatName(String boatName);
}
