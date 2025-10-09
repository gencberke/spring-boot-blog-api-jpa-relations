package com.berkedev.springbootblogapi.data.repository;

import com.berkedev.springbootblogapi.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findBySlug(String slug);
    boolean existsBySlug(String slug);

    List<Post> findByPublished(boolean published);
    
    @Query("SELECT p FROM Post p WHERE p.published = :published ORDER BY p.publishedAt DESC")
    List<Post> findPublishedPostsOrderedByDate(@Param("published") boolean published);

    List<Post> findByAuthorId(Long authorId);

    List<Post> findByAuthorIdAndPublishedTrue(Long authorId);
    List<Post> findByAuthorIdAndPublishedFalse(Long authorId);

    long countByAuthorIdAndPublishedTrue(Long authorId);
    long countByAuthorIdAndPublishedFalse(Long authorId);

    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByPublishedAndCategoryId(boolean published, Long categoryId);

    List<Post> findByTitleContainingIgnoreCase(String keyword);
}
