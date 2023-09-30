package com.blogapi.blog_restapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    /**
     * This method is used to configure the CORS.
     * @param registry - CorsRegistry object
     *                 - This object is used to register the CORS mappings.
     *                 - CORS mappings are used to allow the cross origin requests.
     *                 - Here we are allowing all the requests from all the origins.
     *                 - We are also allowing all the HTTP methods.
     *                 - This is not a good practice to allow all the requests from all the origins.
     *                 - We should allow only the required origins and HTTP methods.
     *                 - We should also allow only the required headers.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    }
}
