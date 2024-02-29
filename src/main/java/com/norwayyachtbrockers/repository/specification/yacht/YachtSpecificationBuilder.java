package com.norwayyachtbrockers.repository.specification.yacht;

import com.norwayyachtbrockers.dto.request.YahctSearchParametersDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationBuilder;
import com.norwayyachtbrockers.repository.specification.SpecificationProviderManager;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YachtSpecificationBuilder implements SpecificationBuilder<Yacht> {
    private final SpecificationProviderManager<Yacht> specificationProviderManager;

    @Override
    public Specification<Yacht> build(YahctSearchParametersDto searchParametersDto) {
        Specification<Yacht> specification = Specification.where(null);
        if (searchParametersDto.minPrice() != null || searchParametersDto.maxPrice() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("price")
                    .getSpecification(new BigDecimal[]{searchParametersDto.minPrice(),
                            searchParametersDto.maxPrice()}));
        }
        if (searchParametersDto.model() != null && !searchParametersDto.model().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("yachtModel")
                    .getSpecification(searchParametersDto.model()));
        }
        if (searchParametersDto.country() != null && !searchParametersDto.country().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("country")
                    .getSpecification(searchParametersDto.country()));
        }
        if (searchParametersDto.town() != null && !searchParametersDto.town().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("town")
                    .getSpecification(searchParametersDto.town()));
        }
        if (searchParametersDto.keelType() != null && !searchParametersDto.keelType().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("keelType")
                    .getSpecification(searchParametersDto.keelType()));
        }
        if (searchParametersDto.fuelType() != null && !searchParametersDto.fuelType().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("fuelType")
                    .getSpecification(searchParametersDto.fuelType()));
        }
        if (searchParametersDto.minYear() != null || searchParametersDto.maxYear() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("year")
                    .getSpecification(new Integer[]{searchParametersDto.minYear(),
                            searchParametersDto.maxYear()}));
        }
        if (searchParametersDto.minLengthOverall() != null || searchParametersDto.maxLengthOverall() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("lengthOverall")
                    .getSpecification(new BigDecimal[]{searchParametersDto.minLengthOverall(),
                            searchParametersDto.maxLengthOverall()}));
        }
        if (searchParametersDto.minBeamWidth() != null || searchParametersDto.maxBeamWidth() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("beamWidth")
                    .getSpecification(new BigDecimal[]{searchParametersDto.minBeamWidth(),
                            searchParametersDto.maxBeamWidth()}));
        }
        if (searchParametersDto.minDraftDepth() != null || searchParametersDto.maxDraftDepth() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("draftDepth")
                    .getSpecification(new BigDecimal[]{searchParametersDto.minDraftDepth(),
                            searchParametersDto.maxDraftDepth()}));
        }
        if (searchParametersDto.minCabinNumber() != null || searchParametersDto.maxCabinNumber() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("cabin")
                    .getSpecification(new Integer[]{searchParametersDto.minCabinNumber(),
                            searchParametersDto.maxCabinNumber()}));
        }
        if (searchParametersDto.minBerthNumber() != null || searchParametersDto.maxBerthNumber() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("berth")
                    .getSpecification(new Integer[]{searchParametersDto.minBerthNumber(),
                            searchParametersDto.maxBerthNumber()}));
        }
        if (searchParametersDto.minHeadsNumber() != null || searchParametersDto.maxHeadsNumber() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("heads")
                    .getSpecification(new Integer[]{searchParametersDto.minHeadsNumber(),
                            searchParametersDto.maxHeadsNumber()}));
        }
        if (searchParametersDto.minShowerNumber() != null || searchParametersDto.maxShowerNumber() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("shower")
                    .getSpecification(new Integer[]{searchParametersDto.minShowerNumber(),
                            searchParametersDto.maxShowerNumber()}));
        }
        return specification;
    }
}
