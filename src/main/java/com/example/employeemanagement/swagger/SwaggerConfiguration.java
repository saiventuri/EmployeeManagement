package com.example.employeemanagement.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

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

    private static final String SECURITY_REFERENCE = "Token Access";
    private static final String AUTHORIZATION_DESCRIPTION = "Full API Permission";
    private static final String AUTHORIZATION_SCOPE = "Unlimited";
    private static final String AUTHORIZATION = "Authorization";

    /**
     * Generates Docket instance for swagger.
     *
     * @return the docket instance.
     */
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiDetails())
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.employeemanagement"))
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    /**
     * Generates the basic details for the swagger API.
     *
     * @return the api information instance.
     */
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

    private ApiKey apiKey() {
        return new ApiKey(SECURITY_REFERENCE, AUTHORIZATION, SecuritySchemeIn.HEADER.toString());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(securityReference()).build();
    }

    private List<SecurityReference> securityReference() {
        AuthorizationScope[] authorizationScopes = {
                new AuthorizationScope(AUTHORIZATION_SCOPE, AUTHORIZATION_DESCRIPTION)
        };
        return Collections.singletonList(new SecurityReference(SECURITY_REFERENCE, authorizationScopes));
    }
}
