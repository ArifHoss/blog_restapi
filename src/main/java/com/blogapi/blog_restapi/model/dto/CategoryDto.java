package com.blogapi.blog_restapi.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private long id;
    private String name;
    private List<Long> blogposts;
}
