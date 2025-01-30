package org.workshop.task_management.internal.server.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.users.Users;
import org.workshop.task_management.internal.server.domain.repositories.UsersRepository;
import org.workshop.task_management.pkg.exceptions.NotFoundException;
import org.workshop.task_management.pkg.exceptions.RepositoryException;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    private static final Logger logger = LoggerFactory.getLogger(UsersRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Users getUser(String username) {
        String sql = "SELECT id, password FROM public.users WHERE username = ? LIMIT 1;";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, _) -> new Users(
                            rs.getLong("id"),
                            rs.getString("password")
                    ),
                    username
            );
        } catch (DataAccessException e) {
            if (e.getMessage().contains("Incorrect result size: expected 1, actual 0")) {
                throw new NotFoundException("Username " + username + " not found.");
            }
            logger.error("repository getTask: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }
}
