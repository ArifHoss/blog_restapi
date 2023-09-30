package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.model.pojo.Author;
import com.blogapi.blog_restapi.model.pojo.Blogpost;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorMapperImpl implements AuthorMapper {


    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthorResponseDto toAuthorDto(Author author) {

        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setId(author.getId());
        dto.setFirst_name(author.getFirst_name());
        dto.setLast_name(author.getLast_name());
        dto.setEmail(author.getEmail());
        if (author.getRoles() != null) {
            dto.setRoles(author.getRoles());
        }
        if (author.getBlogposts() != null) {
            List<Long> postIds = author.getBlogposts().stream().map(Blogpost::getId).collect(Collectors.toList());
            dto.setBlogposts(postIds);
        }
        return dto;
    }

    @Override
    public Author toAuthor(AuthorDto dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setFirst_name(dto.getFirst_name());
        author.setLast_name(dto.getLast_name());
        author.setEmail(dto.getEmail());
        author.setPassword(passwordEncoder.encode(dto.getPassword()));
        author.setRoles(dto.getRoles());
        return author;
    }
}

