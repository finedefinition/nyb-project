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
public class BerthSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "berth";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        Integer[] berthRange = (Integer[]) param;
        return (root, query, criteriaBuilder) -> {
            // Убедитесь, что используете обычный join
            Join<Yacht, YachtDetail> yachtDetailJoin = root.join("yachtDetail", JoinType.LEFT);
            Integer minBerth = berthRange[0];
            Integer maxBerth = berthRange[1];
            List<Predicate> predicates = new ArrayList<>();
            if (minBerth != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(yachtDetailJoin.get("berth"), minBerth));
            }
            if (maxBerth != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(yachtDetailJoin.get("berth"), maxBerth));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
