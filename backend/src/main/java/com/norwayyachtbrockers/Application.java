package com.norwayyachtbrockers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@PropertySource("file:${user.dir}/.env")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(com.norwayyachtbrockers.Application.class, args);
    }
}
