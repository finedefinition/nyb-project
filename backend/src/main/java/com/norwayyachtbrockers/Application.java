package com.norwayyachtbrockers;

import com.norwayyachtbrockers.util.ExchangeRateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
//@PropertySource("file:${user.dir}/.env")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(com.norwayyachtbrockers.Application.class, args);
    }
}
