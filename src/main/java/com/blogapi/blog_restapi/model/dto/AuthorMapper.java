package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.model.pojo.Author;

public interface AuthorMapper {
    AuthorResponseDto toAuthorDto(Author author);
    Author toAuthor(AuthorDto dto);
}
