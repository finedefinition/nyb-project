package com.norwayyachtbrockers;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableScheduling
//@PropertySource("file:${user.dir}/.env")
@OpenAPIDefinition(
        info = @Info(
                title = "Norse Yacht Co. REST API Documentation",
                description = "Norse Yacht Co. REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Sergii Bezrukov",
                        email = "sbezrukov@norseyacht.com",
                        url = "https://www.norseyacht.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.norseyacht.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description =  "Norse Yacht Co. REST API Documentation",
                url = "https://www.norseyacht.com/swagger-ui.html"
        )
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(com.norwayyachtbrockers.Application.class, args);
    }
}
