package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.exception.ResourceNotFoundException;
import com.blogapi.blog_restapi.model.pojo.Author;
import com.blogapi.blog_restapi.model.pojo.Blogpost;
import com.blogapi.blog_restapi.model.pojo.Comment;
import com.blogapi.blog_restapi.repository.PostRepository;
import com.blogapi.blog_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements CommentMapper{
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());

        if (comment.getAuthor() != null) {
            dto.setAuthor(comment.getAuthor().getId());
        }
        if (comment.getBlogpost() != null) {
            dto.setBlogpost(comment.getBlogpost().getId());
        }

        return dto;
    }

    @Override
    public Comment toComment(CommentDto dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setContent(dto.getContent());

        if (dto.getAuthor() != 0) {
            Author author = userRepository.findById(dto.getAuthor())
                    .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
            comment.setAuthor(author);
        }
        if (dto.getBlogpost() != 0) {
            Blogpost post = postRepository.findById(dto.getBlogpost())
                    .orElseThrow(() -> new ResourceNotFoundException("POST_NOT_FOUND"));
            comment.setBlogpost(post);
        }


        return comment;
    }
}
