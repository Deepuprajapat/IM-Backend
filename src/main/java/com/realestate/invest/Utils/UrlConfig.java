package com.realestate.invest.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * @The {@code UrlConfig} class provides a centralized configuration for allowed URLs
 * within the application. It contains a method to retrieve a list of allowed URLs.
 * @Author: Abhishek Srivastav
 */
public class UrlConfig 
{
    /**
     * @Retrieves a list of allowed URLs configured within the application.
     *
     * @return A {@code List} of {@code String} representing the allowed URLs.
     */
    public static List<String> getAllowedUrls() 
    {
        return Arrays.asList
        (
            "/get/all/users",
                "user/create/new",
                "/user/**",
                "/designation/**",
                "/department/**",
                "/organization/**",
                "/auth/**",
                "/v3/api-docs/**",
                "/v3/api-docs.yaml/**",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/swagger-resources/configuration/**",
                "/swagger-resources/configuration/**",
                "/webjars/**",
                "/amenity/get/**",
                "/blogs/get/**",
                "/city/get/**",
                "/developer/get/**",
                "/email/**",
                "/floor-plan/get/**",
                "/locality/get/**",
                "/project-configuration/get/**",
                "/project-configuration-type/get/**",
                "/project/get/**",
                "/source/get/**",
                "/static-site-data/get/**",
                "/testimonials/get/**",
                "/user/get/**",
                "/leads/**",
                "/generic/keywords/**",
                "/property/**",
                "/rera-info/**",
                "/content/**"
        );

    }
}
