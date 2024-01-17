//package com.norwayyachtbrockers.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.media.Schema;
//import jakarta.annotation.PostConstruct;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springdoc.core.customizers.OpenApiCustomiser;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.ContextRefreshedEvent;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class SwaggerConfig
////        implements ApplicationListener<ContextRefreshedEvent>
//{
//
//    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
//    private final ApplicationContext applicationContext;
//
//    public SwaggerConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    @PostConstruct
//    public void init() {
//        OpenApiCustomiser customiser = customOpenApiCustomiser();
//        OpenAPI openApi = applicationContext.getBean(OpenAPI.class);
//        customiser.customise(openApi);
//    }
//
////    @Override
////    public void onApplicationEvent(ContextRefreshedEvent event) {
////        OpenApiCustomiser customiser = customOpenApiCustomiser();
////        OpenAPI openApi = event.getApplicationContext().getBean(OpenAPI.class);
////        customiser.customise(openApi);
////    }
//
//    @Bean
//    public String testBean() {
//        logger.info("Test bean created");
//        return "test";
//    }
//
//    @Bean
//    public OpenApiCustomiser customOpenApiCustomiser() {
//        return openApi -> customizeOpenApi(openApi);
//    }
//
//    private void customizeOpenApi(OpenAPI openApi) {
//        logger.info("Customizing OpenAPI object");
//
//        Map<String, Schema> schemas = new HashMap<>(openApi.getComponents().getSchemas());
//
//        // Remove the specified schemas
//        schemas.keySet().removeIf(schemaName ->
//                schemaName.equals("PagedModelEntityModelYachtDetail") ||
//                        schemaName.equals("PagedModelEntityModelLocation") ||
//                        schemaName.equals("PagedModelEntityModelVessel") ||
//                        schemaName.equals("CollectionModelEntityModelYachtModel") ||
//                        schemaName.equals("CollectionModelObject") ||
//                        schemaName.equals("PagedModelEntityModelYachtModel")
//        );
//
//        // Update the OpenAPI object with the modified schemas map
//        openApi.getComponents().setSchemas(schemas);
//
//        logger.info("Finished customizing OpenAPI object");
//    }
//}