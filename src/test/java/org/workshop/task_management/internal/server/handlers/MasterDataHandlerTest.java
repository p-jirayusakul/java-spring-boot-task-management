package org.workshop.task_management.internal.server.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.workshop.task_management.internal.server.domain.entities.master_data.PriorityLevels;
import org.workshop.task_management.internal.server.domain.entities.master_data.Role;
import org.workshop.task_management.internal.server.domain.entities.master_data.TaskStatus;
import org.workshop.task_management.internal.server.domain.use_case.MasterDataUseCase;
import org.workshop.task_management.pkg.exceptions.RepositoryException;
import org.workshop.task_management.pkg.middleware.security.JwtUtil;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MasterDataHandler.class)
public class MasterDataHandlerTest {


    @MockitoBean
    private JwtUtil jwtUtil; // Mock JwtUtil ด้วย @MockitoBean

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MasterDataUseCase masterDataUseCase;

    @Test
    void listTaskStatuses_shouldReturnTaskStatuses() throws Exception {
        // Arrange
        List<TaskStatus> taskStatuses = new ArrayList<>();
        TaskStatus status1 = new TaskStatus();
        status1.setId(1L);
        status1.setTitle("In Progress");
        taskStatuses.add(status1);

        TaskStatus status2 = new TaskStatus();
        status2.setId(2L);
        status2.setTitle("Completed");
        taskStatuses.add(status2);

        Mockito.when(masterDataUseCase.listTaskStatuses()).thenReturn(taskStatuses);

        // Act & Assert
        mockMvc.perform(get("/api/v1/master-data/task-status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("get list task status completed")))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].title", is("In Progress")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].title", is("Completed")));
    }

    @Test
    void listPriorityLevels_shouldReturnPriorityLevels() throws Exception {
        // Arrange
        List<PriorityLevels> priorityLevels = new ArrayList<>();
        PriorityLevels level1 = new PriorityLevels();
        level1.setId(1L);
        level1.setTitle("High");
        priorityLevels.add(level1);

        PriorityLevels level2 = new PriorityLevels();
        level2.setId(2L);
        level2.setTitle("Medium");
        priorityLevels.add(level2);

        Mockito.when(masterDataUseCase.listPriorityLevels()).thenReturn(priorityLevels);

        // Act & Assert
        mockMvc.perform(get("/api/v1/master-data/priority-levels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("get list priority levels completed")))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].title", is("High")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].title", is("Medium")));
    }

    @Test
    void listRoles_shouldReturnRoles() throws Exception {
        // Arrange
        List<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setId(1L);
        role1.setTitle("Admin");
        roles.add(role1);

        Role role2 = new Role();
        role2.setId(2L);
        role2.setTitle("User");
        roles.add(role2);

        Mockito.when(masterDataUseCase.listRoles()).thenReturn(roles);

        // Act & Assert
        mockMvc.perform(get("/api/v1/master-data/role")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("get list role completed")))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].title", is("Admin")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].title", is("User")));
    }

    @Test
    void listTaskStatuses_repositoryError_shouldReturnInternalServerError() throws Exception {
        // Arrange
        Mockito.when(masterDataUseCase.listTaskStatuses()).thenThrow(new RepositoryException("Database unavailable"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/master-data/task-status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Internal Server Error")));
    }

    @Test
    void listPriorityLevels_repositoryError_shouldReturnInternalServerError() throws Exception {
        // Arrange
        Mockito.when(masterDataUseCase.listPriorityLevels()).thenThrow(new RepositoryException("Database unavailable"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/master-data/priority-levels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Internal Server Error")));
    }

    @Test
    void listRoles_repositoryError_shouldReturnInternalServerError() throws Exception {
        // Arrange
        Mockito.when(masterDataUseCase.listRoles()).thenThrow(new RepositoryException("Database unavailable"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/master-data/role")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Internal Server Error")));
    }
}