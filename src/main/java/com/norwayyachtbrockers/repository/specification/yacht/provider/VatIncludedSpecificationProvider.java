package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VatIncludedSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "vatIncluded";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        Boolean isVatIncluded = (Boolean) param;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("vatIncluded"), isVatIncluded);
    }
}
