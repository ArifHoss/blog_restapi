package com.blogapi.blog_restapi.repository;


import com.blogapi.blog_restapi.model.pojo.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Author, Long> {
    @Query("SELECT e FROM Author e WHERE e.email=?1")
    Optional<Author> findByEmail(String email);

    boolean existsByEmail(String email);
}
