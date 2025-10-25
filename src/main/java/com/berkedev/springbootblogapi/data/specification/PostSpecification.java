package com.berkedev.springbootblogapi.data.specification;

import com.berkedev.springbootblogapi.data.dto.request.PostFilterRequest;
import com.berkedev.springbootblogapi.data.entity.Post;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {

    /**
     * Creates dynamic query based on filter criteria
     * Uses JPA Criteria API to build WHERE clauses
     */
    public static Specification<Post> withFilters(PostFilterRequest filterRequest) {
        return ((root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Add author filter if provided
            if (filterRequest.getAuthorId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("author").get("id"), filterRequest.getAuthorId())
                );
            }

            // Add category filter if provided
            if (filterRequest.getCategoryId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("category").get("id"), filterRequest.getCategoryId())
                );
            }

            // Add tag filter if provided
            if (filterRequest.getTagId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.join("tags").get("id"), filterRequest.getTagId())
                );
            }

            // Add published filter if provided
            if (filterRequest.getPublished() != null) {
                predicates.add(criteriaBuilder.equal(root.get("published"),filterRequest.getPublished())
                );
            }

            // combine all predicates with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
