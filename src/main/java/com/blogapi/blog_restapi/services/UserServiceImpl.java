package com.blogapi.blog_restapi.services;


import com.blogapi.blog_restapi.exception.NonUniqueResultException;
import com.blogapi.blog_restapi.exception.NotAuthorizedException;
import com.blogapi.blog_restapi.exception.ResourceNotFoundException;
import com.blogapi.blog_restapi.exception.UnsupportedCharacterException;
import com.blogapi.blog_restapi.model.pojo.Author;
import com.blogapi.blog_restapi.model.pojo.Blogpost;
import com.blogapi.blog_restapi.repository.UserRepository;
import com.blogapi.blog_restapi.security.Authorizer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Authorizer authorizer;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Author> findAll() {
        Author user = authorizer.getCurrentTokenUser();
        Set<String> roles = user.getRoles();
        if (roles.contains("ADMIN") || roles.contains("admin") || roles.contains("Admin")) {
            return userRepository.findAll();

        } else {
            throw new NotAuthorizedException("NOT_ALLOWED");
        }
    }

    @Override
    public Author getCurrentTokenUser() {
        return authorizer.getCurrentTokenUser();
    }

    @Override
    public Author create(Author user) {
//        Author currentTokenUser = authorizer.getCurrentTokenUser();
//        Set<String> roles = currentTokenUser.getRoles();
        String email = user.getEmail();
        boolean existsByEmail = userRepository.existsByEmail(email);

        if (existsByEmail) {
            throw new NonUniqueResultException("USER_ALREADY_EXISTS");
        }
        if (email.isEmpty())
            throw new UnsupportedCharacterException("USER_EMAIL_CAN_NOT_BE_EMPTY");
        if (user.getPassword().isEmpty())
            throw new UnsupportedCharacterException("PASSWORD_CAN_NOT_BE_EMPTY");
        if (user.getRoles() == null)
            user.setRoles(Set.of("USER"));
//        if (!roles.contains("ADMIN")) {
//            throw new NotAuthorizedException("NOT_ALLOWED");
//        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Author getByUsername(String email) {
        Optional<Author> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }

        throw new ResourceNotFoundException("USER_NOT_FOUND");
    }

    @Override
    public void delete(Long id) {
        Author author = getUserByIdOrElseThrow(id);
        userRepository.delete(author);
    }

    @Override
    public List<Blogpost> getMyPost(Long id) {
        Author author = getUserByIdOrElseThrow(id);
        return author.getBlogposts();
    }

    @Override
    public Author findUserById(Long id) {
        return getUserByIdOrElseThrow(id);
    }

    private Author getUserByIdOrElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
    }

}
