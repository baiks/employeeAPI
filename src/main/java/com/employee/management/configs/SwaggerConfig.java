package com.employee.management.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String apiTitle = "Employees API";
        final String description = "Employees API";
        String apiVersion = "1.0.0";
        Contact contact = new Contact();
        contact.setName("Paul");
        contact.setEmail("paulkabaiku023@gmail.com");
        contact.setUrl("https://paulkimani.net");

        return new OpenAPI()
                .info(new Info().title(apiTitle).version(apiVersion).description(description).contact(contact))
                .addServersItem(new io.swagger.v3.oas.models.servers.Server()
                        .url("http://localhost:8067")
                        .description("Development server"));
    }
}