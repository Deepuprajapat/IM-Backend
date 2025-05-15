package com.realestate.invest.Config.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The {@code AppConfig} class configures the primary data source for the application.
 * It uses the properties provided in {@link DataSourceProperties} to create a data source.
 * 
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 2023-09-01
 */
@Configuration
public class AppConfig 
{
    @Bean
    PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception 
    {
        return builder.getAuthenticationManager();
    }
    
}
