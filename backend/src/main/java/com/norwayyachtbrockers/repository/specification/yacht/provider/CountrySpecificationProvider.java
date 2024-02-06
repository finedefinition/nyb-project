package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CountrySpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "country";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        if (param instanceof String countryName) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("country").get("name"), countryName);
        } else {
            throw new IllegalArgumentException("Invalid parameter type for country name");
        }
    }
}