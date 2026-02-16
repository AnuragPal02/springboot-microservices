package com.ecommerce.apigateway1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gatewayAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gateway")
                        .description("E-Commerce Microservices Gateway")
                        .version("1.0"));
    }
}