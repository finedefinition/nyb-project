package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class KeelTypeSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "keelType";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        String keelTypeName = (String) param; // Expecting a string parameter
        return (root, query, criteriaBuilder) -> {
            if (keelTypeName == null || keelTypeName.isEmpty()) {
                throw new IllegalArgumentException("Keel type name cannot be null or empty");
            }

            // Join with YachtModel and then with KeelType to access the "name" field
            Join<Yacht, YachtModel> yachtModelJoin = root.join("yachtModel", JoinType.LEFT);
            Join<YachtModel, Keel> keelJoin = yachtModelJoin.join("keelType", JoinType.LEFT);

            return criteriaBuilder.equal(keelJoin.get("name"), keelTypeName);
        };
    }
}
