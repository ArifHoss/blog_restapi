package com.blogapi.blog_restapi.security;


import com.blogapi.blog_restapi.model.pojo.Author;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuthorDetails implements UserDetails {

    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public AuthorDetails(Author author) {
        email = author.getEmail();
        password = author.getPassword();
        authorities = author.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
//        authorities = Arrays.stream(author.getRoles().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
