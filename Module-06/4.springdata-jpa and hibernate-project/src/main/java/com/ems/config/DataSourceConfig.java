package com.ems.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Exercise 9: Customizing DataSource Configuration.
 *
 * Spring Boot auto-configures DataSource from application.properties.
 * This class shows how to:
 *  1. Read externalized config via @Value
 *  2. Explicitly build a DataSource bean (overriding auto-config)
 *  3. Pattern for multiple data sources (primary + secondary stubs)
 *
 * NOTE: The @Bean below is commented out so that Spring Boot's auto-config
 *       takes effect by default (avoids duplicate DataSource error in tests).
 *       Uncomment to override.
 */
@Configuration
public class DataSourceConfig {

    // Exercise 9: @Value reads from application.properties
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    /**
     * Exercise 9: Primary DataSource (H2 in-memory).
     * Uncomment @Bean + @Primary to override Spring Boot auto-config.
     */
    // @Bean
    // @Primary
    public DataSource primaryDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(datasourceUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setMaximumPoolSize(10);
        ds.setMinimumIdle(2);
        ds.setConnectionTimeout(30_000);
        ds.setIdleTimeout(600_000);
        ds.setMaxLifetime(1_800_000);
        ds.setPoolName("PrimaryPool");
        return ds;
    }

    /**
     * Exercise 9: Secondary DataSource pattern (stub).
     * In a real multi-datasource setup you would:
     *   - Define separate EntityManagerFactory beans
     *   - Define separate TransactionManager beans
     *   - Use @Qualifier to inject each one in its own repositories
     */
    // @Bean("secondaryDataSource")
    public DataSource secondaryDataSource() {
        HikariDataSource ds = new HikariDataSource();
        // Configure secondary DB (e.g., MySQL for reporting)
        ds.setJdbcUrl("jdbc:h2:mem:secondarydb");
        ds.setUsername("sa");
        ds.setPassword("password");
        ds.setDriverClassName("org.h2.Driver");
        ds.setPoolName("SecondaryPool");
        return ds;
    }
}
