package com.blogapi.blog_restapi.repository;


import com.blogapi.blog_restapi.model.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

