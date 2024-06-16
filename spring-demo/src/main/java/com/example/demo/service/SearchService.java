package com.example.demo.service;

import com.example.demo.repository.specification.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchService {
    public Predicate toPredicate(Path<?> path, CriteriaBuilder builder, SearchCriteria criteria) {
        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(path.get(criteria.getKey()), criteria.getValue());
            case NEGATION -> builder.notEqual(path.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN -> builder.greaterThan(path.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN -> builder.lessThan(path.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE -> builder.like(path.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        };
    }
}
