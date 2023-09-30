package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.exception.ResourceNotFoundException;
import com.blogapi.blog_restapi.model.pojo.*;
import com.blogapi.blog_restapi.repository.CommentRepository;
import com.blogapi.blog_restapi.repository.TagRepository;
import com.blogapi.blog_restapi.repository.UserRepository;
import com.blogapi.blog_restapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;



@Component
@RequiredArgsConstructor
public class BlogpostMapperImpl implements BlogpostMapper {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;


    @Override
    public BlogpostDto toBlogpostDto(Blogpost post) {
        BlogpostDto dto = new BlogpostDto();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPublishDate(post.getPublishDate());
        dto.setImageUrl(post.getImageUrl());

        if (post.getAuthor() != null) {
            dto.setAuthor(post.getAuthor().getId());
            dto.setAuthorName(post.getAuthor().getFirst_name() + " " + post.getAuthor().getLast_name());
        }

        if (post.getCategory() != null) {
            dto.setCategory(post.getCategory().getId());
        }

        if (post.getTags() != null) {
            Set<Long> tags = post.getTags().stream()
                    .map(Tag::getId).collect(Collectors.toSet());
            dto.setTags(tags);
        }

        dto.setViewCount(post.getViewCount());
        dto.setLikesCount(post.getLikesCount());
        dto.setDislikesCount(post.getDislikesCount());

        if (post.getComments() != null) {
            Set<Long> comments = post.getComments().stream()
                    .map(Comment::getId).collect(Collectors.toSet());
            dto.setComments(comments);
        }
        return dto;

    }

    @Override
    public Blogpost toBlogpost(BlogpostDto dto) {

        Blogpost post = new Blogpost();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd : HH:mm");
        String formattedDate = LocalDateTime.now().format(formatter);

        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPublishDate(formattedDate);

        post.setImageUrl(dto.getImageUrl());

        if (dto.getAuthor() != 0) {
            Author author = userRepository.findById(dto.getAuthor())
                    .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
            post.setAuthor(author);

        }

        if (dto.getCategory() != 0) {
            Category category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("CATEGORY_NOT_FOUND"));
            post.setCategory(category);
        }

        if (dto.getTags() != null) {
            Set<Tag> tags = dto.getTags().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("TAG_NOT_FOUND")))
                    .collect(Collectors.toSet());
            post.setTags(tags);
        }

        post.setViewCount(dto.getViewCount());
        post.setLikesCount(dto.getLikesCount());
        post.setDislikesCount(dto.getDislikesCount());

        if (dto.getComments() != null) {
            Set<Comment> comments = dto.getComments().stream()
                    .map(commentId -> commentRepository.findById(commentId)
                            .orElseThrow(() -> new ResourceNotFoundException("COMMENT_NOT_FOUND")))
                    .collect(Collectors.toSet());

            post.setComments(comments);
        }

        return post;
    }
}
