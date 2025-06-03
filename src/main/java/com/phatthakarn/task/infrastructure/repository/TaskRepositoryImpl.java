package com.phatthakarn.task.infrastructure.repository;


import com.phatthakarn.task.domain.model.Task;
import com.phatthakarn.task.domain.model.UpdateTask;
import com.phatthakarn.task.domain.repository.TaskRepository;
import com.phatthakarn.task.domain.services.IDGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final JdbcTemplate jdbcTemplate;
    private final IDGenerator idGenerator;

    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate, IDGenerator idGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.idGenerator = idGenerator;
    }

    // Query แบบ Raw: ดึงข้อมูลทั้งหมด
    @Override
    public List<Task> findAll() {
        String sql = "SELECT id, title, description, status FROM tasks";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    // Query แบบ Raw: เพิ่ม Task ใหม่
    @Override
    public Long save(Task task) {
        String sql = "INSERT INTO tasks (id, title, description, status) VALUES (?, ?, ?, ?) RETURNING id";
        task.setId(idGenerator.generateId());
        return jdbcTemplate.queryForObject(sql, Long.class, task.getId(), task.getTitle(), task.getDescription(), task.getStatus());
    }


    // Query แบบ Raw: อัปเดต Task
    @Override
    public void update(Long id, UpdateTask task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, WHERE id = ?";
        jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), id);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new com.phatthakarn.task.domain.model.Task(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("status")
            );
        }
    }
}
