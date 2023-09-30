package com.blogapi.blog_restapi.services;



import com.blogapi.blog_restapi.model.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category create(Category category);
}
