package com.sjincho.hun.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "배달 Restful Service API 명세서",
                     description = "SpringBoot로 개발하는 RESTful API 명세서 입니다.",
                     version = "v1.0.0"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi customOpenApi() {
        String[] paths = {
                "/foods/**",
                "/members/**",
                "/orders/**",
                "/deliveries/**"};

        return GroupedOpenApi.builder()
                .group("\"음식상품\", \"회원\", \"주문\", \"배달\" 도메인에 대한 API")
                .pathsToMatch(paths)
                .build();
    }

}
