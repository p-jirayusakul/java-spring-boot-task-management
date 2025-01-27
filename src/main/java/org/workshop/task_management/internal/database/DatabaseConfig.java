package org.workshop.task_management.internal.database;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    // โหลดไฟล์ .env.local และ System Environment
    private final Dotenv dotenv = Dotenv.configure()
            .filename(".env.local")
            .ignoreIfMissing()
            .load();

    @Value("${spring.datasource.url:}")
    private String dbUrl;

    @Value("${spring.datasource.username:}")
    private String dbUsername;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");

        // โหลดค่าจาก Environment Variables / .env.local
        String effectiveDbUrl = setenvOrDefault("DB_URL", dbUrl);
        String effectiveUsername = setenvOrDefault("DB_USERNAME", dbUsername);
        String effectivePassword = setenvOrDefault("DB_PASSWORD", dbPassword);

        dataSource.setUrl(effectiveDbUrl != null ? effectiveDbUrl : dbUrl);
        dataSource.setUsername(effectiveUsername != null ? effectiveUsername : dbUsername);
        dataSource.setPassword(effectivePassword != null ? effectivePassword : dbPassword);

        return dataSource;
    }

    private String setenvOrDefault(String key, String fallback) {
        String valueFromEnv = dotenv.get(key);
        if (valueFromEnv == null || valueFromEnv.isEmpty()) {
            valueFromEnv = System.getenv(key);
        }
        return (valueFromEnv != null && !valueFromEnv.isEmpty()) ? valueFromEnv : fallback;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}