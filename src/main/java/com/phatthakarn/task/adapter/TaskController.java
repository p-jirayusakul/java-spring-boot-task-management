package com.phatthakarn.task.adapter;

import com.phatthakarn.task.adapter.request.TaskDTO;
import com.phatthakarn.task.adapter.response.CreateResponse;
import com.phatthakarn.task.application.TaskUseCase;
import com.phatthakarn.task.domain.model.Task;
import com.phatthakarn.task.domain.model.UpdateTask;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskUseCase service;

    public TaskController(TaskUseCase service) {
        this.service = service;
    }

    @PostMapping
    public CreateResponse create(@RequestBody TaskDTO request) {
        Long id = service.createTask(request.getTitle(), request.getDescription());
        return new CreateResponse(id);
    }

    @GetMapping
    public List<Task> getAll() {
        return service.getTasks();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody TaskDTO request) {
        UpdateTask payload = new UpdateTask(request.getTitle(), request.getDescription());
        service.updateTask(id, payload);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.deleteTask(id);
    }
}
