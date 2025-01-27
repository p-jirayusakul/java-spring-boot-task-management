package org.workshop.task_management.internal.server.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.workshop.task_management.internal.server.domain.entities.master_data.PriorityLevels;
import org.workshop.task_management.internal.server.domain.entities.master_data.Role;
import org.workshop.task_management.internal.server.domain.entities.master_data.TaskStatus;
import org.workshop.task_management.internal.server.domain.repositories.MasterDataRepository;
import org.workshop.task_management.pkg.exceptions.RepositoryException;

import java.util.List;

@Repository
public class MasterDataRepositoryImpl implements MasterDataRepository {
    private static final Logger logger = LoggerFactory.getLogger(MasterDataRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public MasterDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TaskStatus> listTaskStatuses() {
        String sql = "SELECT id, title, code FROM public.master_data_task_status WHERE active IS TRUE;";
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, _) -> new TaskStatus(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("code")
                    )
            );
        } catch (DataAccessException e) {
            logger.error("Error: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<PriorityLevels> listPriorityLevels() {
        String sql = "SELECT id, title, code FROM public.master_data_priority_levels WHERE active IS TRUE ORDER BY seq ASC;";
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, _) -> new PriorityLevels(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("code")
                    )
            );
        } catch (DataAccessException e) {
            logger.error("Error: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<Role> listRoles() {
        String sql = "SELECT id, title, code FROM public.master_data_role WHERE active IS TRUE;";
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, _) -> new Role(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("code")
                    )
            );
        } catch (DataAccessException e) {
            logger.error("Error: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

}