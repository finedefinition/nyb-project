package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
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
public class BeamWidthSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "beamWidth";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        BigDecimal[] beamWidthRange = (BigDecimal[]) param;

        return (root, query, criteriaBuilder) -> {
            BigDecimal minBeamWidth = beamWidthRange[0];
            BigDecimal maxBeamWidth = beamWidthRange[1];
            List<Predicate> predicates = new ArrayList<>();

            // Предполагается, что beamWidth находится в связанной сущности yachtModel
            Join<Object, Object> yachtModelJoin = root.join("yachtModel", JoinType.LEFT);

            if (minBeamWidth != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        yachtModelJoin.get("beamWidth"), minBeamWidth));
            }
            if (maxBeamWidth != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        yachtModelJoin.get("beamWidth"), maxBeamWidth));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
