package com.blogapi.blog_restapi.model.dto;

import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private String content;
    private long author;
    private long blogpost;
}
