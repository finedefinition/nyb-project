package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FeaturedSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "featured";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        Boolean isFeatured = (Boolean) param;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("featured"), isFeatured);
    }
}
