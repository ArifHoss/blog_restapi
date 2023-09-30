package com.blogapi.blog_restapi.repository;


import com.blogapi.blog_restapi.model.pojo.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Blogpost, Long> {
}
