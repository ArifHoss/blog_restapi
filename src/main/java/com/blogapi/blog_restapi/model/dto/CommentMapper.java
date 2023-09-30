package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.model.pojo.Comment;

public interface CommentMapper {

    CommentDto toCommentDto(Comment comment);

    Comment toComment(CommentDto commentDto);

}
