package com.blogapi.blog_restapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthorDetailsService authorDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        String token = null;
//        String username = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            username = jwtService.extractUsername(token);
//        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = blogUserDetailsService.loadUserByUsername(username);
//            if (jwtService.validateToken(token, userDetails)) {
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//        chain.doFilter(request, response);
        log.debug("In JwtRequestFilter......");
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                log.warn("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                log.info("JWT Token has expired");
            }
        } else if (requestTokenHeader != null && requestTokenHeader.startsWith("JWT ")) {
            jwtToken = requestTokenHeader.substring(4);
            try {
                username = jwtService.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                log.trace("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                log.trace("JWT Token has expired");
            }
        } else {
            log.trace("Not Bearer nor JWT");
        }
        log.trace("Retrieved token with username: " + username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.trace("Loading user by username...");
            UserDetails userDetails = this.authorDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                log.trace("Token valid: " + jwtService.validateToken(jwtToken, userDetails));
            }

        } else {
            log.trace("Failed to assert");
        }
        chain.doFilter(request, response);



    }
}
