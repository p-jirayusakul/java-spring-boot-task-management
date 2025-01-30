package org.workshop.task_management.internal.server.handlers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.task.TaskID;
import org.workshop.task_management.internal.server.domain.use_case.TaskUseCase;
import org.workshop.task_management.internal.server.request.TaskRequest;
import org.workshop.task_management.pkg.middleware.security.JwtUtil;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TaskHandler.class)
@TestPropertySource(properties = {
        "JWT_SECRET=NhfWytDIzKNteB5zChVsYBYL99Yed4Cx"
})
public class TaskHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskUseCase taskUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void createTaskSuccessfully() throws Exception {

        TaskRequest taskRequest = new TaskRequest(
                "Task Title",
                "Task Description",
                1L,
                2L
        );

        TaskID mockTaskID = new TaskID();
        mockTaskID.setId(1L);


        // ใช้ expectedToken ที่ Mock ไว้
        String expectedToken = jwtUtil.generateToken("1844995683120058368");
        when(taskUseCase.createTask(any(Task.class))).thenReturn(mockTaskID);

        mockMvc.perform(post("/api/v1/task")
                        .header("Authorization", "Bearer " + expectedToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/task/1"))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("create task completed"))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void createTaskInvalidRequest() throws Exception {
        TaskRequest taskRequest = new TaskRequest(
                "",
                "Task Description",
                1L,
                2L
        );

        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Title is required")));
    }

    @Test
    void createTaskFailure() throws Exception {
        TaskRequest taskRequest = new TaskRequest(
                "Task Title",
                "Task Description",
                1L,
                2L
        );

        when(taskUseCase.createTask(any(Task.class))).thenReturn(null);

        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Failed to create task"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void listTasksSuccessfully() throws Exception {
        // Arrange
        List<Task> listTask = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        listTask.add(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        listTask.add(task2);

        when(taskUseCase.listTasks()).thenReturn(listTask);

        mockMvc.perform(get("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("get list task completed"))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].title").value("Task 1"))
                .andExpect(jsonPath("$.data[0].description").value("Description 1"))
                .andExpect(jsonPath("$.data[1].id").value(2L))
                .andExpect(jsonPath("$.data[1].title").value("Task 2"))
                .andExpect(jsonPath("$.data[1].description").value("Description 2"));
    }
}