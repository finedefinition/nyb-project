package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "price";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        BigDecimal[] priceRange = (BigDecimal[]) param;
        // Теперь используйте priceRange в реализации
        return (root, query, criteriaBuilder) -> {
            BigDecimal minPrice = priceRange[0];
            BigDecimal maxPrice = priceRange[1];
            List<Predicate> predicates = new ArrayList<>();
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
