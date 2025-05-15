package com.realestate.invest.Utils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

/**
 * The {@code DataSourceConfig} class configures the primary data source for the application.
 * It uses the properties provided in {@link DataSourceProperties} to create a data source.
 * 
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 2023-09-01
 */
@Configuration
public class DataSourceConfig 
{

    /**
     * Configures and creates the primary data source using the properties from {@code DataSourceProperties}.
     *
     * @param dataSourceProperties The properties for configuring the data source.
     * @return The primary data source.
     */
    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties dataSourceProperties) 
    {
        return DataSourceBuilder
                .create()
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .driverClassName(dataSourceProperties.getDriverClassName())
                .build();
    }
}
