package com.blogapi.blog_restapi.services;



import com.blogapi.blog_restapi.model.pojo.Blogpost;

import java.util.List;

public interface PostService {
    List<Blogpost> getPosts();
    Blogpost createPost(Blogpost blogpost);
    Blogpost updatePost(Long id, Blogpost blogpost);
    void deletePost(Long id);

    Blogpost getAPostById(Long id);
}
