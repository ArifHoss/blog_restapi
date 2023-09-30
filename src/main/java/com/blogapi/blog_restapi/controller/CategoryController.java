package com.blogapi.blog_restapi.controller;


import com.blogapi.blog_restapi.model.dto.CategoryDto;
import com.blogapi.blog_restapi.model.dto.CategoryMapper;
import com.blogapi.blog_restapi.model.pojo.Category;
import com.blogapi.blog_restapi.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    
    private final CategoryMapper categoryMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryService.getAllCategories();

        return categories.stream()
                .map(categoryMapper::toCategoryDto).toList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toCategoryDto(categoryService.create(category));
    }

}
