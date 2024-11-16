package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class YearSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "year";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        Integer[] yearRange = (Integer[]) param; // Expecting a range of integers
        return (root, query, criteriaBuilder) -> {
            // Establish a join with the YachtModel entity
            Join<Yacht, YachtModel> yachtModelJoin = root.join("yachtModel", JoinType.LEFT);
            Integer minYear = yearRange[0];
            Integer maxYear = yearRange[1];
            List<Predicate> predicates = new ArrayList<>();
            if (minYear != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(yachtModelJoin.get("year"), minYear));
            }
            if (maxYear != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(yachtModelJoin.get("year"), maxYear));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
