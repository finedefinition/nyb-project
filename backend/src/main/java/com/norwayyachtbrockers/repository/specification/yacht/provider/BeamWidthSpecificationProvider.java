package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BeamWidthSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "beamWidth";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        if (param instanceof Integer[] range) {
            if (range[1] == null) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.join("yachtModel").get("beamWidth"), range[0]);
            } else if (range[0] == null) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.join("yachtModel").get("beamWidth"), range[1]);
            }
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.join("yachtModel").get("beamWidth"), range[0], range[1]);
        } else {
            throw new IllegalArgumentException("Invalid parameter type for beam width range");
        }
    }
}
