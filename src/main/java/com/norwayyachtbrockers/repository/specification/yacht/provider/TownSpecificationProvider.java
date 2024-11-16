package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TownSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "town";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        String townName = (String) param; // Expecting a string parameter
        return (root, query, criteriaBuilder) -> {
            if (townName == null || townName.isEmpty()) {
                throw new IllegalArgumentException("Town name cannot be null or empty");
            }
            // Establish a join with the Town entity
            Join<Yacht, Town> townJoin = root.join("town", JoinType.LEFT);
            return criteriaBuilder.equal(townJoin.get("name"), townName);
        };
    }
}