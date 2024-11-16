package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
        String fuelTypeName = (String) param; // Expecting a string parameter
        return (root, query, criteriaBuilder) -> {
            if (fuelTypeName == null || fuelTypeName.isEmpty()) {
                throw new IllegalArgumentException("Fuel type name cannot be null or empty");
            }
            // Join with YachtModel and then with Fuel to access the "name" field
            Join<Yacht, YachtModel> yachtModelJoin = root.join("yachtModel", JoinType.LEFT);
            Join<YachtModel, Fuel> fuelJoin = yachtModelJoin.join("fuelType", JoinType.LEFT);
            return criteriaBuilder.equal(fuelJoin.get("name"), fuelTypeName);
        };
    }
}
