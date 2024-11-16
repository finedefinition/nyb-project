package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class LengthOverallSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "lengthOverall";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        BigDecimal[] lengthRange = (BigDecimal[]) param;
        return (root, query, criteriaBuilder) -> {
            // Establish a join with the YachtModel entity
            Join<Yacht, YachtModel> yachtModelJoin = root.join("yachtModel", JoinType.LEFT);
            BigDecimal minLength = lengthRange[0];
            BigDecimal maxLength = lengthRange[1];
            List<Predicate> predicates = new ArrayList<>();
            if (minLength != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(yachtModelJoin.get("lengthOverall"), minLength));
            }
            if (maxLength != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(yachtModelJoin.get("lengthOverall"), maxLength));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
