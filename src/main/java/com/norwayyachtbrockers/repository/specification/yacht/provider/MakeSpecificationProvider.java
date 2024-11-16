package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MakeSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "make";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        String make = (String) param; // Expecting a string parameter
        return (root, query, criteriaBuilder) -> {
            if (make == null || make.isEmpty()) {
                throw new IllegalArgumentException("Make cannot be null or empty");
            }
            // Establish a join with the YachtModel entity
            Join<Yacht, YachtModel> yachtModelJoin = root.join("yachtModel", JoinType.LEFT);
            return criteriaBuilder.equal(yachtModelJoin.get("make"), make);
        };
    }
}
