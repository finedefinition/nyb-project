package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import com.norwayyachtbrockers.util.YachtSpecificationUtil;
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
        return YachtSpecificationUtil.getSpecificationOrElseThrow(param, getKey(), "name");
    }
}
