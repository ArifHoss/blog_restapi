package com.blogapi.blog_restapi.controller;


import com.blogapi.blog_restapi.model.dto.BlogpostDto;
import com.blogapi.blog_restapi.model.dto.BlogpostMapper;
import com.blogapi.blog_restapi.model.pojo.Blogpost;
import com.blogapi.blog_restapi.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final BlogpostMapper blogpostMapper;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<BlogpostDto> getPosts() {
        List<Blogpost> posts = postService.getPosts();
        return posts.stream()
                .map(blogpostMapper::toBlogpostDto)
                .toList();
    }

    @GetMapping("/post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BlogpostDto getPostByID(@PathVariable Long id) {
        return blogpostMapper.toBlogpostDto(postService.getAPostById(id));
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BlogpostDto createPost(@RequestBody BlogpostDto blogpostDto) {

        Blogpost blogpost = blogpostMapper.toBlogpost(blogpostDto);
        Blogpost savedBlogpost = postService.createPost(blogpost);
        return blogpostMapper.toBlogpostDto(savedBlogpost);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BlogpostDto update(@PathVariable Long id, @RequestBody BlogpostDto blogpostDto) {
        Blogpost blogpost = blogpostMapper.toBlogpost(blogpostDto);
        Blogpost updatedBlogpost = postService.updatePost(id, blogpost);
        return blogpostMapper.toBlogpostDto(updatedBlogpost);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        postService.deletePost(id);
        return "POST_DELETED_SUCCESSFULLY";
    }
}
