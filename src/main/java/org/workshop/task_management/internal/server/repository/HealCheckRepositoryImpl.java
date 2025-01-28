package org.workshop.task_management.internal.server.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.workshop.task_management.internal.server.domain.repositories.HealthCheckRepository;

@Repository
public class HealCheckRepositoryImpl implements HealthCheckRepository {

    private static final Logger logger = LoggerFactory.getLogger(HealCheckRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;
    
    public HealCheckRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean readiness() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return true;
        } catch (Exception e) {
            logger.error("Database is not ready: {}", e.getMessage(), e);
            return false;
        }
    }
}
