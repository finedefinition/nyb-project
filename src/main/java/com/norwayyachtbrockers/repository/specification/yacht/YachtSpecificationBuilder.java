package com.norwayyachtbrockers.repository.specification.yacht;

import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
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
    public Specification<Yacht> build(YachtSearchParametersDto searchParametersDto) {
        Specification<Yacht> specification = Specification.where(null);

        if (searchParametersDto.getMinPrice() != null || searchParametersDto.getMaxPrice() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("price")
                    .getSpecification(new BigDecimal[]{searchParametersDto.getMinPrice(),
                            searchParametersDto.getMaxPrice()}));
        }
        if (searchParametersDto.getModel() != null && !searchParametersDto.getModel().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("yachtModel")
                    .getSpecification(searchParametersDto.getModel()));
        }

        if (searchParametersDto.getMake() != null && !searchParametersDto.getMake().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("make")
                    .getSpecification(searchParametersDto.getMake()));
        }

        if (searchParametersDto.getCountry() != null && !searchParametersDto.getCountry().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("country")
                    .getSpecification(searchParametersDto.getCountry()));
        }
        if (searchParametersDto.getTown() != null && !searchParametersDto.getTown().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("town")
                    .getSpecification(searchParametersDto.getTown()));
        }
        if (searchParametersDto.getKeelType() != null && !searchParametersDto.getKeelType().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("keelType")
                    .getSpecification(searchParametersDto.getKeelType()));
        }
        if (searchParametersDto.getFuelType() != null && !searchParametersDto.getFuelType().isEmpty()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("fuelType")
                    .getSpecification(searchParametersDto.getFuelType()));
        }
        if (searchParametersDto.getMinYear() != null || searchParametersDto.getMaxYear() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("year")
                    .getSpecification(new Integer[]{searchParametersDto.getMinYear(),
                            searchParametersDto.getMaxYear()}));
        }
        if (searchParametersDto.getMinLengthOverall() != null || searchParametersDto.getMaxLengthOverall() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("lengthOverall")
                    .getSpecification(new BigDecimal[]{searchParametersDto.getMinLengthOverall(),
                            searchParametersDto.getMaxLengthOverall()}));
        }
        if (searchParametersDto.getMinBeamWidth() != null || searchParametersDto.getMaxBeamWidth() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("beamWidth")
                    .getSpecification(new BigDecimal[]{searchParametersDto.getMinBeamWidth(),
                            searchParametersDto.getMaxBeamWidth()}));
        }
        if (searchParametersDto.getMinDraftDepth() != null || searchParametersDto.getMaxDraftDepth() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("draftDepth")
                    .getSpecification(new BigDecimal[]{searchParametersDto.getMinDraftDepth(),
                            searchParametersDto.getMaxDraftDepth()}));
        }
        if (searchParametersDto.getMinCabinNumber() != null || searchParametersDto.getMaxCabinNumber() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("cabin")
                    .getSpecification(new Integer[]{searchParametersDto.getMinCabinNumber(),
                            searchParametersDto.getMaxCabinNumber()}));
        }
        if (searchParametersDto.getMinBerthNumber() != null || searchParametersDto.getMaxBerthNumber() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("berth")
                    .getSpecification(new Integer[]{searchParametersDto.getMinBerthNumber(),
                            searchParametersDto.getMaxBerthNumber()}));
        }
        if (searchParametersDto.getMinHeadsNumber() != null || searchParametersDto.getMaxHeadsNumber() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("heads")
                    .getSpecification(new Integer[]{searchParametersDto.getMinHeadsNumber(),
                            searchParametersDto.getMaxHeadsNumber()}));
        }
        if (searchParametersDto.getMinShowerNumber() != null || searchParametersDto.getMaxShowerNumber() != null) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("shower")
                    .getSpecification(new Integer[]{searchParametersDto.getMinShowerNumber(),
                            searchParametersDto.getMaxShowerNumber()}));
        }
        return specification;
    }
}
