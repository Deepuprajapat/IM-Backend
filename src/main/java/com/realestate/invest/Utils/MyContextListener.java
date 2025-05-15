package com.realestate.invest.Utils;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The {@code MyContextListener} class implements a ServletContextListener to manage resources when the application context is initialized and destroyed.
 * It specifically handles the closing of a HikariDataSource when the application context is destroyed.
 * 
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 2023-09-01
 */
@Configuration
public class MyContextListener implements ServletContextListener 
{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) 
    {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) 
    {
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        HikariDataSource hikariDataSource = context.getBean(HikariDataSource.class);
        
        if (hikariDataSource != null) 
        {
            hikariDataSource.close();
        }
    }

    @Bean
    RestTemplate restTemplate() 
    {
        return new RestTemplate();
    }

}
