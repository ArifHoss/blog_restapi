package com.blogapi.blog_restapi.services;


import com.blogapi.blog_restapi.exception.ResourceNotFoundException;
import com.blogapi.blog_restapi.model.pojo.Blogpost;
import com.blogapi.blog_restapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<Blogpost> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Blogpost createPost(Blogpost blogpost) {
        return postRepository.save(blogpost);
    }

    @Override
    public Blogpost updatePost(Long id, Blogpost blogpost) {

        Blogpost existingPost = getPostById(id);

        String title = blogpost.getTitle();
        String content = blogpost.getContent();

        if (title == null || title.isEmpty()) throw new IllegalArgumentException("TITLE_CANNOT_BE_EMPTY");
        if (content == null || content.isEmpty()) throw new IllegalArgumentException("CONTENT_CANNOT_BE_EMPTY");

        existingPost.setTitle(title);
        existingPost.setContent(content);
        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(Long id) {
        Blogpost existingPost = getPostById(id);
        postRepository.delete(existingPost);
    }

    @Override
    public Blogpost getAPostById(Long id) {
        return getPostById(id);
    }

    private Blogpost getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

}
