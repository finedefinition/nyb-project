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
public class DraftDepthSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "draftDepth";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        BigDecimal[] draftDepthRange = (BigDecimal[]) param;
        return (root, query, criteriaBuilder) -> {
            // Устанавливаем связь с сущностью YachtModel
            Join<Yacht, YachtModel> yachtModelJoin = root.join("yachtModel", JoinType.LEFT);
            BigDecimal minDraftDepth = draftDepthRange[0];
            BigDecimal maxDraftDepth = draftDepthRange[1];
            List<Predicate> predicates = new ArrayList<>();
            if (minDraftDepth != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(yachtModelJoin.get("draftDepth"), minDraftDepth));
            }
            if (maxDraftDepth != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(yachtModelJoin.get("draftDepth"), maxDraftDepth));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
