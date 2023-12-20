package com.norwayyachtbrockers.util;

import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityUtils {

    public static <T> T findEntityOrThrow(Long id, JpaRepository<T, Long> repository, String entityName) {
        return repository.findById(id).orElseThrow(() ->
                new AppEntityNotFoundException(String.format("%s with ID: %d not found", entityName, id))
        );
    }
}

