package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import com.norwayyachtbrockers.util.YachtSpecificationUtil;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class LengthOverallSpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "lengthOverall";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        return YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(
                (BigDecimal[]) param, "yachtModel", getKey()
        );
    }
}
