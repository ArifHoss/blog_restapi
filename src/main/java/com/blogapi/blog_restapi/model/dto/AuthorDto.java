package com.blogapi.blog_restapi.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AuthorDto {
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Set<String> roles;
    private List<Long> blogposts;
}
