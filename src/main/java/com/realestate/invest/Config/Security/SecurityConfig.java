package com.realestate.invest.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.realestate.invest.Config.JWT.JwtAuthenticationEntryPoint;
import com.realestate.invest.Config.JWT.JwtAuthenticationFilter;
import com.realestate.invest.Utils.UrlConfig;

/**
 * The {@code SecurityConfig} class configures security settings for the application.
 * It sets up user authentication, authorization rules, CORS configuration, and JWT-based authentication.
 * 
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 2023-09-01
 */
@EnableWebSecurity
@Configuration
@EnableWebMvc
public class SecurityConfig 
{
    

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired 
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures and creates an AuthenticationProvider for user authentication.
     *
     * @return An AuthenticationProvider configured with userDetailsService and passwordEncoder.
     */
    @Bean
    AuthenticationProvider authenticationProvider() 
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * @Configures security filters and rules for HTTP requests.
     *
     * @param http The HttpSecurity configuration.
     * @return A SecurityFilterChain with configured security settings.
     * @throws Exception If there is an issue configuring the security settings.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
        http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorizeRequests -> 
        authorizeRequests
            .requestMatchers(UrlConfig.getAllowedUrls().toArray(new String[0])).permitAll()
            .requestMatchers(HttpMethod.OPTIONS).permitAll()
            .anyRequest().authenticated())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }
}
