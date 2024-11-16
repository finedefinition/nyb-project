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
public class CabinSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "cabin";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        Integer[] cabinRange = (Integer[]) param;
        return (root, query, criteriaBuilder) -> {
            // Устанавливаем связь с сущностью YachtDetail
            Join<Yacht, YachtDetail> yachtDetailJoin = root.join("yachtDetail", JoinType.LEFT);
            Integer minCabin = cabinRange[0];
            Integer maxCabin = cabinRange[1];
            List<Predicate> predicates = new ArrayList<>();
            if (minCabin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(yachtDetailJoin.get("cabin"), minCabin));
            }
            if (maxCabin != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(yachtDetailJoin.get("cabin"), maxCabin));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
