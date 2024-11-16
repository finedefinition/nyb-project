package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HeadsSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "heads";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        Integer[] headsRange = (Integer[]) param; // Expecting an array of integers for the range
        return (root, query, criteriaBuilder) -> {
            Join<Yacht, YachtDetail> yachtDetailJoin = root.join("yachtDetail", JoinType.LEFT); // Join with YachtDetail

            Integer minHeads = headsRange[0];
            Integer maxHeads = headsRange[1];
            List<Predicate> predicates = new ArrayList<>();

            // Apply filters for the range
            if (minHeads != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(yachtDetailJoin.get("heads"), minHeads));
            }
            if (maxHeads != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(yachtDetailJoin.get("heads"), maxHeads));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
