package com.blogapi.blog_restapi.controller;

import com.blogapi.blog_restapi.model.dto.*;
import com.blogapi.blog_restapi.model.pojo.Author;
import com.blogapi.blog_restapi.model.pojo.Blogpost;
import com.blogapi.blog_restapi.security.AuthRequest;
import com.blogapi.blog_restapi.security.JwtResponse;
import com.blogapi.blog_restapi.security.JwtService;
import com.blogapi.blog_restapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthorMapper authorMapper;
    private final UserService userService;
    private final JwtService jwtService;
    private final BlogpostMapper blogpostMapper;
//    private final AuthenticationManager authenticationManager;
//    private final AuthorDetailsService userDetailsService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<AuthorResponseDto> getAllUser() {
        List<Author> users = userService.findAll();
        return users.stream().map(authorMapper::toAuthorDto).collect(Collectors.toList());
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public AuthorResponseDto create(@RequestBody AuthorDto dto) {
        Author author = authorMapper.toAuthor(dto);
        return authorMapper.toAuthorDto(userService.create(author));
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public AuthorResponseDto getUserById(@PathVariable Long id) {
        return authorMapper.toAuthorDto(userService.findUserById(id));
    }



    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/mypost/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<BlogpostDto> getMyPost(@PathVariable Long id) {
        List<Blogpost> posts = userService.getMyPost(id);
        return posts.stream()
                .map(blogpostMapper::toBlogpostDto)
                .toList();
    }

    @GetMapping("/auth")
    @ResponseStatus(value = HttpStatus.OK)
    public AuthorResponseDto getCurrentTokenUser() {
        Author user = userService.getCurrentTokenUser();
        return authorMapper.toAuthorDto(user);
    }

    @PostMapping("/auth")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getGWTToken(@RequestBody AuthRequest authRequest) {

        log.debug("In auth controller");
        log.debug("Authenticating user:" + authRequest.getEmail());

        final String token = jwtService.generateToken(authRequest.getEmail());
        Author user = userService.getByUsername(authRequest.getEmail());
        AuthorResponseDto authorDto = authorMapper.toAuthorDto(user);
        JwtResponse jwtResponse = new JwtResponse(token, authorDto);

        return ResponseEntity.ok(jwtResponse);
    }

    /*
//    Another way to create a token
    @PostMapping("/auth")
    public String getGWTToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());

        } else {
            throw new UsernameNotFoundException("NOT_FOUND");
        }
     */

}
