package com.blogapi.blog_restapi.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BlogpostDto {
    private long id;
    private String title;
    private String content;
    private String publishDate;
    private String imageUrl;
    private long author;
    private String authorName;
    private long category;
    private Set<Long> tags;
    private long viewCount;
    private long likesCount;
    private long dislikesCount;
    private Set<Long> comments;
}
