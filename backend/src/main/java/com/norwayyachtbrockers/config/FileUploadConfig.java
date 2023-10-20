package com.norwayyachtbrockers.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        // You can customize the settings if needed, such as max file size and max request size
        // multipartResolver.setMaxFileSize(2 * 1024 * 1024); // 2MB
        // multipartResolver.setMaxRequestSize(10 * 1024 * 1024); // 10MB
        return multipartResolver;
    }

}

