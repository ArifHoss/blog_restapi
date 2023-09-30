package com.blogapi.blog_restapi.security;


import com.blogapi.blog_restapi.exception.ResourceNotFoundException;
import com.blogapi.blog_restapi.model.pojo.Author;
import com.blogapi.blog_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Authorizer {

    private final UserRepository userRepository;


    public Author getCurrentTokenUser() {
        Optional<Author> author = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return author.orElseThrow(() -> new ResourceNotFoundException("USER_MISSING"));
    }



    public Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public String getRole(){
        return getAuth().getAuthorities().toArray()[0].toString();
    }


}
