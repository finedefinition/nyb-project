package com.norwayyachtbrockers.repository.specification.yacht;

import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationBuilder;
import com.norwayyachtbrockers.repository.specification.SpecificationProviderManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YachtSpecificationBuilder implements SpecificationBuilder<Yacht> {

    private final SpecificationProviderManager<Yacht> specificationProviderManager;

    @Override
    public Specification<Yacht> build(YachtSearchParametersDto searchParametersDto) {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class && !Long.class.equals(query.getResultType())) {
                // Устанавливаем уникальность результатов, чтобы избежать дубликатов из-за JOIN
                query.distinct(true);

                // Выполняем fetch необходимых ассоциаций для предотвращения LazyInitializationException
                root.fetch("yachtModel", JoinType.LEFT);
                root.fetch("ownerInfo", JoinType.LEFT);
                root.fetch("country", JoinType.LEFT);
                root.fetch("town", JoinType.LEFT);
                root.fetch("yachtDetail", JoinType.LEFT);
                root.fetch("favouritedByUsers", JoinType.LEFT);
                root.fetch("yachtImages", JoinType.LEFT);

            }

            // Создаем список предикатов для условий поиска
            List<Predicate> predicates = new ArrayList<>();

            // Фильтрация по цене
            if (searchParametersDto.getMinPrice() != null || searchParametersDto.getMaxPrice() != null) {
                Specification<Yacht> priceSpec = specificationProviderManager
                        .getSpecificationProvider("price")
                        .getSpecification(new BigDecimal[]{
                                searchParametersDto.getMinPrice(),
                                searchParametersDto.getMaxPrice()
                        });
                predicates.add(priceSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по модели яхты
            if (searchParametersDto.getModel() != null && !searchParametersDto.getModel().isEmpty()) {
                Specification<Yacht> modelSpec = specificationProviderManager
                        .getSpecificationProvider("yachtModel")
                        .getSpecification(searchParametersDto.getModel());
                predicates.add(modelSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по производителю (марке)
            if (searchParametersDto.getMake() != null && !searchParametersDto.getMake().isEmpty()) {
                Specification<Yacht> makeSpec = specificationProviderManager
                        .getSpecificationProvider("make")
                        .getSpecification(searchParametersDto.getMake());
                predicates.add(makeSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по стране
            if (searchParametersDto.getCountry() != null && !searchParametersDto.getCountry().isEmpty()) {
                Specification<Yacht> countrySpec = specificationProviderManager
                        .getSpecificationProvider("country")
                        .getSpecification(searchParametersDto.getCountry());
                predicates.add(countrySpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по городу
            if (searchParametersDto.getTown() != null && !searchParametersDto.getTown().isEmpty()) {
                Specification<Yacht> townSpec = specificationProviderManager
                        .getSpecificationProvider("town")
                        .getSpecification(searchParametersDto.getTown());
                predicates.add(townSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по типу киля
            if (searchParametersDto.getKeelType() != null && !searchParametersDto.getKeelType().isEmpty()) {
                Specification<Yacht> keelTypeSpec = specificationProviderManager
                        .getSpecificationProvider("keelType")
                        .getSpecification(searchParametersDto.getKeelType());
                predicates.add(keelTypeSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по типу топлива
            if (searchParametersDto.getFuelType() != null && !searchParametersDto.getFuelType().isEmpty()) {
                Specification<Yacht> fuelTypeSpec = specificationProviderManager
                        .getSpecificationProvider("fuelType")
                        .getSpecification(searchParametersDto.getFuelType());
                predicates.add(fuelTypeSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по году выпуска
            if (searchParametersDto.getMinYear() != null || searchParametersDto.getMaxYear() != null) {
                Specification<Yacht> yearSpec = specificationProviderManager
                        .getSpecificationProvider("year")
                        .getSpecification(new Integer[]{
                                searchParametersDto.getMinYear(),
                                searchParametersDto.getMaxYear()
                        });
                predicates.add(yearSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по общей длине
            if (searchParametersDto.getMinLengthOverall() != null || searchParametersDto.getMaxLengthOverall() != null) {
                Specification<Yacht> lengthOverallSpec = specificationProviderManager
                        .getSpecificationProvider("lengthOverall")
                        .getSpecification(new BigDecimal[]{
                                searchParametersDto.getMinLengthOverall(),
                                searchParametersDto.getMaxLengthOverall()
                        });
                predicates.add(lengthOverallSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по ширине
            if (searchParametersDto.getMinBeamWidth() != null || searchParametersDto.getMaxBeamWidth() != null) {
                Specification<Yacht> beamWidthSpec = specificationProviderManager
                        .getSpecificationProvider("beamWidth")
                        .getSpecification(new BigDecimal[]{
                                searchParametersDto.getMinBeamWidth(),
                                searchParametersDto.getMaxBeamWidth()
                        });
                predicates.add(beamWidthSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по осадке
            if (searchParametersDto.getMinDraftDepth() != null || searchParametersDto.getMaxDraftDepth() != null) {
                Specification<Yacht> draftDepthSpec = specificationProviderManager
                        .getSpecificationProvider("draftDepth")
                        .getSpecification(new BigDecimal[]{
                                searchParametersDto.getMinDraftDepth(),
                                searchParametersDto.getMaxDraftDepth()
                        });
                predicates.add(draftDepthSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по количеству кают
            if (searchParametersDto.getMinCabinNumber() != null || searchParametersDto.getMaxCabinNumber() != null) {
                Specification<Yacht> cabinSpec = specificationProviderManager
                        .getSpecificationProvider("cabin")
                        .getSpecification(new Integer[]{
                                searchParametersDto.getMinCabinNumber(),
                                searchParametersDto.getMaxCabinNumber()
                        });
                predicates.add(cabinSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по количеству спальных мест
            if (searchParametersDto.getMinBerthNumber() != null || searchParametersDto.getMaxBerthNumber() != null) {
                Specification<Yacht> berthSpec = specificationProviderManager
                        .getSpecificationProvider("berth")
                        .getSpecification(new Integer[]{
                                searchParametersDto.getMinBerthNumber(),
                                searchParametersDto.getMaxBerthNumber()
                        });
                predicates.add(berthSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по количеству санузлов
            if (searchParametersDto.getMinHeadsNumber() != null || searchParametersDto.getMaxHeadsNumber() != null) {
                Specification<Yacht> headsSpec = specificationProviderManager
                        .getSpecificationProvider("heads")
                        .getSpecification(new Integer[]{
                                searchParametersDto.getMinHeadsNumber(),
                                searchParametersDto.getMaxHeadsNumber()
                        });
                predicates.add(headsSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по количеству душевых
            if (searchParametersDto.getMinShowerNumber() != null || searchParametersDto.getMaxShowerNumber() != null) {
                Specification<Yacht> showerSpec = specificationProviderManager
                        .getSpecificationProvider("shower")
                        .getSpecification(new Integer[]{
                                searchParametersDto.getMinShowerNumber(),
                                searchParametersDto.getMaxShowerNumber()
                        });
                predicates.add(showerSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по имени владельца
            if (searchParametersDto.getOwnerFirstName() != null && !searchParametersDto.getOwnerFirstName().isEmpty()) {
                Specification<Yacht> ownerFirstNameSpec = specificationProviderManager
                        .getSpecificationProvider("ownerFirstName")
                        .getSpecification(searchParametersDto.getOwnerFirstName());
                predicates.add(ownerFirstNameSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Фильтрация по фамилии владельца
            if (searchParametersDto.getOwnerLastName() != null && !searchParametersDto.getOwnerLastName().isEmpty()) {
                Specification<Yacht> ownerLastNameSpec = specificationProviderManager
                        .getSpecificationProvider("ownerLastName")
                        .getSpecification(searchParametersDto.getOwnerLastName());
                predicates.add(ownerLastNameSpec.toPredicate(root, query, criteriaBuilder));
            }

            // Объединяем все предикаты с помощью AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
