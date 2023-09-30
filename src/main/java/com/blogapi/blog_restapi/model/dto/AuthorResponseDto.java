package com.blogapi.blog_restapi.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

// AuthorResponseDto is a DTO (Data Transfer Object) that is used to send data to the client.
// It contains properties that are needed to be sent to the client. Ignore password field.

@Data
public class AuthorResponseDto {
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private Set<String> roles;
    private List<Long> blogposts;

}
