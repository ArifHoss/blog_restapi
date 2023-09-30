package com.blogapi.blog_restapi.security;



import com.blogapi.blog_restapi.exception.ResourceNotFoundException;
import com.blogapi.blog_restapi.model.pojo.Author;
import com.blogapi.blog_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // this method is called by spring security to get the user details from the database

        Optional<Author> findUserByEmail = userRepository.findByEmail(username);

        if (findUserByEmail.isPresent()) {
            Author author = findUserByEmail.get();
            log.trace("User found");
            String password = author.getPassword();
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            log.debug("Building authorities.....");
            return new User(author.getEmail(), password, authorities); // this is the user details object that spring security uses to authenticate the user
        } else {
            return findUserByEmail.map(AuthorDetails::new)
                    .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
        }
    }
}
