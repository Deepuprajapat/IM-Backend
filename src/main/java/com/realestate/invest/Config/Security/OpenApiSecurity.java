package com.realestate.invest.Config.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * The {@code OpenApiSecurity} class configures OpenAPI security definitions for the application.
 * It defines a Bearer Authentication scheme with the "x-auth-token" header.
 * Additionally, it provides information about the API such as title, description, version, and contact details.
 * 
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 2023-09-01
 */
@Configuration
public class OpenApiSecurity 
{
    /**
     * @apiNote Configures and creates an OpenAPI specification for the application.
     * @Note: Use "x-auth-token" in the header instead of "Authorization" for token-based authentication.
     * @return An OpenAPI specification with security definitions and metadata.
     */
    @Bean
    public OpenAPI openApi() 
    {
        return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().
            addList("Bearer Authentication"))
        .components(new Components().addSecuritySchemes
            ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("Invest Mango Back-End")
                        .description("Invest Mango Back-End With Jwt SpringBoot 3, And Swagger OpenAPI")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Abhishek Srivastav")
                                .url("https://abhishek-srv.github.io/abhishek/")
                                .email("thespyderwaves@gmail.com"))
                        .termsOfService("TOC")
                        .license(new License().name("License").url("#"))
                );
    }

    /**
     * Creates a Bearer Authentication security scheme with the "x-auth-token" header.
     *
     * @return A Bearer Authentication security scheme.
     */
    private SecurityScheme createAPIKeyScheme() 
    {
        return new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .in(SecurityScheme.In.HEADER)
        .name("x-auth-token");
    }
    
}
