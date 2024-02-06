package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FuelTypeSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "fuelType";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        return (root, query, criteriaBuilder)
                -> root.get("yachtModel").get("fuelType").get("name").in(param);
    }
}
