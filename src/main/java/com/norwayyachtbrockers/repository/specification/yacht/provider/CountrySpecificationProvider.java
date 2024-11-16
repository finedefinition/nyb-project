package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CountrySpecificationProvider implements SpecificationProvider<Yacht> {
    @Override
    public String getKey() {
        return "country";
    }

    @Override
    public Specification<Yacht> getSpecification(Object param) {
        String countryName = (String) param; // Ожидаем строковый параметр
        return (root, query, criteriaBuilder) -> {
            if (countryName == null || countryName.isEmpty()) {
                throw new IllegalArgumentException("Country name cannot be null or empty");
            }
            // Устанавливаем связь с сущностью Country и фильтруем по полю name
            Join<Yacht, Country> countryJoin = root.join("country", JoinType.LEFT);
            return criteriaBuilder.equal(countryJoin.get("name"), countryName);
        };
    }
}