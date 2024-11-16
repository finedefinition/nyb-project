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
public class ShowerSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "shower";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        Integer[] showerRange = (Integer[]) param; // Expecting a range of integers
        return (root, query, criteriaBuilder) -> {
            // Establish a join with the YachtDetail entity
            Join<Yacht, YachtDetail> yachtDetailJoin = root.join("yachtDetail", JoinType.LEFT);
            Integer minShower = showerRange[0];
            Integer maxShower = showerRange[1];
            List<Predicate> predicates = new ArrayList<>();
            if (minShower != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(yachtDetailJoin.get("shower"), minShower));
            }
            if (maxShower != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(yachtDetailJoin.get("shower"), maxShower));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
