package com.realestate.invest.Config;

import org.springframework.context.annotation.Bean;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @The {@code MyCorsPolicy} class configures Cross-Origin Resource Sharing (CORS) policies for the application.
 * @It allows requests from any origin and sets the necessary headers for CORS.
 * @Additionally it exposes custom headers and specifies allowed HTTP methods.
 * 
 * @author Abhishek Srivastav
 */
@Configuration
public class MyCorsPolicy 
{
    /**
     * @implNoteConfigures and creates a CORS filter for the application.
     * @Note: Use "x-auth-token" in the header instead of "Authorization" for token-based authentication.
     * @return A CORS filter with the specified configuration.
     */
    @Bean
    CorsFilter corsFilter() 
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PATCH");

        config.addAllowedHeader("Origin");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("x-auth-token");
        config.addExposedHeader("x-auth-token");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
