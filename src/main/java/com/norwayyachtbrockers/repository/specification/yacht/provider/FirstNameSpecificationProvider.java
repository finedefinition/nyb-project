package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FirstNameSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "ownerFirstName";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        String firstName = (String) param; // Expecting a string parameter
        return (root, query, criteriaBuilder) -> {
            if (firstName == null || firstName.isEmpty()) {
                throw new IllegalArgumentException("Owner first name cannot be null or empty");
            }
            // Establish a join with the OwnerInfo entity
            Join<Yacht, OwnerInfo> ownerInfoJoin = root.join("ownerInfo", JoinType.LEFT);
            return criteriaBuilder.equal(ownerInfoJoin.get("firstName"), firstName);
        };
    }
}