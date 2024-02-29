package com.norwayyachtbrockers.util;

import com.norwayyachtbrockers.model.Yacht;
import org.springframework.data.jpa.domain.Specification;

public class YachtSpecificationUtil {

    public static <T> Specification<Yacht> getSpecificationOrElseThrow(T value, String key, String keyParam) {
        if (value != null) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(key).get(keyParam), value);
        } else {
            throw new IllegalArgumentException(String.format("Invalid parameter type for %s", key));
        }
    }

    public static <T> Specification<Yacht> getSpecificationOrElseThrow(T value, String model, String key, String keyParam) {
        if (value != null) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(model).get(key).get(keyParam), value);
        } else {
            throw new IllegalArgumentException(String.format("Invalid parameter type for %s", key));
        }
    }

    public static <T extends Comparable<? super T>> Specification<Yacht>
    getSpecificationInRangeOrElseThrow(T[] range, String key, String keyParam) {
        if (range != null && range.length == 2) {
            if (range[1] == null) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get(key).get(keyParam), range[0]);
            }
            else if (range[0] == null) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get(key).get(keyParam), range[1]);
            }
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get(key).get(keyParam), range[0], range[1]);
        } else {
            throw new IllegalArgumentException(String.format("Invalid parameter type for %s range", key));
        }
    }

    public static <T extends Comparable<? super T>> Specification<Yacht>
    getSpecificationInRangeOrElseThrow(T[] range, String key) {
        if (range != null && range.length == 2) {
            if (range[1] == null) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get(key), range[0]);
            }
            else if (range[0] == null) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get(key), range[1]);
            }
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get(key), range[0], range[1]);
        } else {
            throw new IllegalArgumentException(String.format("Invalid parameter type for %s range", key));
        }
    }
}
