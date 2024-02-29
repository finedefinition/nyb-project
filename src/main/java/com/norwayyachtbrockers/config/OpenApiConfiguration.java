package com.norwayyachtbrockers.config;


import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenApiCustomiser customiser() {
        return openApi -> {
            // Custom logic to modify the OpenAPI object
            // For example, remove certain schemas:
            openApi.getComponents().getSchemas().remove("AbstractJsonSchemaPropertyObject");
        };
    }
}
