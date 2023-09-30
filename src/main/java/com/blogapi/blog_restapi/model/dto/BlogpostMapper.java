package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.model.pojo.Blogpost;

public interface BlogpostMapper {

    BlogpostDto toBlogpostDto(Blogpost blogpost);
    Blogpost toBlogpost(BlogpostDto blogpostDto);
}
