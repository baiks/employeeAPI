//package com.employee.management.configs;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Value("${env.devUrl}")
//    String dev;
//
//    @Value("${env.prodUrl}")
//    String prod;
//
//    // OpenAPI customization bean (adding info, servers, etc.)
//    @Bean
//    public OpenAPI customOpenAPI() {
//        final String apiTitle = "School System API";
//        final String description = "School System API";
//        String apiVersion = "1.0.0";
//
//        // Contact information
//        Contact contact = new Contact();
//        contact.setName("Paul");
//        contact.setEmail("paulkabaiku023@gmail.com");
//        contact.setUrl("https://paulkimani.net");
//
//        return new OpenAPI()
//                .info(new Info().title(apiTitle).version(apiVersion).description(description).contact(contact))
//                .addServersItem(new io.swagger.v3.oas.models.servers.Server()
//                        .url(dev)
//                        .description("Development server"))
//                .addServersItem(new io.swagger.v3.oas.models.servers.Server()
//                        .url(prod)
//                        .description("Production server"))
//                // Define security scheme for Bearer Authentication
//                .components(new io.swagger.v3.oas.models.Components()
//                        .addSecuritySchemes("bearerAuth",
//                                new SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT"))
//                )
//                // Apply the security requirement globally for all endpoints
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
//    }
//
////    // GroupedOpenApi to limit scanning to controller package only
////    @Bean
////    public GroupedOpenApi defaultOpenApi() {
////        return GroupedOpenApi.builder()
////                .group("default-api")
////                // Specify the controller package to scan only
////                .packagesToScan("com.web.api.controllers")  // Only scan this package
////                .build();
////    }
////
////    @Bean
////    public GroupedOpenApi publicApi() {
////        return GroupedOpenApi.builder()
////                .group("public-api")
////                // Specify the controller package to scan only
////                .packagesToScan("com.web.api.controllers")  // Only scan this package
////                .build();
////    }
//}
