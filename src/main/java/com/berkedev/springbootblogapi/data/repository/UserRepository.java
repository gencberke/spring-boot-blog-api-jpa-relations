package com.berkedev.springbootblogapi.data.repository;

import com.berkedev.springbootblogapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

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

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
