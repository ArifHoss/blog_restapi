package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.exception.ResourceNotFoundException;
import com.blogapi.blog_restapi.model.pojo.Blogpost;
import com.blogapi.blog_restapi.model.pojo.Category;
import com.blogapi.blog_restapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {

    private final PostRepository postRepository;

    @Override
    public CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        if (category.getBlogposts() != null) {
            List<Long> blogIDs = category.getBlogposts().stream().map(Blogpost::getId).collect(Collectors.toList());
            dto.setBlogposts(blogIDs);
        }
        return dto;
    }

    @Override
    public Category toCategory(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        // if the category has blogposts, we need to fetch them from the database
        // and set them to the category object
        if (dto.getBlogposts() != null) {
            List<Blogpost> blogposts = dto.getBlogposts().stream()
                    .map(blogpostId -> postRepository.findById(blogpostId)
                            .orElseThrow(() -> new ResourceNotFoundException("Blogpost with id " + blogpostId + " not found")))
                    .collect(Collectors.toList());
            category.setBlogposts(blogposts);
        }
        return category;
    }

}
