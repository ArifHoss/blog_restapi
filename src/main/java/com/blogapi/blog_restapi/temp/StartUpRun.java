package com.blogapi.blog_restapi.temp;


import com.blogapi.blog_restapi.model.pojo.Author;
import com.blogapi.blog_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartUpRun implements CommandLineRunner {

    
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if ( !userRepository.existsByEmail("arif@gmail.com") ) {

            Author user = Author.builder()
                    .first_name("Arif")
                    .last_name("Hossain")
                    .roles(Set.of("ADMIN"))
                    .email("arif@gmail.com")
                    .password(passwordEncoder.encode("zoro123"))
//                    .encryptedPassword(passwordEncoder.encode("zoro123").getBytes(StandardCharsets.UTF_8))
                    .build();

            userRepository.save(user);

            log.debug("User {} created...", user.getFirst_name());
        }
        if ( !userRepository.existsByEmail("sumon@gmail.com") ) {

            Author user = Author.builder()
                    .first_name("Sumon")
                    .last_name("Hossain")
                    .roles(Set.of("USER"))
                    .email("sumon@gmail.com")
                    .password(passwordEncoder.encode("zoro123"))
//                    .encryptedPassword(passwordEncoder.encode("zoro123").getBytes(StandardCharsets.UTF_8))
                    .build();

            userRepository.save(user);

            log.debug("User {} created...", user.getFirst_name());
        }
    }
}