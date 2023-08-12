package com.spring.kiddiecare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spring.kiddiecare.controller.AdminController"))
                .paths(PathSelectors.ant("admin/info/**"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("My App REST API")
                .description("REST API for My App")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("My App Team", "localhost:8082/", "heejae0629@naver.com"))
                .build();
    }
}
