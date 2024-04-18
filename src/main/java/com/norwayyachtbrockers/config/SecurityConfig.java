package com.norwayyachtbrockers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${aws.cognito.issuerUri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                // Explicitly permit public endpoints
                                .requestMatchers("/").permitAll()
                                .requestMatchers( "/error", "/swagger-ui/**").authenticated()
                        // Secure favourite yachts endpoints for users with USER_ROLE
                        .requestMatchers(HttpMethod.POST, "/users/{userId}/favouriteYachts/{yachtId}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/users/{userId}/favouriteYachts").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/users/{userId}/favouriteYachts/{yachtId}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/yachts").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/yachts/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/yachts/{id}").hasRole("ADMIN")
                                .anyRequest().permitAll())
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless session; no session will be created or used by Spring Security
                .httpBasic(Customizer.withDefaults()) // If basic authentication is needed, consider removing if not
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt
                        .decoder(jwtDecoder()) // Use the jwtDecoder method
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))) // Use custom JWT converter
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("cognito:groups");
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
}
