package com.norwayyachtbrockers.repository.specification;

import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(YachtSearchParametersDto yachtSearchParametersDto);
}
