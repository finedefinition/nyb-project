package com.norwayyachtbrockers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // !WARNING! Allow requests from all origins
        config.addAllowedOrigin("https://nyb-git-filtering-antons-projects-563e8532.vercel.app");
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("Vessel-Delete-Status");


        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}