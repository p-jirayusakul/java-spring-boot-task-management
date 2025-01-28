package org.workshop.task_management.internal.server.repository;

import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.task.TaskPriorityLevel;
import org.workshop.task_management.internal.server.domain.entities.task.TaskStatus;
import org.workshop.task_management.internal.server.domain.repositories.TaskRepository;
import org.workshop.task_management.pkg.exceptions.NotFoundException;
import org.workshop.task_management.pkg.exceptions.RepositoryException;

import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> listTasks() {
        String sql = "SELECT id, title, description, task_status_id, priority_levels_id, created_by, created_at, updated_at, updated_by FROM public.task;";
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, _) -> new Task(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getLong("task_status_id"),
                            rs.getLong("priority_levels_id"),
                            rs.getLong("created_by"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
                            rs.getLong("updated_by")
                    )
            );
        } catch (DataAccessException e) {
            logger.error("repository listTasks: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Task getTask(Long id) {
        String sql = "SELECT id, title, description, task_status_id, priority_levels_id, created_by, created_at, updated_at, updated_by FROM public.task WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, _) -> new Task(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getLong("task_status_id"),
                            rs.getLong("priority_levels_id"),
                            rs.getLong("created_by"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
                            rs.getLong("updated_by")
                    ),
                    id
            );
        } catch (DataAccessException e) {
            if (e.getMessage().contains("Incorrect result size: expected 1, actual 0")) {
                throw new NotFoundException("Task with ID " + id + " not found.");
            }
            logger.error("repository getTask: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public boolean taskExists(Long id) {
        String sql = "SELECT COUNT(1) FROM public.task WHERE id = ?;";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            logger.error("repository taskExists: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Long createTask(Task task) {

        long machineId = 1L;  // Unique machine ID
        long datacenterId = 1L;  // Unique datacenter ID
        cn.hutool.core.lang.Snowflake snowflake = IdUtil.getSnowflake(machineId, datacenterId);

        task.setId(snowflake.nextId());
        String sql = "INSERT INTO public.task (id, title, description, task_status_id, priority_levels_id, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id;";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    Long.class,
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getTaskStatusId(),
                    task.getPriorityLevelsId(),
                    task.getCreatedBy()
            );
        } catch (DataAccessException e) {
            logger.error("repository createTask: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task) {
        String sql = "UPDATE public.task SET title = ?, description = ?, task_status_id = ?, priority_levels_id = ?, updated_by=?, updated_at = NOW() WHERE id = ?;";
        try {
            jdbcTemplate.update(
                    sql,
                    task.getTitle(),
                    task.getDescription(),
                    task.getTaskStatusId(),
                    task.getPriorityLevelsId(),
                    task.getUpdatedBy(),
                    task.getId()
            );
        } catch (DataAccessException e) {
            logger.error("repository updateTask: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void updateTaskStatus(TaskStatus task) {
        String sql = "UPDATE public.task SET task_status_id = ?, updated_by = ?, updated_at = NOW() WHERE id = ?;";
        try {
            jdbcTemplate.update(
                    sql,
                    task.getTaskStatusId(),
                    task.getUpdatedBy(),
                    task.getId()
            );
        } catch (DataAccessException e) {
            logger.error("repository updateTaskStatus: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void updateTaskPriorityLevel(TaskPriorityLevel task) {
        String sql = "UPDATE public.task SET priority_levels_id = ?, updated_by = ?, updated_at = NOW() WHERE id = ?;";
        try {
            jdbcTemplate.update(
                    sql,
                    task.getPriorityLevelId(),
                    task.getUpdatedBy(),
                    task.getId()
            );
        } catch (DataAccessException e) {
            logger.error("repository updateTaskPriorityLevel: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void deleteTask(Long id) {
        String sql = "DELETE FROM public.task WHERE id = ?;";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            logger.error("repository deleteTask: {}", e.getMessage(), e);
            throw new RepositoryException(e.getMessage());
        }
    }
}
