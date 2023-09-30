package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.model.pojo.Category;

public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
    Category toCategory(CategoryDto dto);

}
