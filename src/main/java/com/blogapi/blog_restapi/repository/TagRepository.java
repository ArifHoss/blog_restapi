package com.blogapi.blog_restapi.repository;

import com.blogapi.blog_restapi.model.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
