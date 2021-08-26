package com.example.employeemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Basic configuration class
 * containing properties to integrate
 * swagger in the application.
 *
 * @author sai praveen venturi
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.employeemanagement"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Employee Management API",
                "REST API used to perform CRUD operations on Employee Data source",
                "1.0",
                "Free to use",
                new Contact("Sai Praveen Venturi", "http://sai.com", "sai@gmail.com"),
                "API License",
                "http://sai.com",
                Collections.emptyList()
        );
    }
}
