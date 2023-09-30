package com.blogapi.blog_restapi.services;



import com.blogapi.blog_restapi.model.pojo.Author;
import com.blogapi.blog_restapi.model.pojo.Blogpost;

import java.util.List;

public interface UserService {
//    @Secured("_ADMIN")
    List<Author> findAll();

    Author getCurrentTokenUser();

    Author create(Author user);

    Author getByUsername(String email);

    void delete(Long id);

    List<Blogpost> getMyPost(Long id);

    Author findUserById(Long id);
}
