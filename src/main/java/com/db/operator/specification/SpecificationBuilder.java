package com.db.operator.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.Map;

public class SpecificationBuilder<T> {

    public Specification<T> build(Map<String, String> filters) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String field = entry.getKey();
                String value = entry.getValue();

                if (value != null && !value.isBlank()) {
                    // простая equals‑фильтрация
                    predicate = cb.and(predicate, cb.equal(root.get(field), value));
                }
            }
            return predicate;
        };
    }
}
