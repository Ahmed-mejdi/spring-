package com.example.bookcatalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bookCatalogAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Catalog API")
                        .description("A Spring Boot REST API for managing a book catalog with Redis caching")
                        .version("v1")
                        .contact(new Contact()
                                .name("Your Name")
                                .url("https://example.com")
                                .email("your.email@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}