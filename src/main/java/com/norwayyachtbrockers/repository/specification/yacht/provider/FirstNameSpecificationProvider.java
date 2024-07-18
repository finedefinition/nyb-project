package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FirstNameSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "ownerFirstName";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        String firstName = (String) param;
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(
                    root.get("ownerInfo").get("firstName"), firstName
            );
        };
    }
}