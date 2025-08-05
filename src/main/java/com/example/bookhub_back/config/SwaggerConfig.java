package com.example.bookhub_back.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("BookHub API 문서")
                .description("BookHub ERP API 문서입니다.")
                .version("1.0.0")
            );
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
            .group("auth-api")
            .pathsToMatch("/api/v2/auth/**")
            .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .group("admin-api")
            .pathsToMatch("/api/v2/admin/**")
            .build();
    }

    @Bean
    public GroupedOpenApi staffApi() {
        return GroupedOpenApi.builder()
            .group("staff-api")
            .pathsToMatch("/api/v2/staff/**")
            .build();
    }

    @Bean
    public GroupedOpenApi managerApi() {
        return GroupedOpenApi.builder()
            .group("manager-api")
            .pathsToMatch("/api/v2/manager/**")
            .build();
    }
}
