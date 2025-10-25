package com.berkedev.springbootblogapi.data.repository;

import com.berkedev.springbootblogapi.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * PostRepository with JpaSpecificationExecutor for dynamic filtering.
 * Most filter operations are handled by PostSpecification.withFilters()
 * Only unique/specific queries are defined here.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    /**
     * Used for: Single post retrieval (SEO)
     */
    Optional<Post> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
