package com.berkedev.springbootblogapi.data.repository;

import com.berkedev.springbootblogapi.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * We create custom query methods for unique fields.
     * Spring Data JPA automatically converts these method names to JPQL queries
     * by parsing the method name into SQL keywords. For example:
     * "find" converts to "SELECT"
     * "existsBy" converts to "SELECT COUNT(*) > 0"
     * Spring creates a proxy implementation class (which we don't see directly)
     * that gets injected when we use this repository.
     * However, we still may need to hard code some sql codes for complex queries.
     * Or if method name become too long.
     */

    // Find operations
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthorId(Long authorId);
    
    // Count operations
    long countByPostId(Long postId);
    long countByAuthorId(Long authorId);
    
    // Ordered queries - using JPQL for better readability
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.createdAt DESC")
    List<Comment> findPostCommentsOrderedByDate(@Param("postId") Long postId);
    
    @Query("SELECT c FROM Comment c WHERE c.author.id = :authorId ORDER BY c.createdAt DESC")
    List<Comment> findUserCommentsOrderedByDate(@Param("authorId") Long authorId);
}
